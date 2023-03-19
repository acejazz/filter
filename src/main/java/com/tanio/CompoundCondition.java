package com.tanio;

import java.util.List;

public class CompoundCondition implements Condition {
    private final BooleanOperator operator;
    private final List<Condition> conditions;

    CompoundCondition(BooleanOperator operator, List<Condition> conditions) {
        this.operator = operator;
        this.conditions = conditions;
    }

    public BooleanOperator getOperator() {
        return operator;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    enum BooleanOperator {
        OR, AND, NOT
    }
}