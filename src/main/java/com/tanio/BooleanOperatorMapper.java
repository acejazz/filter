package com.tanio;

import static com.tanio.CompoundCondition.BooleanOperator.*;

class BooleanOperatorMapper {
    CompoundCondition.BooleanOperator mapToOperator(String fieldName) {
        return switch (fieldName) {
            case "and" -> AND;
            case "or" -> OR;
            case "not" -> NOT;
            default -> throw new RuntimeException();
        };
    }
}
