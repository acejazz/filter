package com.tanio;

import com.tanio.Condition.Operator;

class FieldConditionEvaluator {
    boolean evaluateCondition(Operator conditionOperator, Object first, Object second) {
        // TODO: Check they are the same instances of the same class?
        Class<?> firstObjectClass = first.getClass();

        if (second.getClass().isEnum()) {
            String firstString = first.toString();
            String secondString = second.toString();
            return switch (conditionOperator) {
                case EQUAL -> firstString.equals(secondString);
                case NOT_EQUAL -> !firstString.equals(secondString);
                case LOWER_THAN -> firstString.compareTo(secondString) <= -1;
            };
        }

        if (firstObjectClass.equals(String.class)) {
            return switch (conditionOperator) {
                case EQUAL -> first.equals(second);
                case NOT_EQUAL -> !first.equals(second);
                case LOWER_THAN -> ((String) first).compareTo((String) second) <= -1;
            };
        }

        if (firstObjectClass.equals(Boolean.class)) {
            return switch (conditionOperator) {
                case EQUAL -> first.equals(second);
                case NOT_EQUAL -> !first.equals(second);
                case LOWER_THAN -> throw new FilterException("'lower than' operator cannot be applied to booleans");
            };
        }

        if (firstObjectClass.equals(Character.class)) {
            return switch (conditionOperator) {
                case EQUAL -> first.equals(second);
                case NOT_EQUAL -> !first.equals(second);
                case LOWER_THAN -> ((Character) first).compareTo((Character) second) <= -1;
            };
        }

        if (Number.class.isAssignableFrom(firstObjectClass)) {
            Double firstNumberValue = ((Number) first).doubleValue();
            Double secondNumberValue = ((Number) second).doubleValue();

            return switch (conditionOperator) {
                case EQUAL -> firstNumberValue.equals(secondNumberValue);
                case NOT_EQUAL -> !firstNumberValue.equals(secondNumberValue);
                case LOWER_THAN -> firstNumberValue.compareTo(secondNumberValue) <= -1;
            };
        }

        throw new FilterException("Filter applicable only to primitives, primitive wrappers and strings");
    }
}