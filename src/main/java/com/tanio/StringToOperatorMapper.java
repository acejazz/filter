package com.tanio;

import com.tanio.CompoundCondition.BooleanOperator;
import com.tanio.SimpleCondition.ComparisonOperator;

import static com.tanio.ArgumentChecks.checkNotNull;
import static com.tanio.CompoundCondition.BooleanOperator.*;
import static com.tanio.SimpleCondition.ComparisonOperator.*;

class StringToOperatorMapper {
    static final String STRING_AND = "and";
    static final String STRING_OR = "or";
    static final String STRING_NOT = "not";
    static final String STRING_FIELD_NAME = "fieldName";
    static final String STRING_OPERATOR = "operator";
    static final String STRING_VALUE = "value";

    BooleanOperator mapToBooleanOperator(String text) {
        checkNotNull(text, "text");

        return switch (text) {
            case STRING_AND -> AND;
            case STRING_OR -> OR;
            case STRING_NOT -> NOT;
            default -> throw new RuntimeException();
        };
    }

    ComparisonOperator mapToComparisonOperator(String text) {
        checkNotNull(text, "text");

        return switch (text) {
            case "equal" -> EQUAL;
            case "not_equal" -> NOT_EQUAL;
            case "greater_than" -> GREATER_THAN;
            case "less_than" -> LESS_THAN;
            case "contains" -> CONTAINS;
            case "not_contains" -> NOT_CONTAINS;
            default -> throw new FilterException("Invalid operator");
        };
    }
}