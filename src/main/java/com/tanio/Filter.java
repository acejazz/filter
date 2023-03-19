package com.tanio;

import java.util.List;
import java.util.Set;

class Filter {
    Mapper mapper = new Mapper();
    private final FieldValueRetriever fieldValueRetriever = new FieldValueRetriever();
    private final FieldConditionEvaluator fieldConditionEvaluator = new FieldConditionEvaluator();

    <T> Set<T> apply(ConditionDto conditionDto, List<T> target) {
        CompoundCondition compoundCondition = (CompoundCondition) mapper.map(conditionDto);
        return compoundCondition.evaluate(target, fieldConditionEvaluator, fieldValueRetriever);
    }
}