package com.tanio;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class EvaluableSimpleCondition implements Evaluable {
    private final String fieldName;
    private final Operator operator;
    private final Object value;

    EvaluableSimpleCondition(String fieldName,
                             Operator operator,
                             Object value) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public <T> Set<T> evaluate(List<T> target, FieldConditionEvaluator evaluator, FieldValueRetriever retriever) {
        return target.stream()
                .filter(it -> matchesCondition(it, evaluator, retriever))
                .collect(Collectors.toSet());
    }

    private <T> boolean matchesCondition(T object, FieldConditionEvaluator evaluator, FieldValueRetriever retriever) {
        Object fieldValue = retriever.retrieveFieldValue(fieldName, object);

        if (fieldValue == null) {
            return false;
        }

        return evaluator.evaluateCondition(operator, fieldValue, value);
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

    enum Operator {
        EQUAL, LESS_THAN, GREATER_THAN, NOT_EQUAL
    }
}