package com.tanio;

import java.util.Objects;
import java.util.Set;

class CompoundCondition implements Condition {
    private final BooleanOperator operator;
    private final Set<Condition> conditions;

    CompoundCondition(BooleanOperator operator, Set<Condition> conditions) {
        this.operator = operator;
        this.conditions = conditions;
    }

    BooleanOperator getOperator() {
        return operator;
    }

    Set<Condition> getConditions() {
        return conditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompoundCondition condition = (CompoundCondition) o;
        return operator == condition.operator && conditions.equals(condition.conditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, conditions);
    }

    public static CompoundCondition or(Condition... conditions) {
        return new CompoundCondition(BooleanOperator.OR, Set.of(conditions));
    }

    public static CompoundCondition and(Condition... conditions) {
        return new CompoundCondition(BooleanOperator.AND, Set.of(conditions));
    }

    public static CompoundCondition not(Condition... conditions) {
        return new CompoundCondition(BooleanOperator.NOT, Set.of(conditions));
    }

    enum BooleanOperator {
        OR, AND, NOT
    }
}