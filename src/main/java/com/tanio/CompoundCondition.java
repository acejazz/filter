package com.tanio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.copyOf;

class CompoundCondition implements Evaluable {
    private final BooleanOperator operator;
    private final List<Evaluable> conditions;

    CompoundCondition(BooleanOperator operator, List<Evaluable> conditions) {
        this.operator = operator;
        this.conditions = conditions;
    }

    @Override
    public <T> List<T> evaluate(List<T> target) {
        List<List<T>> results = conditions.stream()
                .map(it -> it.evaluate(target))
                .collect(Collectors.toList());

        return switch (operator) {
            case OR -> or(results);
            case AND -> and(results);
            // !A and !B and !C = !(A or B or C)
            case NOT -> not(target, or(results));
        };
    }

    private static <T> List<T> and(List<List<T>> resultLists) {
        Iterator<List<T>> iterator = resultLists.iterator();
        List<T> result = new ArrayList<>(iterator.next());
        while (iterator.hasNext()) {
            result.retainAll(iterator.next());
        }
        return result;
    }

    private static <T> List<T> or(List<List<T>> resultLists) {
        return resultLists.stream()
                .flatMap(List::stream)
                .toList();
    }

    private static <T> List<T> not(List<T> universe, List<T> list) {
        List<T> result = new ArrayList<>(copyOf(universe));
        result.removeAll(list);
        return result;
    }

    enum BooleanOperator {
        OR, AND, NOT
    }
}