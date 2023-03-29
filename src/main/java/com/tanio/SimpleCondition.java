package com.tanio;

import java.util.Objects;

import static com.tanio.SimpleCondition.Operator.*;

class SimpleCondition implements Condition {
    private String fieldName;
    private Operator operator;
    private Object value;

    public SimpleCondition() {
    }

    SimpleCondition(String fieldName, Operator operator, Object value) {
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

    public static SimpleCondition equal(String fieldName, Object value) {
        return new SimpleCondition(fieldName, EQUAL, value);
    }

    public static SimpleCondition notEqual(String fieldName, Object value) {
        return new SimpleCondition(fieldName, NOT_EQUAL, value);
    }

    public static SimpleCondition greaterThan(String fieldName, Object value) {
        return new SimpleCondition(fieldName, GREATER_THAN, value);
    }

    public static SimpleCondition lessThan(String fieldName, Object value) {
        return new SimpleCondition(fieldName, LESS_THAN, value);
    }

    public static SimpleCondition contains(String fieldName, Object value) {
        return new SimpleCondition(fieldName, CONTAINS, value);
    }

    public static SimpleCondition notContains(String fieldName, Object value) {
        return new SimpleCondition(fieldName, NOT_CONTAINS, value);
    }

    enum Operator {
        EQUAL,
        NOT_EQUAL,
        LESS_THAN,
        GREATER_THAN,
        CONTAINS,
        NOT_CONTAINS
    }
}