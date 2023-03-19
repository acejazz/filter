package com.tanio;

import java.util.List;
import java.util.stream.Collectors;

class SimpleCondition implements Evaluable {
    private final String fieldName;
    private final Operator operator;
    private final Object value;

    private final FieldConditionEvaluator fieldConditionEvaluator;
    private final FieldValueRetriever fieldValueRetriever;

    SimpleCondition(String fieldName,
                    Operator operator,
                    Object value,
                    FieldConditionEvaluator fieldConditionEvaluator,
                    FieldValueRetriever fieldValueRetriever) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
        this.fieldConditionEvaluator = fieldConditionEvaluator;
        this.fieldValueRetriever = fieldValueRetriever;
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

    public String getFieldName() {
        return fieldName;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    public FieldConditionEvaluator getFieldConditionEvaluator() {
        return fieldConditionEvaluator;
    }

    public FieldValueRetriever getFieldValueRetriever() {
        return fieldValueRetriever;
    }

    enum Operator {
        EQUAL, LOWER_THAN, GREATER_THAN, NOT_EQUAL
    }
}