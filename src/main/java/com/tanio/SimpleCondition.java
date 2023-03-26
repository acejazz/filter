package com.tanio;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleCondition that = (SimpleCondition) o;
        return Objects.equals(fieldName, that.fieldName) && operator == that.operator && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, operator, value);
    }

    @Override
    public String toString() {
        return "SimpleCondition{" +
                "fieldName='" + fieldName + '\'' +
                ", operator=" + operator +
                ", value=" + value +
                '}';
    }

    enum Operator {
        EQUAL, LESS_THAN, GREATER_THAN, NOT_EQUAL
    }
}