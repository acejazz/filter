package com.tanio;

public class SimpleCondition implements Condition {
    private final String fieldName;
    private final Operator operator;
    private final Object value;

    SimpleCondition(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
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
