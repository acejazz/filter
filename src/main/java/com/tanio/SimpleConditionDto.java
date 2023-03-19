package com.tanio;

public class SimpleConditionDto implements ConditionDto{
    private final String fieldName;
    private final Operator operator;
    private final Object value;

    SimpleConditionDto(String fieldName, Operator operator, Object value) {
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
        EQUAL, LOWER_THAN, GREATER_THAN, NOT_EQUAL
    }
}
