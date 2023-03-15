package com.tanio;

import com.tanio.Condition.Operator;

class FieldConditionEvaluator {
    boolean evaluateCondition(Operator operator, Object first, Object second) {
        // TODO: Check they are the same instances of the same class?
        Class<?> firstObjectClass = first.getClass();

        if (second.getClass().isEnum()) {
            String firstString = String.valueOf(first);
            String secondString = String.valueOf(second);
            return evaluateConditionForStrings(operator, firstString, secondString);
        }

        if (firstObjectClass.equals(String.class)) {
            String firstString = String.valueOf(first);
            String secondString = String.valueOf(second);
            return evaluateConditionForStrings(operator, firstString, secondString);
        }

        if (firstObjectClass.equals(Character.class)) {
            String firstString = String.valueOf(first);
            String secondString = String.valueOf(second);
            return evaluateConditionForStrings(operator, firstString, secondString);
        }

        if (firstObjectClass.equals(Boolean.class)) {
            return switch (operator) {
                case EQUAL -> first.equals(second);
                case NOT_EQUAL -> !first.equals(second);
                case LOWER_THAN -> throw new FilterException("'lower than' operator cannot be applied to booleans");
            };
        }

        if (Number.class.isAssignableFrom(firstObjectClass)) {
            Double firstNumberValue = ((Number) first).doubleValue();
            Double secondNumberValue = ((Number) second).doubleValue();

            return switch (operator) {
                case EQUAL -> firstNumberValue.equals(secondNumberValue);
                case NOT_EQUAL -> !firstNumberValue.equals(secondNumberValue);
                case LOWER_THAN -> firstNumberValue.compareTo(secondNumberValue) <= -1;
            };
        }

        throw new FilterException("Filter applicable only to primitives, primitive wrappers and strings");
    }

    private static boolean evaluateConditionForStrings(Operator conditionOperator, String firstString, String secondString) {
        return switch (conditionOperator) {
            case EQUAL -> firstString.equals(secondString);
            case NOT_EQUAL -> !firstString.equals(secondString);
            case LOWER_THAN -> firstString.compareTo(secondString) <= -1;
        };
    }
}