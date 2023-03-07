package com.tanio;

import java.util.List;
import java.util.stream.Collectors;

class Filter {
    private final FieldConditionEvaluator fieldConditionEvaluator = new FieldConditionEvaluator();
    private final FieldValueRetriever fieldValueRetriever = new FieldValueRetriever();

    <T> List<T> perform(List<T> target, CompoundCondition compoundCondition) {
        return switch (compoundCondition.booleanOperator) {
            case OR -> performOr(target, compoundCondition.conditions);
            case AND -> performAnd(target, compoundCondition.conditions);
            default -> performNot(target, compoundCondition.conditions);
        };
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