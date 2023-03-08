package com.tanio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

class Filter {
    private final FieldConditionEvaluator fieldConditionEvaluator = new FieldConditionEvaluator();
    private final FieldValueRetriever fieldValueRetriever = new FieldValueRetriever();

    <T> List<T> perform(List<T> target, CompoundCondition compoundCondition) {
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
            case NOT -> not(target, nestedResults);
        };
    }

    private static <T> List<T> and(List<List<T>> resultLists) {
        Iterator<List<T>> iterator = resultLists.iterator();
        List<T> firstResultList = iterator.next();
        while (iterator.hasNext()) {
            firstResultList.retainAll(iterator.next());
        }
        return firstResultList;
    }

    private static <T> List<T> or(List<List<T>> resultLists) {
        List<T> result = new ArrayList<>();
        for (List<T> resultList : resultLists) {
            for (T element : resultList) {
                if (!result.contains(element)) {
                    result.add(element);
                }
            }
        }
        return result;
    }

    private static <T> List<T> not(List<T> target, List<List<T>> resultLists) {
        List<T> result = new ArrayList<>(List.copyOf(target));
        for (List<T> list : resultLists) {
            result.removeAll(list);
        }
        return result;
    }

    <T> List<T> perform(List<T> target, Condition condition) {
        return target.stream()
                .filter(it -> matchesCondition(condition, it))
                .collect(Collectors.toList());
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