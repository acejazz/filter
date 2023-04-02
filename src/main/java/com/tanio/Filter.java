package com.tanio;

import com.tanio.FieldValueRetriever.BooleanFieldNameHandling;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tanio.FieldValueRetriever.BooleanFieldNameHandling.IS;
import static com.tanio.Filter.FieldNameCase.CAMEL_CASE;

class Filter {
    private final FieldValueRetriever retriever;
    private final FieldConditionEvaluator evaluator = new FieldConditionEvaluator();
    private final SetCombiner setCombiner = new SetCombiner();

    public Filter() {
        this(CAMEL_CASE, IS, ".");
    }

    public Filter(FieldNameCase fieldNameCase,
                  BooleanFieldNameHandling booleanFieldNameHandling,
                  String nestingSeparator) {
        GetterMethodNameBuilder getterMethodNameBuilder = null;
        switch (fieldNameCase) {
            case CAMEL_CASE -> getterMethodNameBuilder = new GetterMethodNameBuilderFromCamelCase();
            case SNAKE_CASE -> getterMethodNameBuilder = new GetterMethodNameBuilderFromSnakeCase();
        }

        retriever =
                new FieldValueRetriever(
                        getterMethodNameBuilder,
                        booleanFieldNameHandling,
                        nestingSeparator);
    }

    public <T> Set<T> evaluate(Condition condition, List<T> target) {
        if (condition instanceof CompoundCondition compoundCondition) {
            return handleCompoundCondition(compoundCondition, target);
        }

        return handleSimpleCondition((SimpleCondition) condition, target);
    }

    private <T> Set<T> handleSimpleCondition(SimpleCondition condition, List<T> target) {
        return target.stream()
                .filter(it -> fulfillsSimpleCondition(it, condition))
                .collect(Collectors.toSet());
    }

    private <T> Set<T> handleCompoundCondition(CompoundCondition condition, List<T> target) {
        List<Set<T>> results = condition.getConditions().stream()
                .map(it -> evaluate(it, target))
                .toList();

        return switch (condition.getOperator()) {
            case OR -> setCombiner.or(results);
            case AND -> setCombiner.and(results);
            case NOT -> setCombiner.not(target, results);
        };
    }

    private <T> boolean fulfillsSimpleCondition(T object, SimpleCondition condition) {
        Object fieldValue = retriever.retrieveFieldValue(condition.getFieldName(), object);
        return evaluator.evaluateCondition(condition.getOperator(), fieldValue, condition.getValue());
    }

    enum FieldNameCase {
        SNAKE_CASE, CAMEL_CASE;
    }
}