package com.tanio;

import com.tanio.CompoundCondition.BooleanOperator;
import com.tanio.SimpleCondition.Operator;

import java.util.stream.Collectors;

import static com.tanio.CompoundCondition.BooleanOperator.*;
import static com.tanio.SimpleCondition.Operator.*;

public class Decorator {
    private final FieldValueRetriever fieldValueRetriever = new FieldValueRetriever();
    private final FieldConditionEvaluator fieldConditionEvaluator = new FieldConditionEvaluator();

    Evaluable decorate(ConditionDto condition) {
        if (condition instanceof CompoundConditionDto compoundConditionDto) {
            return new CompoundCondition(
                    map(compoundConditionDto.getOperator()),
                    compoundConditionDto.getConditions().stream()
                            .map(this::decorate)
                            .collect(Collectors.toSet()));
        }

        SimpleConditionDto simpleConditionDto = (SimpleConditionDto) condition;
        return new SimpleCondition(
                simpleConditionDto.getFieldName(),
                map(simpleConditionDto.getOperator()),
                simpleConditionDto.getValue(),
                fieldConditionEvaluator,
                fieldValueRetriever);
    }

    BooleanOperator map(CompoundConditionDto.BooleanOperator operator) {
        return switch (operator) {
            case AND -> AND;
            case OR -> OR;
            case NOT -> NOT;
        };
    }

    Operator map(SimpleConditionDto.Operator operator) {
        return switch (operator) {
            case EQUAL -> EQUAL;
            case NOT_EQUAL -> NOT_EQUAL;
            case GREATER_THAN -> GREATER_THAN;
            case LOWER_THAN -> LOWER_THAN;
        };
    }
}