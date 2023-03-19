package com.tanio;

import java.util.List;
import java.util.Set;

class Filter {
    Decorator decorator = new Decorator();

    <T> Set<T> apply(ConditionDto conditionDto, List<T> target) {
        CompoundCondition compoundCondition = (CompoundCondition) decorator.decorate(conditionDto);
        return compoundCondition.evaluate(target);
    }
}