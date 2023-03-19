package com.tanio;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Filter {
    private final Mapper mapper = new Mapper();
    private final FieldValueRetriever fieldValueRetriever = new FieldValueRetriever();
    private final FieldConditionEvaluator fieldConditionEvaluator = new FieldConditionEvaluator();
    private final SetCombiner setCombiner = new SetCombiner();

    <T> Set<T> apply(Condition condition, List<T> target) {
        Evaluable evaluable = mapper.map(condition);
        return evaluate(evaluable, target);
    }

    <T> Set<T> evaluate(Evaluable condition, List<T> target) {
        if (condition instanceof EvaluableCompoundCondition compoundCondition) {
            List<Set<T>> results = compoundCondition.getConditions().stream()
                    .map(it -> evaluate(it, target))
                    .collect(Collectors.toList());

            return switch (compoundCondition.getOperator()) {
                case OR -> setCombiner.or(results);
                case AND -> setCombiner.and(results);
                case NOT -> setCombiner.not(target, results);
            };
        }

        EvaluableSimpleCondition simpleCondition = (EvaluableSimpleCondition) condition;
        return target.stream()
                .filter(it -> matchesCondition(it, simpleCondition))
                .collect(Collectors.toSet());
    }

    private <T> boolean matchesCondition(T object, EvaluableSimpleCondition condition) {
        Object fieldValue = fieldValueRetriever.retrieveFieldValue(condition.getFieldName(), object);

        if (fieldValue == null) {
            return false;
        }

        return fieldConditionEvaluator.evaluateCondition(condition.getOperator(), fieldValue, condition.getValue());
    }
}