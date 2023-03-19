package com.tanio;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.List.copyOf;

class CompoundCondition implements Evaluable {
    private final BooleanOperator operator;
    private final Set<Evaluable> conditions;

    CompoundCondition(BooleanOperator operator, Set<Evaluable> conditions) {
        this.operator = operator;
        this.conditions = conditions;
    }

    @Override
    public <T> Set<T> evaluate(List<T> target) {
        List<Set<T>> results = conditions.stream()
                .map(it -> it.evaluate(target))
                .collect(Collectors.toList());

        return switch (operator) {
            case OR -> or(results);
            case AND -> and(results);
            // !A and !B and !C = !(A or B or C)
            case NOT -> not(target, or(results));
        };
    }

    private static <T> Set<T> or(List<Set<T>> resultLists) {
        return resultLists.stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    private static <T> Set<T> and(List<Set<T>> resultLists) {
        Iterator<Set<T>> iterator = resultLists.iterator();
        Set<T> result = new HashSet<>(iterator.next());
        while (iterator.hasNext()) {
            result.retainAll(iterator.next());
        }
        return result;
    }

    private static <T> Set<T> not(List<T> universe, Set<T> set) {
        Set<T> result = new HashSet<>(copyOf(universe));
        result.removeAll(set);
        return result;
    }

    public BooleanOperator getOperator() {
        return operator;
    }

    public Set<Evaluable> getConditions() {
        return conditions;
    }

    enum BooleanOperator {
        OR, AND, NOT
    }
}