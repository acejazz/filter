package com.tanio;

import java.util.List;
import java.util.stream.Collectors;

class SimpleCondition implements Evaluable {
    private final String fieldName;
    private final Operator operator;
    private final Object value;

    private final FieldConditionEvaluator fieldConditionEvaluator = new FieldConditionEvaluator();
    private final FieldValueRetriever fieldValueRetriever = new FieldValueRetriever();

    SimpleCondition(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public <T> List<T> evaluate(List<T> target) {
        return target.stream()
                .filter(this::matchesCondition)
                .collect(Collectors.toList());
    }

    private <T> boolean matchesCondition(T object) {
        Object fieldValue = fieldValueRetriever.retrieveFieldValue(fieldName, object);

        if (fieldValue == null) {
            return false;
        }

        return fieldConditionEvaluator.evaluateCondition(
                operator,
                value,
                fieldValue);
    }

    enum Operator {
        EQUAL, LOWER_THAN, GREATER_THAN, NOT_EQUAL
    }
}