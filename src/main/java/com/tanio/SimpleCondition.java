package com.tanio;

import java.util.Objects;

import static com.tanio.ArgumentChecks.checkNotNull;
import static com.tanio.SimpleCondition.ComparisonOperator.*;

class SimpleCondition implements Condition {
    private String fieldName;
    private ComparisonOperator operator;
    private Object value;

    public SimpleCondition() {
    }

    SimpleCondition(String fieldName,
                    ComparisonOperator operator,
                    Object value /* nullable */) {
        checkNotNull(fieldName, "fieldName");
        checkNotNull(operator, "operator");

        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    String getFieldName() {
        return fieldName;
    }

    ComparisonOperator getOperator() {
        return operator;
    }

    Object getValue() {
        return value;
    }

    public void setFieldName(String fieldName) {
        checkNotNull(fieldName, "fieldName");
        this.fieldName = fieldName;
    }

    public void setOperator(ComparisonOperator operator) {
        checkNotNull(operator, "operator");
        this.operator = operator;
    }

    public void setValue(Object value /* nullable */) {
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
        String template = "{%s %s %s}";
        return String.format(template, fieldName, operator, value);
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

    enum ComparisonOperator {
        EQUAL,
        NOT_EQUAL,
        LESS_THAN,
        GREATER_THAN,
        CONTAINS,
        NOT_CONTAINS
    }
}