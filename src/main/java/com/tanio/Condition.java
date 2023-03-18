package com.tanio;

class Condition {
    private final String fieldName;
    private final Operator operator;
    private final Object value;

    public Condition(String fieldName, Operator operator, Object value) {
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

    public static Condition has(String fieldName, Operator operator, Object value) {
        return new Condition(fieldName, operator, value);
    }

    enum Operator {
        EQUAL, LOWER_THAN, GREATER_THAN, NOT_EQUAL
    }
}