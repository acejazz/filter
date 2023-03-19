package com.tanio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static com.tanio.EvaluableSimpleCondition.Operator.*;
import static org.assertj.core.api.Assertions.assertThat;

class EvaluableSimpleConditionTest {
    EvaluationInfra infra = new EvaluationInfra();

    @BeforeEach
    void setUp() {
        infra.retriever = new FieldValueRetriever();
        infra.evaluator = new FieldConditionEvaluator();
        infra.setCombiner = new SetCombiner();
    }

    @Test
    void equal() {
        TestEntity matching = new TestEntity();
        matching.setStringField("hello");

        TestEntity nonMatching = new TestEntity();
        nonMatching.setStringField("bye");

        EvaluableSimpleCondition sut = new EvaluableSimpleCondition("stringField", EQUAL, "hello");

        Set<TestEntity> result = sut.evaluate(List.of(matching, nonMatching), infra);

        assertThat(result).containsExactly(matching);
    }

    @Test
    void notEqual() {
        TestEntity matching = new TestEntity();
        matching.setStringField("hello");

        TestEntity nonMatching = new TestEntity();
        nonMatching.setStringField("bye");

        EvaluableSimpleCondition sut = new EvaluableSimpleCondition("stringField", NOT_EQUAL, "bye");

        Set<TestEntity> result = sut.evaluate(List.of(matching, nonMatching), infra);

        assertThat(result).containsExactly(matching);
    }

    @Test
    void greaterThan() {
        TestEntity matching = new TestEntity();
        matching.setIntegerField(7);

        TestEntity nonMatching = new TestEntity();
        nonMatching.setIntegerField(3);

        EvaluableSimpleCondition sut = new EvaluableSimpleCondition("integerField", GREATER_THAN, 5);

        Set<TestEntity> result = sut.evaluate(List.of(matching, nonMatching), infra);

        assertThat(result).containsExactly(matching);
    }

    @Test
    void lessThan() {
        TestEntity matching = new TestEntity();
        matching.setIntegerField(3);

        TestEntity nonMatching = new TestEntity();
        nonMatching.setIntegerField(7);

        EvaluableSimpleCondition sut = new EvaluableSimpleCondition("integerField", LESS_THAN, 5);

        Set<TestEntity> result = sut.evaluate(List.of(matching, nonMatching), infra);

        assertThat(result).containsExactly(matching);
    }
}