package com.tanio;

import com.tanio.SimpleCondition.Operator;

class FieldConditionEvaluator {
    boolean evaluateCondition(Operator operator, Object first, Object second) {
        // The second object is the actual field value, so it rules the way the condition is evaluated
        Class<?> secondObjectClass = second.getClass();

        if (secondObjectClass.isEnum()) {
            return evaluateConditionOnStringifiedObjects(operator, first, second);
        }

        if (secondObjectClass.equals(String.class)) {
            return evaluateConditionOnStringifiedObjects(operator, first, second);
        }

        if (secondObjectClass.equals(Character.class)) {
            return evaluateConditionOnStringifiedObjects(operator, first, second);
        }

        if (secondObjectClass.equals(Boolean.class)) {
            return switch (operator) {
                case EQUAL -> first.equals(second);
                case NOT_EQUAL -> !first.equals(second);
                case LOWER_THAN -> throw new FilterException("'lower than' operator cannot be applied to booleans");
                case GREATER_THAN -> throw new FilterException("'greater than' operator cannot be applied to booleans");
            };
        }

        if (Number.class.isAssignableFrom(secondObjectClass)) {
            Double firstNumberValue = ((Number) first).doubleValue();
            Double secondNumberValue = ((Number) second).doubleValue();

            return switch (operator) {
                case EQUAL -> firstNumberValue.equals(secondNumberValue);
                case NOT_EQUAL -> !firstNumberValue.equals(secondNumberValue);
                case LOWER_THAN -> firstNumberValue.compareTo(secondNumberValue) < 0;
                case GREATER_THAN -> firstNumberValue.compareTo(secondNumberValue) > 0;
            };
        }

        throw new FilterException("Filter applicable only to primitives, primitive wrappers and strings");
    }

    private static boolean evaluateConditionOnStringifiedObjects(Operator conditionOperator,
                                                                 Object first,
                                                                 Object second) {
        String firstString = String.valueOf(first);
        String secondString = String.valueOf(second);

        return switch (conditionOperator) {
            case EQUAL -> firstString.equals(secondString);
            case NOT_EQUAL -> !firstString.equals(secondString);
            case LOWER_THAN -> firstString.compareTo(secondString) < 0;
            case GREATER_THAN -> firstString.compareTo(secondString) > 0;
        };
    }
}