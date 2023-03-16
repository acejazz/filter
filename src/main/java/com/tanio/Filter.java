package com.tanio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.copyOf;

class Filter {
    private final FieldConditionEvaluator fieldConditionEvaluator = new FieldConditionEvaluator();
    private final FieldValueRetriever fieldValueRetriever = new FieldValueRetriever();

    <T> List<T> perform(List<T> target, CompoundCondition compoundCondition) {
        if (compoundCondition.conditions != null && compoundCondition.nestedConditions != null) {
            List<List<T>> nonNestedConditionsResults = compoundCondition.conditions.stream()
                    .map(it -> perform(target, it))
                    .toList();

            List<List<T>> nestedConditionsResults = compoundCondition.nestedConditions.stream()
                    .map(it -> perform(target, it))
                    .toList();

            List<List<T>> allResults = new ArrayList<>();
            allResults.addAll(nonNestedConditionsResults);
            allResults.addAll(nestedConditionsResults);

            return switch (compoundCondition.booleanOperator) {
                case OR -> or(allResults);
                case AND -> and(allResults);
                // !A and !B and !C = !(A or B or C)
                case NOT -> not(target, or(allResults));
            };
        }

        if (compoundCondition.conditions != null) {
            return switch (compoundCondition.booleanOperator) {
                case OR -> performOr(target, compoundCondition.conditions);
                case AND -> performAnd(target, compoundCondition.conditions);
                case NOT -> performNot(target, compoundCondition.conditions);
            };
        }

        List<List<T>> nestedResults = compoundCondition.nestedConditions.stream()
                .map(it -> perform(target, it))
                .toList();
        return switch (compoundCondition.booleanOperator) {
            case OR -> or(nestedResults);
            case AND -> and(nestedResults);
            // !A and !B and !C = !(A or B or C)
            case NOT -> not(target, or(nestedResults));
        };
    }

    private <T> List<T> perform(List<T> target, Condition condition) {
        return target.stream()
                .filter(it -> matchesCondition(condition, it))
                .collect(Collectors.toList());
    }

    private static <T> List<T> and(List<List<T>> resultLists) {
        Iterator<List<T>> iterator = resultLists.iterator();
        List<T> result = iterator.next();
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

    private <T> List<T> performOr(List<T> target, List<Condition> conditions) {
        return target.stream()
                .filter(it -> matchesAtLeastOneCondition(conditions, it))
                .collect(Collectors.toList());
    }

    private <T> List<T> performAnd(List<T> target, List<Condition> conditions) {
        return target.stream()
                .filter(it -> matchesAllConditions(conditions, it))
                .collect(Collectors.toList());
    }

    private <T> List<T> performNot(List<T> target, List<Condition> conditions) {
        return target.stream()
                .filter(it -> !matchesAtLeastOneCondition(conditions, it))
                .collect(Collectors.toList());
    }

    private <T> boolean matchesAtLeastOneCondition(List<Condition> conditions, T t) {
        return conditions.stream().anyMatch(it -> matchesCondition(it, t));
    }

    private <T> boolean matchesAllConditions(List<Condition> conditions, T t) {
        return conditions.stream().allMatch(it -> matchesCondition(it, t));
    }

    private <T> boolean matchesCondition(Condition condition, T object) {
        Object fieldValue = fieldValueRetriever.retrieveFieldValue(condition.fieldName, object);

        if (fieldValue == null) {
            return false;
        }

        return fieldConditionEvaluator.evaluateCondition(
                condition.operator,
                condition.value,
                fieldValue);
    }
}