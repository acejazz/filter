package com.tanio;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class EvaluableCompoundCondition implements Evaluable {
    private final BooleanOperator operator;
    private final Set<Evaluable> conditions;

    EvaluableCompoundCondition(BooleanOperator operator, Set<Evaluable> conditions) {
        this.operator = operator;
        this.conditions = conditions;
    }

    @Override
    public <T> Set<T> evaluate(List<T> target, EvaluationInfra evaluationInfra) {
        List<Set<T>> results = conditions.stream()
                .map(it -> it.evaluate(target, evaluationInfra))
                .collect(Collectors.toList());

        SetCombiner setCombiner = evaluationInfra.setCombiner;
        return switch (operator) {
            case OR -> setCombiner.or(results);
            case AND -> setCombiner.and(results);
            case NOT -> setCombiner.not(target, results);
        };
    }

    public BooleanOperator getOperator() {
        return operator;
    }

    public Set<Evaluable> getConditions() {
        return conditions;
    }

    enum BooleanOperator {
        OR, AND, NOT
    }
}