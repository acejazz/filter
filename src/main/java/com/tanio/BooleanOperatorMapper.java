package com.tanio;

import com.tanio.CompoundCondition.BooleanOperator;

import static com.tanio.CompoundCondition.BooleanOperator.*;

class BooleanOperatorMapper {
    BooleanOperator mapToOperator(String fieldName) {
        return switch (fieldName) {
            case "and" -> AND;
            case "or" -> OR;
            case "not" -> NOT;
            default -> throw new RuntimeException();
        };
    }
}