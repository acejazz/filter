package com.tanio;

import com.tanio.EvaluableCompoundCondition.BooleanOperator;
import com.tanio.EvaluableSimpleCondition.Operator;

import java.util.stream.Collectors;

import static com.tanio.EvaluableCompoundCondition.BooleanOperator.*;
import static com.tanio.EvaluableSimpleCondition.Operator.*;

class Mapper {
    Evaluable map(Condition condition) {
        if (condition instanceof CompoundCondition compoundCondition) {
            return new EvaluableCompoundCondition(
                    map(compoundCondition.getOperator()),
                    compoundCondition.getConditions().stream()
                            .map(this::map)
                            .collect(Collectors.toSet()));
        }

        SimpleCondition simpleConditionDto = (SimpleCondition) condition;
        return new EvaluableSimpleCondition(
                simpleConditionDto.getFieldName(),
                map(simpleConditionDto.getOperator()),
                simpleConditionDto.getValue());
    }

    BooleanOperator map(CompoundCondition.BooleanOperator operator) {
        return switch (operator) {
            case AND -> AND;
            case OR -> OR;
            case NOT -> NOT;
        };
    }

    Operator map(SimpleCondition.Operator operator) {
        return switch (operator) {
            case EQUAL -> EQUAL;
            case NOT_EQUAL -> NOT_EQUAL;
            case GREATER_THAN -> GREATER_THAN;
            case LESS_THAN -> LESS_THAN;
        };
    }
}