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
                default -> performNot(target, compoundCondition.conditions);
            };
        }

        List<CompoundCondition> compoundConditions = compoundCondition.compoundConditions;

        if (compoundCondition.booleanOperator == BooleanOperator.OR) {
            List<T> result = new ArrayList<>();
            for (CompoundCondition cc : compoundConditions) {
                List<T> ccResult = perform(target, cc);
                for (T t : ccResult) {
                    if (!result.contains(t)) {
                        result.add(t);
                    }
                }
            }
            return result;
        }

        if (compoundCondition.booleanOperator == BooleanOperator.AND) {
            Iterator<CompoundCondition> iterator = compoundConditions.iterator();
            List<T> result = perform(target, iterator.next());
            while (iterator.hasNext()) {
                result.retainAll(perform(target, iterator.next()));
            }
            return result;
        }

        List<T> result = new ArrayList<>(List.copyOf(target));
        for (CompoundCondition cc : compoundConditions) {
            result.removeAll(perform(target, cc));
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