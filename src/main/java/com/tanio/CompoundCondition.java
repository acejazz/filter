package com.tanio;

import java.util.List;

class CompoundCondition {
    BooleanOperator booleanOperator;
    List<Condition> conditions;
    List<CompoundCondition> nestedConditions;
}
