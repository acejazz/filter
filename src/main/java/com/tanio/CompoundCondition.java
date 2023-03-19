package com.tanio;

import java.util.Set;

class CompoundCondition implements Condition {
    private final BooleanOperator operator;
    private final Set<Condition> conditions;

    CompoundCondition(BooleanOperator operator, Set<Condition> conditions) {
        this.operator = operator;
        this.conditions = conditions;
    }

    public BooleanOperator getOperator() {
        return operator;
    }

    public Set<Condition> getConditions() {
        return conditions;
    }

    enum BooleanOperator {
        OR, AND, NOT
    }
}