package com.tanio;

import com.tanio.Condition.Operator;

class FieldConditionEvaluator {
    boolean evaluateCondition(Operator conditionOperator, Object first, Object second) {
        Class<?> conditionValueClass = first.getClass();

        if (conditionValueClass.equals(String.class)) {
            if (conditionOperator.equals(Operator.EQUAL)) {
                return first.equals(second);
            }
            return !first.equals(second);
        }

        if (conditionValueClass.equals(Boolean.class)) {
            if (conditionOperator.equals(Operator.EQUAL)) {
                return first.equals(second);
            }
            return !first.equals(second);
        }

        if (conditionValueClass.equals(Character.class)) {
            if (conditionOperator.equals(Operator.EQUAL)) {
                return first.equals(second);
            }
            return !first.equals(second);
        }

        if (Number.class.isAssignableFrom(conditionValueClass)) {
            Double conditionNumberValue = ((Number) first).doubleValue();
            Double fieldNumberValue = ((Number) second).doubleValue();
            if (conditionOperator.equals(Operator.EQUAL)) {
                return conditionNumberValue.equals(fieldNumberValue);
            }
            return !conditionNumberValue.equals(fieldNumberValue);
        }

        throw new FilterException("Filter applicable only to primitives, primitive wrappers and strings");
    }
}