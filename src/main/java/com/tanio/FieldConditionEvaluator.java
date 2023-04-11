package com.tanio;

import com.tanio.SimpleCondition.ComparisonOperator;

class FieldConditionEvaluator {
    boolean evaluateCondition(ComparisonOperator operator, Object first, Object second) {
        if ((first == null) ^ (second == null)) {
            return evaluateConditionForOneNull(operator);
        }

        if (first == null) {
            // second is also null
            return evaluateConditionOnBothNulls(operator);
        }

        // The second object is the actual field value, so it rules the way the condition is evaluated
        Class<?> secondObjectClass = second.getClass();

        if (secondObjectClass.isEnum()) {
            return evaluateConditionOnStringifiableObjects(operator, first, second);
        }

        if (secondObjectClass.equals(String.class)) {
            return evaluateConditionOnStringifiableObjects(operator, first, second);
        }

        if (secondObjectClass.equals(Character.class)) {
            return evaluateConditionOnStringifiableObjects(operator, first, second);
        }

        if (secondObjectClass.equals(Boolean.class)) {
            return evaluateConditionOnBooleans(operator, first, second);
        }

        if (Number.class.isAssignableFrom(secondObjectClass)) {
            return evaluateConditionOnNumbers(operator, (Number) first, (Number) second);
        }

        throw new FilterException("Filter applicable only to primitives, primitive wrappers and strings");
    }

    private static boolean evaluateConditionForOneNull(ComparisonOperator operator) {
        return switch (operator) {
            case NOT_EQUAL -> true;
            case EQUAL, LESS_THAN, GREATER_THAN, CONTAINS, NOT_CONTAINS -> false;
        };
    }

    private static boolean evaluateConditionOnBothNulls(ComparisonOperator operator) {
        return switch (operator) {
            case EQUAL -> true;
            case NOT_EQUAL, LESS_THAN, GREATER_THAN, CONTAINS, NOT_CONTAINS -> false;
        };
    }

    private static boolean evaluateConditionOnNumbers(ComparisonOperator operator,
                                                      Number first,
                                                      Number second) {
        Double firstNumberValue = first.doubleValue();
        Double secondNumberValue = second.doubleValue();

        return switch (operator) {
            case EQUAL -> firstNumberValue.equals(secondNumberValue);
            case NOT_EQUAL -> !firstNumberValue.equals(secondNumberValue);
            case LESS_THAN -> firstNumberValue.compareTo(secondNumberValue) < 0;
            case GREATER_THAN -> firstNumberValue.compareTo(secondNumberValue) > 0;
            case CONTAINS -> throw new FilterException("'contains' operator cannot be applied to numbers");
            case NOT_CONTAINS -> throw new FilterException("'not contains' operator cannot be applied to numbers");
        };
    }

    private static boolean evaluateConditionOnBooleans(ComparisonOperator operator,
                                                       Object first,
                                                       Object second) {
        return switch (operator) {
            case EQUAL -> first.equals(second);
            case NOT_EQUAL -> !first.equals(second);
            case LESS_THAN -> throw new FilterException("'lower than' operator cannot be applied to booleans");
            case GREATER_THAN -> throw new FilterException("'greater than' operator cannot be applied to booleans");
            case CONTAINS -> throw new FilterException("'contains' operator cannot be applied to booleans");
            case NOT_CONTAINS -> throw new FilterException("'not contains' operator cannot be applied to booleans");
        };
    }

    private static boolean evaluateConditionOnStringifiableObjects(ComparisonOperator operator,
                                                                   Object first,
                                                                   Object second) {
        String firstString = String.valueOf(first);
        String secondString = String.valueOf(second);

        return switch (operator) {
            case EQUAL -> firstString.equals(secondString);
            case NOT_EQUAL -> !firstString.equals(secondString);
            case LESS_THAN -> firstString.compareTo(secondString) < 0;
            case GREATER_THAN -> firstString.compareTo(secondString) > 0;
            case CONTAINS -> firstString.contains(secondString);
            case NOT_CONTAINS -> !firstString.contains(secondString);
        };
    }
}