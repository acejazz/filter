package com.tanio;

import static com.tanio.SimpleCondition.Operator.*;

class OperatorMapper {
    SimpleCondition.Operator map(String operator) {
        return switch (operator) {
            case "equal" -> EQUAL;
            case "not_equal" -> NOT_EQUAL;
            case "greater_than" -> GREATER_THAN;
            case "less_than" -> LESS_THAN;
            default -> throw new RuntimeException();
        };
    }
}
