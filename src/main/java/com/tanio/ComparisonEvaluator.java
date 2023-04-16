package com.tanio;

import com.tanio.SimpleCondition.ComparisonOperator;

import java.util.regex.Pattern;

class ComparisonEvaluator {
    boolean compare(ComparisonOperator operator, Object actualValue, Object filterValue) {
        ComparisonOperand actualOperand = new ComparisonOperand(actualValue);
        ComparisonOperand filterOperand = new ComparisonOperand(filterValue);

        if (actualOperand.isNull() ^ filterOperand.isNull()) {
            return compareWithOnlyOneNull(operator);
        }

        if (actualOperand.isNull()) { // filterValue is also null
            return compareWithTwoNulls(operator);
        }

        if (filterOperand.isEnum()) {
            return compareStringifiableOperands(operator, actualOperand, filterOperand);
        }

        if (filterOperand.isString()) {
            if (actualOperand.isBoolean() && !filterOperand.isParseableIntoBoolean()) {
                String message = String.format("[%s] is not a valid boolean value", filterOperand.stringified());
                throw new FilterException(message);
            }

            if (actualOperand.isNumber() && !filterOperand.isParseableIntoNumber()) {
                String message = String.format("[%s] is not a valid numeric value", filterOperand.stringified());
                throw new FilterException(message);
            }

            return compareStringifiableOperands(operator, actualOperand, filterOperand);
        }

        if (filterOperand.isCharacter()) {
            return compareStringifiableOperands(operator, actualOperand, filterOperand);
        }

        if (filterOperand.isBoolean()) {
            return compareBooleanOperands(operator, actualOperand, filterOperand);
        }

        if (filterOperand.isNumber()) {
            return compareNumericOperands(operator, actualOperand, filterOperand);
        }

        throw new FilterException("Filter applicable only to primitives, primitive wrappers and strings");
    }

    private static boolean compareWithOnlyOneNull(ComparisonOperator operator) {
        return switch (operator) {
            case NOT_EQUAL -> true; // Nothing is equal to null, except null
            case EQUAL, LESS_THAN, GREATER_THAN, CONTAINS, NOT_CONTAINS -> false;
        };
    }

    private static boolean compareWithTwoNulls(ComparisonOperator operator) {
        return switch (operator) {
            case EQUAL -> true; // Nothing is equal to null, except null
            case NOT_EQUAL, LESS_THAN, GREATER_THAN, CONTAINS, NOT_CONTAINS -> false;
        };
    }

    private static boolean compareNumericOperands(ComparisonOperator operator,
                                                  ComparisonOperand firstOperand,
                                                  ComparisonOperand secondOperand) {
        Double firstNumberValue = firstOperand.toDouble();
        Double secondNumberValue = secondOperand.toDouble();

        return switch (operator) {
            case EQUAL -> firstNumberValue.equals(secondNumberValue);
            case NOT_EQUAL -> !firstNumberValue.equals(secondNumberValue);
            case LESS_THAN -> firstNumberValue.compareTo(secondNumberValue) < 0;
            case GREATER_THAN -> firstNumberValue.compareTo(secondNumberValue) > 0;
            case CONTAINS -> throw new FilterException("'contains' operator cannot be applied to numbers");
            case NOT_CONTAINS -> throw new FilterException("'not contains' operator cannot be applied to numbers");
        };
    }

    private static boolean compareBooleanOperands(ComparisonOperator operator,
                                                  ComparisonOperand firstOperand,
                                                  ComparisonOperand secondOperand) {
        boolean first = firstOperand.toBoolean();
        boolean second = secondOperand.toBoolean();

        return switch (operator) {
            case EQUAL -> first == second;
            case NOT_EQUAL -> first != second;
            case LESS_THAN -> throw new FilterException("'lower than' operator cannot be applied to booleans");
            case GREATER_THAN -> throw new FilterException("'greater than' operator cannot be applied to booleans");
            case CONTAINS -> throw new FilterException("'contains' operator cannot be applied to booleans");
            case NOT_CONTAINS -> throw new FilterException("'not contains' operator cannot be applied to booleans");
        };
    }

    private static boolean compareStringifiableOperands(ComparisonOperator operator,
                                                        ComparisonOperand firstOperand,
                                                        ComparisonOperand secondOperand) {
        String firstString = firstOperand.stringified();
        String secondString = secondOperand.stringified();

        return switch (operator) {
            case EQUAL -> firstString.equals(secondString);
            case NOT_EQUAL -> !firstString.equals(secondString);
            case LESS_THAN -> firstString.compareTo(secondString) < 0;
            case GREATER_THAN -> firstString.compareTo(secondString) > 0;
            case CONTAINS -> firstString.contains(secondString);
            case NOT_CONTAINS -> !firstString.contains(secondString);
        };
    }

    static class ComparisonOperand {
        private final Object object;
        private final static Pattern NUMERIC_REGEX_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

        ComparisonOperand(Object object) {
            this.object = object;
        }

        boolean isNull() {
            return object == null;
        }

        boolean isEnum() {
            if (isNull()) {
                return false;
            }

            return object.getClass().isEnum();
        }

        boolean isString() {
            if (isNull()) {
                return false;
            }

            return object.getClass().equals(String.class);
        }

        boolean isBoolean() {
            if (isNull()) {
                return false;
            }

            return object.getClass().equals(Boolean.class);
        }

        boolean isNumber() {
            if (isNull()) {
                return false;
            }

            return Number.class.isAssignableFrom(object.getClass());
        }

        boolean isCharacter() {
            if (isNull()) {
                return false;
            }

            return object.getClass().equals(Character.class);
        }

        boolean isParseableIntoBoolean() {
            if (isNull()) {
                return false;
            }

            return stringified().equals("true") || stringified().equals("false");
        }

        boolean isParseableIntoNumber() {
            if (isNull()) {
                return false;
            }

            return NUMERIC_REGEX_PATTERN.matcher(stringified()).matches();
        }

        double toDouble() {
            return ((Number) object).doubleValue();
        }

        String stringified() {
            return String.valueOf(object);
        }

        Boolean toBoolean() {
            return Boolean.valueOf(stringified());
        }
    }
}