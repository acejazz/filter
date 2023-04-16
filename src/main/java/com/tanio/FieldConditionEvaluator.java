package com.tanio;

import com.tanio.SimpleCondition.ComparisonOperator;

import java.util.regex.Pattern;

class FieldConditionEvaluator {
    private final static Pattern NUMERIC_REGEX_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

    boolean evaluateCondition(ComparisonOperator operator, Object actualValue, Object filterValue) {
        if ((actualValue == null) ^ (filterValue == null)) {
            return evaluateConditionForOneNull(operator);
        }

        if (actualValue == null) { // filterValue is also null
            return evaluateConditionOnBothNulls(operator);
        }

        Class<?> actualValueClass = actualValue.getClass();
        Class<?> filterValueClass = filterValue.getClass();

        if (filterValueClass.isEnum()) {
            return evaluateConditionOnStringifiableObjects(operator, actualValue, filterValue);
        }

        if (filterValueClass.equals(String.class)) {
            if (actualValueClass.equals(Boolean.class)) {
                ensureFilterValueStringHoldsBoolean((String) filterValue);
            }

            if (Number.class.isAssignableFrom(actualValueClass)) {
                ensureFilterValueStringHoldsNumber((String) filterValue);
            }

            return evaluateConditionOnStringifiableObjects(operator, actualValue, filterValue);
        }

        if (filterValueClass.equals(Character.class)) {
            return evaluateConditionOnStringifiableObjects(operator, actualValue, filterValue);
        }

        if (filterValueClass.equals(Boolean.class)) {
            return evaluateConditionOnBooleans(operator, actualValue, filterValue);
        }

        if (Number.class.isAssignableFrom(filterValueClass)) {
            return evaluateConditionOnNumbers(operator, (Number) actualValue, (Number) filterValue);
        }

        throw new FilterException("Filter applicable only to primitives, primitive wrappers and strings");
    }

    private static void ensureFilterValueStringHoldsBoolean(String filterValueString) {
        boolean isParseableToBoolean = filterValueString.equals("true") || filterValueString.equals("false");
        if (!isParseableToBoolean) {
            String message = String.format("[%s] is not a valid boolean value", filterValueString);
            throw new FilterException(message);
        }
    }

    private static void ensureFilterValueStringHoldsNumber(String filterValueString) {
        boolean isParseableToNumber = NUMERIC_REGEX_PATTERN.matcher(filterValueString).matches();
        if (!isParseableToNumber) {
            String message = String.format("[%s] is not a valid numeric value", filterValueString);
            throw new FilterException(message);
        }
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