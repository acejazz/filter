package com.tanio;

import java.util.List;
import java.util.Set;

class Filter {
    private final Mapper mapper = new Mapper();
    private final FieldValueRetriever fieldValueRetriever = new FieldValueRetriever();
    private final FieldConditionEvaluator fieldConditionEvaluator = new FieldConditionEvaluator();

    <T> Set<T> apply(Condition condition, List<T> target) {
        Evaluable evaluable = mapper.map(condition);
        return evaluable.evaluate(target, fieldConditionEvaluator, fieldValueRetriever);
    }
}