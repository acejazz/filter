package com.tanio;

import com.tanio.SimpleCondition.ComparisonOperator;

import static com.tanio.CompoundCondition.BooleanOperator.*;
import static com.tanio.SimpleCondition.ComparisonOperator.*;

class StringToOperatorMapper {
    CompoundCondition.BooleanOperator mapToBooleanOperator(String text) {
        return switch (text) {
            case "and" -> AND;
            case "or" -> OR;
            case "not" -> NOT;
            default -> throw new RuntimeException();
        };
    }

    ComparisonOperator mapToComparisonOperator(String text) {
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