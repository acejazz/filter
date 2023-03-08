package com.tanio;

import com.tanio.Condition.Operator;

class FieldConditionEvaluator {
    boolean evaluateCondition(Operator conditionOperator, Object first, Object second) {
        Class<?> firstObjectClass = first.getClass();

        if (firstObjectClass.equals(String.class)) {
            return switch (conditionOperator) {
                case EQUAL -> first.equals(second);
                case NOT_EQUAL -> !first.equals(second);
            };
        }

        if (firstObjectClass.equals(Boolean.class)) {
            return switch (conditionOperator) {
                case EQUAL -> first.equals(second);
                case NOT_EQUAL -> !first.equals(second);
            };
        }

        if (firstObjectClass.equals(Character.class)) {
            return switch (conditionOperator) {
                case EQUAL -> first.equals(second);
                case NOT_EQUAL -> !first.equals(second);
            };
        }

        if (Number.class.isAssignableFrom(firstObjectClass)) {
            Double firstNumberValue = ((Number) first).doubleValue();
            Double secondNumberValue = ((Number) second).doubleValue();

            return switch (conditionOperator) {
                case EQUAL -> firstNumberValue.equals(secondNumberValue);
                case NOT_EQUAL -> !firstNumberValue.equals(secondNumberValue);
            };
        }

        throw new FilterException("Filter applicable only to primitives, primitive wrappers and strings");
    }
}