package com.tanio;

import java.util.List;

public class Filter {
    Decorator decorator = new Decorator();

    <T> List<T> apply(ConditionDto conditionDto, List<T> target) {
        CompoundCondition compoundCondition =
                (CompoundCondition) decorator.decorate(conditionDto);
        return compoundCondition.evaluate(target);
    }
}