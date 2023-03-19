package com.tanio;

import java.util.List;

public class CompoundConditionDto implements ConditionDto {
    private final BooleanOperator operator;
    private final List<ConditionDto> conditions;

    CompoundConditionDto(BooleanOperator operator, List<ConditionDto> conditions) {
        this.operator = operator;
        this.conditions = conditions;
    }

    public BooleanOperator getOperator() {
        return operator;
    }

    public List<ConditionDto> getConditions() {
        return conditions;
    }

    enum BooleanOperator {
        OR, AND, NOT
    }
}