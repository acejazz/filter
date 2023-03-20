package com.tanio;

class SimpleCondition implements Condition {
    private String fieldName;
    private Operator operator;
    private Object value;

    public SimpleCondition() {
    }

    SimpleCondition(String fieldName,
                    Operator operator,
                    Object value) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    String getFieldName() {
        return fieldName;
    }

    Operator getOperator() {
        return operator;
    }

    Object getValue() {
        return value;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    enum Operator {
        EQUAL, LESS_THAN, GREATER_THAN, NOT_EQUAL
    }
}