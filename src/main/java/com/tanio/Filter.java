package com.tanio;

import java.util.List;
import java.util.Set;

class Filter {
    private final EvaluationInfra evaluationInfra;
    private final Mapper mapper = new Mapper();

    public Filter() {
        evaluationInfra = new EvaluationInfra();
        evaluationInfra.retriever = new FieldValueRetriever();
        evaluationInfra.evaluator = new FieldConditionEvaluator();
        evaluationInfra.setCombiner = new SetCombiner();
    }

    <T> Set<T> apply(Condition condition, List<T> target) {
        Evaluable evaluable = mapper.map(condition);
        return evaluable.evaluate(target, evaluationInfra);
    }
}