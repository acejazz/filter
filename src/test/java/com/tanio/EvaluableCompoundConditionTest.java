package com.tanio;

import com.tanio.EvaluableSimpleCondition.Operator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.tanio.TestPlatform.*;
import static org.assertj.core.api.Assertions.assertThat;

class EvaluableCompoundConditionTest {
    FieldValueRetriever retriever = new FieldValueRetriever();
    FieldConditionEvaluator evaluator = new FieldConditionEvaluator();

    @Test
    void performCompoundCondition_or() {
        TestEntity firstConditionMatchingTestEntity = new TestEntity();
        firstConditionMatchingTestEntity.setStringField("hello");

        TestEntity secondConditionMatchingTestEntity = new TestEntity();
        secondConditionMatchingTestEntity.setStringField("bye");

        TestEntity notMatchingTestEntity = new TestEntity();
        notMatchingTestEntity.setStringField("sup");

        EvaluableCompoundCondition condition = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.OR,
                Set.of(
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "hello"),
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "bye")));

        List<TestEntity> target = Arrays.asList(
                firstConditionMatchingTestEntity,
                secondConditionMatchingTestEntity,
                notMatchingTestEntity);
        Set<TestEntity> result = condition.evaluate(target, evaluator, retriever);

        assertThat(result)
                .containsExactlyInAnyOrder(
                        firstConditionMatchingTestEntity,
                        secondConditionMatchingTestEntity);
    }

    @Test
    void performCompoundCondition_and() {
        TestEntity conditionMatchingTestEntity = new TestEntity();
        conditionMatchingTestEntity.setStringField("hello");
        conditionMatchingTestEntity.setIntegerField(13);

        TestEntity notMatchingTestEntity0 = new TestEntity();
        notMatchingTestEntity0.setStringField("hello");

        TestEntity notMatchingTestEntity1 = new TestEntity();
        notMatchingTestEntity1.setIntegerField(13);

        EvaluableCompoundCondition condition = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.AND,
                Set.of(
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "hello"),
                        new EvaluableSimpleCondition("integerField", Operator.EQUAL, 13)));

        List<TestEntity> target = Arrays.asList(
                conditionMatchingTestEntity,
                notMatchingTestEntity0,
                notMatchingTestEntity1);
        Set<TestEntity> result = condition.evaluate(target, evaluator, retriever);

        assertThat(result).containsExactlyInAnyOrder(conditionMatchingTestEntity);
    }

    @Test
    void performCompoundCondition_not() {
        TestEntity conditionMatchingTestEntity = new TestEntity();

        TestEntity notMatchingTestEntity0 = new TestEntity();
        notMatchingTestEntity0.setStringField("hello");

        TestEntity notMatchingTestEntity1 = new TestEntity();
        notMatchingTestEntity1.setIntegerField(13);

        TestEntity notMatchingTestEntity2 = new TestEntity();
        notMatchingTestEntity2.setStringField("hello");
        notMatchingTestEntity2.setIntegerField(13);

        EvaluableCompoundCondition condition = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.NOT,
                Set.of(
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "hello"),
                        new EvaluableSimpleCondition("integerField", Operator.EQUAL, 13)));

        List<TestEntity> target = Arrays.asList(
                conditionMatchingTestEntity,
                notMatchingTestEntity0,
                notMatchingTestEntity1,
                notMatchingTestEntity2);
        Set<TestEntity> result = condition.evaluate(target, evaluator, retriever);

        assertThat(result).containsExactlyInAnyOrder(conditionMatchingTestEntity);
    }

    @Test
    void performCompoundConditionNested_or() {
        TestEntity testEntity0 = new TestEntity();
        testEntity0.setStringField("hello");

        TestEntity testEntity1 = new TestEntity();
        testEntity1.setStringField("bye");

        TestEntity testEntity2 = new TestEntity();
        testEntity2.setStringField("good morning");

        TestEntity testEntity3 = new TestEntity();
        testEntity3.setStringField("good night");

        TestEntity testEntity4 = new TestEntity();
        testEntity4.setStringField("batman");

        EvaluableCompoundCondition first = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.OR,
                Set.of(
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "hello"),
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "bye")));

        EvaluableCompoundCondition second = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.OR,
                Set.of(
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "good morning"),
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "good night")));

        EvaluableCompoundCondition condition = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.OR,
                Set.of(first, second));

        List<TestEntity> target = Arrays.asList(testEntity0, testEntity1, testEntity2, testEntity3, testEntity4);
        Set<TestEntity> result = condition.evaluate(target, evaluator, retriever);

        assertThat(result)
                .containsExactlyInAnyOrder(testEntity0, testEntity1, testEntity2, testEntity3);
    }

    @Test
    void performCompoundConditionNested_and() {
        TestEntity testEntity0 = new TestEntity();
        testEntity0.setStringField("hello");
        testEntity0.setIntegerField(13);
        testEntity0.setBooleanField(true);
        testEntity0.setCharField('a');

        TestEntity testEntity1 = new TestEntity();
        testEntity1.setStringField("bye");
        testEntity1.setIntegerField(13);
        testEntity1.setBooleanField(true);
        testEntity1.setCharField('a');

        TestEntity testEntity2 = new TestEntity();
        testEntity2.setStringField("hello");
        testEntity2.setIntegerField(17);
        testEntity2.setBooleanField(true);
        testEntity2.setCharField('a');

        TestEntity testEntity3 = new TestEntity();
        testEntity3.setStringField("hello");
        testEntity3.setIntegerField(13);
        testEntity3.setBooleanField(false);
        testEntity3.setCharField('a');

        TestEntity testEntity4 = new TestEntity();
        testEntity4.setStringField("hello");
        testEntity4.setIntegerField(13);
        testEntity4.setBooleanField(true);
        testEntity4.setCharField('z');

        EvaluableCompoundCondition first = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.AND,
                Set.of(
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "hello"),
                        new EvaluableSimpleCondition("integerField", Operator.EQUAL, 13)));

        EvaluableCompoundCondition second = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.AND,
                Set.of(
                        new EvaluableSimpleCondition("booleanField", Operator.EQUAL, true),
                        new EvaluableSimpleCondition("charField", Operator.EQUAL, 'a')));

        EvaluableCompoundCondition condition = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.AND,
                Set.of(first, second));

        List<TestEntity> target = Arrays.asList(
                testEntity0,
                testEntity1,
                testEntity2,
                testEntity3,
                testEntity4);
        Set<TestEntity> result = condition.evaluate(target, evaluator, retriever);

        assertThat(result).containsExactly(testEntity0);
    }

    @Test
    void performCompoundConditionNested_not() {
        TestEntity testEntity0 = new TestEntity();
        testEntity0.setStringField("hello");

        TestEntity testEntity1 = new TestEntity();
        testEntity1.setStringField("bye");

        TestEntity testEntity2 = new TestEntity();
        testEntity2.setStringField("good morning");

        TestEntity testEntity3 = new TestEntity();
        testEntity3.setStringField("good night");

        TestEntity testEntity4 = new TestEntity();
        testEntity4.setStringField("batman");

        EvaluableCompoundCondition first = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.OR,
                Set.of(
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "hello"),
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "bye")));

        EvaluableCompoundCondition second = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.OR,
                Set.of(
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "good morning"),
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "good night")));

        EvaluableCompoundCondition condition = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.NOT,
                Set.of(first, second));

        List<TestEntity> target = Arrays.asList(
                testEntity0,
                testEntity1,
                testEntity2,
                testEntity3,
                testEntity4);
        Set<TestEntity> result = condition.evaluate(target, evaluator, retriever);

        assertThat(result).containsExactlyInAnyOrder(testEntity4);
    }

    @Test
    void performCompoundConditionNested_mixedAndOr() {
        List<MusicArtist> musicArtists = Arrays.asList(
                beatles(),
                rollingStones(),
                madonna(),
                marvinGaye(),
                bjork(),
                edithPiaf(),
                nirvana(),
                eltonJohn(),
                bruceSpringsteen());

        EvaluableCompoundCondition first = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.OR,
                Set.of(
                        new EvaluableSimpleCondition("country", Operator.EQUAL, "UK"),
                        new EvaluableSimpleCondition("country", Operator.EQUAL, "USA")));

        EvaluableCompoundCondition second = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.OR,
                Set.of(
                        new EvaluableSimpleCondition("numberOfComponents", Operator.EQUAL, 3),
                        new EvaluableSimpleCondition("numberOfComponents", Operator.EQUAL, 4)));

        EvaluableCompoundCondition condition = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.AND,
                Set.of(first, second));

        Set<MusicArtist> result = condition.evaluate(musicArtists, evaluator, retriever);
        assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), nirvana());
    }

    @Test
    void performCompoundConditionNested_mixedOrAnd() {
        List<MusicArtist> musicArtists = Arrays.asList(
                beatles(),
                rollingStones(),
                madonna(),
                marvinGaye(),
                bjork(),
                edithPiaf(),
                nirvana(),
                eltonJohn(),
                bruceSpringsteen());

        EvaluableCompoundCondition first = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.AND,
                Set.of(
                        new EvaluableSimpleCondition("country", Operator.EQUAL, "USA"),
                        new EvaluableSimpleCondition("numberOfComponents", Operator.NOT_EQUAL, 3),
                        new EvaluableSimpleCondition("numberOfComponents", Operator.NOT_EQUAL, 4)));

        EvaluableCompoundCondition second = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.AND,
                Set.of(
                        new EvaluableSimpleCondition("country", Operator.EQUAL, "UK"),
                        new EvaluableSimpleCondition("genre", Operator.EQUAL, "Pop")));

        EvaluableCompoundCondition condition = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.OR,
                Set.of(first, second));

        Set<MusicArtist> result = condition.evaluate(musicArtists, evaluator, retriever);
        assertThat(result)
                .containsExactlyInAnyOrder(
                        marvinGaye(),
                        bruceSpringsteen(),
                        eltonJohn(),
                        madonna());
    }

    @Test
    void performCompoundConditionNested_mixedOrAndNot() {
        List<MusicArtist> musicArtists = Arrays.asList(
                beatles(),
                rollingStones(),
                madonna(),
                marvinGaye(),
                bjork(),
                edithPiaf(),
                nirvana(),
                eltonJohn(),
                bruceSpringsteen());

        EvaluableCompoundCondition first = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.NOT,
                Set.of(
                        new EvaluableSimpleCondition("country", Operator.EQUAL, "USA"),
                        new EvaluableSimpleCondition("numberOfComponents", Operator.EQUAL, 1)));

        EvaluableCompoundCondition second = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.NOT,
                Set.of(
                        new EvaluableSimpleCondition("country", Operator.EQUAL, "UK"),
                        new EvaluableSimpleCondition("numberOfComponents", Operator.EQUAL, 1)));

        EvaluableCompoundCondition condition = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.OR,
                Set.of(first, second));

        Set<MusicArtist> result = condition.evaluate(musicArtists, evaluator, retriever);
        assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), nirvana());
    }

    @Test
    void performAndOr() {
        TestEntity matchingNonNestedConditionEntity = new TestEntity();
        matchingNonNestedConditionEntity.setStringField("anything");

        TestEntity matchingNestedConditionEntity = new TestEntity();
        matchingNestedConditionEntity.setIntegerField(13);
        matchingNestedConditionEntity.setCharField('x');

        TestEntity nonMatchingNonNestedConditionEntity = new TestEntity();
        nonMatchingNonNestedConditionEntity.setStringField("something");

        TestEntity nonMatchingNestedConditionEntity0 = new TestEntity();
        nonMatchingNestedConditionEntity0.setIntegerField(13);

        TestEntity nonMatchingNestedConditionEntity1 = new TestEntity();
        nonMatchingNestedConditionEntity1.setCharField('x');

        TestEntity nonMatchingNestedConditionEntity2 = new TestEntity();
        nonMatchingNestedConditionEntity2.setIntegerField(13);
        nonMatchingNestedConditionEntity2.setCharField('a');

        EvaluableCompoundCondition nested = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.AND,
                Set.of(
                        new EvaluableSimpleCondition("integerField", Operator.EQUAL, 13),
                        new EvaluableSimpleCondition("charField", Operator.EQUAL, 'x')));

        EvaluableCompoundCondition condition = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.OR,
                Set.of(
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "anything"),
                        nested));

        List<TestEntity> target = Arrays.asList(
                matchingNestedConditionEntity,
                matchingNonNestedConditionEntity,
                nonMatchingNonNestedConditionEntity,
                nonMatchingNestedConditionEntity0,
                nonMatchingNestedConditionEntity1,
                nonMatchingNestedConditionEntity2);
        Set<TestEntity> result = condition.evaluate(target, evaluator, retriever);

        assertThat(result)
                .containsExactlyInAnyOrder(
                        matchingNestedConditionEntity,
                        matchingNonNestedConditionEntity);
    }

    @Test
    void performOrAnd() {
        TestEntity matchingNestedConditionEntity0 = new TestEntity();
        matchingNestedConditionEntity0.setStringField("anything");
        matchingNestedConditionEntity0.setCharField('x');

        TestEntity matchingNestedConditionEntity1 = new TestEntity();
        matchingNestedConditionEntity1.setIntegerField(13);

        TestEntity matchingNonNestedConditionEntity = new TestEntity();
        matchingNonNestedConditionEntity.setCharField('x');
        matchingNonNestedConditionEntity.setBooleanField(true);

        TestEntity nonMatchingEntity0 = new TestEntity();
        nonMatchingEntity0.setStringField("something");
        nonMatchingEntity0.setCharField('x');

        TestEntity nonMatchingEntity1 = new TestEntity();
        nonMatchingEntity1.setIntegerField(19);

        TestEntity nonMatchingEntity2 = new TestEntity();
        nonMatchingEntity2.setStringField("anything");
        nonMatchingEntity2.setCharField('a');

        EvaluableCompoundCondition nested = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.OR,
                Set.of(
                        new EvaluableSimpleCondition("stringField", Operator.EQUAL, "anything"),
                        new EvaluableSimpleCondition("integerField", Operator.EQUAL, 13)));

        EvaluableCompoundCondition condition = new EvaluableCompoundCondition(
                EvaluableCompoundCondition.BooleanOperator.AND,
                Set.of(
                        new EvaluableSimpleCondition("charField", Operator.EQUAL, 'x'),
                        nested));

        List<TestEntity> target = Arrays.asList(
                matchingNonNestedConditionEntity,
                matchingNestedConditionEntity0,
                matchingNestedConditionEntity1,
                nonMatchingEntity0,
                nonMatchingEntity1,
                nonMatchingEntity2);
        Set<TestEntity> result = condition.evaluate(target, evaluator, retriever);

        assertThat(result).containsExactlyInAnyOrder(matchingNestedConditionEntity0);
    }
}