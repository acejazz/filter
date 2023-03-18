package com.tanio;

import java.util.List;

class CompoundCondition {
    BooleanOperator booleanOperator;
    List<Condition> conditions;
    List<CompoundCondition> nestedConditions;

    static CompoundCondition or(Condition... conditions) {
        CompoundCondition result = new CompoundCondition();
        result.booleanOperator = BooleanOperator.OR;
        result.conditions = List.of(conditions);
        return result;
    }

    static CompoundCondition and(Condition... conditions) {
        CompoundCondition result = new CompoundCondition();
        result.booleanOperator = BooleanOperator.AND;
        result.conditions = List.of(conditions);
        return result;
    }

    static CompoundCondition not(Condition... conditions) {
        CompoundCondition result = new CompoundCondition();
        result.booleanOperator = BooleanOperator.NOT;
        result.conditions = List.of(conditions);
        return result;
    }

    static CompoundCondition or(CompoundCondition... compoundConditions) {
        CompoundCondition result = new CompoundCondition();
        result.booleanOperator = BooleanOperator.OR;
        result.nestedConditions = List.of(compoundConditions);
        return result;
    }

    static CompoundCondition and(CompoundCondition... compoundConditions) {
        CompoundCondition result = new CompoundCondition();
        result.booleanOperator = BooleanOperator.AND;
        result.nestedConditions = List.of(compoundConditions);
        return result;
    }

    static CompoundCondition not(CompoundCondition... compoundConditions) {
        CompoundCondition result = new CompoundCondition();
        result.booleanOperator = BooleanOperator.NOT;
        result.nestedConditions = List.of(compoundConditions);
        return result;
    }

    static CompoundCondition or(List<Condition> conditions, CompoundCondition... compoundConditions) {
        CompoundCondition result = new CompoundCondition();
        result.booleanOperator = BooleanOperator.OR;
        result.conditions = conditions;
        result.nestedConditions = List.of(compoundConditions);
        return result;
    }

    static CompoundCondition and(List<Condition> conditions, CompoundCondition... compoundConditions) {
        CompoundCondition result = new CompoundCondition();
        result.booleanOperator = BooleanOperator.AND;
        result.conditions = conditions;
        result.nestedConditions = List.of(compoundConditions);
        return result;
    }
}