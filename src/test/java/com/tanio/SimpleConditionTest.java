package com.tanio;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static com.tanio.SimpleCondition.Operator.*;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleConditionTest {
    FieldValueRetriever retriever = new FieldValueRetriever();
    FieldConditionEvaluator evaluator = new FieldConditionEvaluator();

    @Test
    void equal() {
        TestEntity matching = new TestEntity();
        matching.setStringField("hello");

        TestEntity nonMatching = new TestEntity();
        nonMatching.setStringField("bye");

        SimpleCondition sut =
                new SimpleCondition("stringField", EQUAL, "hello", evaluator, retriever);

        Set<TestEntity> result = sut.evaluate(List.of(matching, nonMatching));

        assertThat(result).containsExactly(matching);
    }

    @Test
    void notEqual() {
        TestEntity matching = new TestEntity();
        matching.setStringField("hello");

        TestEntity nonMatching = new TestEntity();
        nonMatching.setStringField("bye");

        SimpleCondition sut =
                new SimpleCondition("stringField", NOT_EQUAL, "bye", evaluator, retriever);

        Set<TestEntity> result = sut.evaluate(List.of(matching, nonMatching));

        assertThat(result).containsExactly(matching);
    }

    @Test
    void greaterThan() {
        TestEntity matching = new TestEntity();
        matching.setIntegerField(7);

        TestEntity nonMatching = new TestEntity();
        nonMatching.setIntegerField(3);

        SimpleCondition sut =
                new SimpleCondition("integerField", GREATER_THAN, 5, evaluator, retriever);

        Set<TestEntity> result = sut.evaluate(List.of(matching, nonMatching));

        assertThat(result).containsExactly(matching);
    }

    @Test
    void lessThan() {
        TestEntity matching = new TestEntity();
        matching.setIntegerField(3);

        TestEntity nonMatching = new TestEntity();
        nonMatching.setIntegerField(7);

        SimpleCondition sut =
                new SimpleCondition("integerField", LESS_THAN, 5, evaluator, retriever);

        Set<TestEntity> result = sut.evaluate(List.of(matching, nonMatching));

        assertThat(result).containsExactly(matching);
    }
}