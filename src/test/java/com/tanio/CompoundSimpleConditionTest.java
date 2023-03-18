package com.tanio;

import com.tanio.SimpleCondition.Operator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CompoundSimpleConditionTest {

    @Test
    void performCompoundCondition_or() {
        TestEntity firstConditionMatchingTestEntity = new TestEntity();
        firstConditionMatchingTestEntity.setStringField("hello");

        TestEntity secondConditionMatchingTestEntity = new TestEntity();
        secondConditionMatchingTestEntity.setStringField("bye");

        TestEntity notMatchingTestEntity = new TestEntity();
        notMatchingTestEntity.setStringField("sup");

        CompoundCondition condition = new CompoundCondition(
                CompoundCondition.BooleanOperator.OR,
                Arrays.asList(
                        new SimpleCondition("stringField", Operator.EQUAL, "hello"),
                        new SimpleCondition("stringField", Operator.EQUAL, "bye")));

        List<TestEntity> result = condition.evaluate(
                Arrays.asList(
                        firstConditionMatchingTestEntity,
                        secondConditionMatchingTestEntity,
                        notMatchingTestEntity));

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

        CompoundCondition condition = new CompoundCondition(
                CompoundCondition.BooleanOperator.AND,
                Arrays.asList(
                        new SimpleCondition("stringField", Operator.EQUAL, "hello"),
                        new SimpleCondition("integerField", Operator.EQUAL, 13)));

        List<TestEntity> result = condition.evaluate(
                Arrays.asList(
                        conditionMatchingTestEntity,
                        notMatchingTestEntity0,
                        notMatchingTestEntity1));

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

        CompoundCondition condition = new CompoundCondition(
                CompoundCondition.BooleanOperator.NOT,
                Arrays.asList(
                        new SimpleCondition("stringField", Operator.EQUAL, "hello"),
                        new SimpleCondition("integerField", Operator.EQUAL, 13)));

        List<TestEntity> result = condition.evaluate(
                Arrays.asList(
                        conditionMatchingTestEntity,
                        notMatchingTestEntity0,
                        notMatchingTestEntity1,
                        notMatchingTestEntity2));

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

        CompoundCondition first = new CompoundCondition(
                CompoundCondition.BooleanOperator.OR,
                Arrays.asList(
                        new SimpleCondition("stringField", Operator.EQUAL, "hello"),
                        new SimpleCondition("stringField", Operator.EQUAL, "bye")));

        CompoundCondition second = new CompoundCondition(
                CompoundCondition.BooleanOperator.OR,
                Arrays.asList(
                        new SimpleCondition("stringField", Operator.EQUAL, "good morning"),
                        new SimpleCondition("stringField", Operator.EQUAL, "good night")));

        CompoundCondition condition = new CompoundCondition(
                CompoundCondition.BooleanOperator.OR,
                Arrays.asList(first, second));

        List<TestEntity> result = condition.evaluate(
                Arrays.asList(testEntity0, testEntity1, testEntity2, testEntity3, testEntity4));

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

        CompoundCondition first = new CompoundCondition(
                CompoundCondition.BooleanOperator.AND,
                Arrays.asList(
                        new SimpleCondition("stringField", Operator.EQUAL, "hello"),
                        new SimpleCondition("integerField", Operator.EQUAL, 13)));

        CompoundCondition second = new CompoundCondition(
                CompoundCondition.BooleanOperator.AND,
                Arrays.asList(
                        new SimpleCondition("booleanField", Operator.EQUAL, true),
                        new SimpleCondition("charField", Operator.EQUAL, 'a')));

        CompoundCondition condition = new CompoundCondition(
                CompoundCondition.BooleanOperator.AND,
                Arrays.asList(first, second));

        List<TestEntity> result =
                condition.evaluate(
                        Arrays.asList(
                                testEntity0,
                                testEntity1,
                                testEntity2,
                                testEntity3,
                                testEntity4));

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

        CompoundCondition first = new CompoundCondition(
                CompoundCondition.BooleanOperator.OR,
                Arrays.asList(
                        new SimpleCondition("stringField", Operator.EQUAL, "hello"),
                        new SimpleCondition("stringField", Operator.EQUAL, "bye")));

        CompoundCondition second = new CompoundCondition(
                CompoundCondition.BooleanOperator.OR,
                Arrays.asList(
                        new SimpleCondition("stringField", Operator.EQUAL, "good morning"),
                        new SimpleCondition("stringField", Operator.EQUAL, "good night")));

        CompoundCondition condition = new CompoundCondition(
                CompoundCondition.BooleanOperator.NOT,
                Arrays.asList(first, second));

        List<TestEntity> result = condition.evaluate(
                Arrays.asList(
                        testEntity0,
                        testEntity1,
                        testEntity2,
                        testEntity3,
                        testEntity4));

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

        CompoundCondition first = new CompoundCondition(
                CompoundCondition.BooleanOperator.OR,
                Arrays.asList(
                        new SimpleCondition("country", Operator.EQUAL, "UK"),
                        new SimpleCondition("country", Operator.EQUAL, "USA")));

        CompoundCondition second = new CompoundCondition(
                CompoundCondition.BooleanOperator.OR,
                Arrays.asList(
                        new SimpleCondition("numberOfComponents", Operator.EQUAL, 3),
                        new SimpleCondition("numberOfComponents", Operator.EQUAL, 4)));

        CompoundCondition condition = new CompoundCondition(
                CompoundCondition.BooleanOperator.AND,
                Arrays.asList(first, second));

        List<MusicArtist> result = condition.evaluate(musicArtists);
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

        CompoundCondition first = new CompoundCondition(
                CompoundCondition.BooleanOperator.AND,
                Arrays.asList(
                        new SimpleCondition("country", Operator.EQUAL, "USA"),
                        new SimpleCondition("numberOfComponents", Operator.NOT_EQUAL, 3),
                        new SimpleCondition("numberOfComponents", Operator.NOT_EQUAL, 4)));

        CompoundCondition second = new CompoundCondition(
                CompoundCondition.BooleanOperator.AND,
                Arrays.asList(
                        new SimpleCondition("country", Operator.EQUAL, "UK"),
                        new SimpleCondition("genre", Operator.EQUAL, "Pop")));

        CompoundCondition condition = new CompoundCondition(
                CompoundCondition.BooleanOperator.OR,
                Arrays.asList(first, second));

        List<MusicArtist> result = condition.evaluate(musicArtists);
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

        CompoundCondition first = new CompoundCondition(
                CompoundCondition.BooleanOperator.NOT,
                Arrays.asList(
                        new SimpleCondition("country", Operator.EQUAL, "USA"),
                        new SimpleCondition("numberOfComponents", Operator.EQUAL, 1)));

        CompoundCondition second = new CompoundCondition(
                CompoundCondition.BooleanOperator.NOT,
                Arrays.asList(
                        new SimpleCondition("country", Operator.EQUAL, "UK"),
                        new SimpleCondition("numberOfComponents", Operator.EQUAL, 1)));

        CompoundCondition condition = new CompoundCondition(
                CompoundCondition.BooleanOperator.OR,
                Arrays.asList(first, second));

        List<MusicArtist> result = condition.evaluate(musicArtists);
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

        CompoundCondition nested = new CompoundCondition(
                CompoundCondition.BooleanOperator.AND,
                Arrays.asList(
                        new SimpleCondition("integerField", Operator.EQUAL, 13),
                        new SimpleCondition("charField", Operator.EQUAL, 'x')));

        CompoundCondition condition = new CompoundCondition(
                CompoundCondition.BooleanOperator.OR,
                Arrays.asList(
                        new SimpleCondition("stringField", Operator.EQUAL, "anything"),
                        nested));

        List<TestEntity> result =
                condition.evaluate(
                        Arrays.asList(
                                matchingNestedConditionEntity,
                                matchingNonNestedConditionEntity,
                                nonMatchingNonNestedConditionEntity,
                                nonMatchingNestedConditionEntity0,
                                nonMatchingNestedConditionEntity1,
                                nonMatchingNestedConditionEntity2));

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

        CompoundCondition nested = new CompoundCondition(
                CompoundCondition.BooleanOperator.OR,
                Arrays.asList(
                        new SimpleCondition("stringField", Operator.EQUAL, "anything"),
                        new SimpleCondition("integerField", Operator.EQUAL, 13)));

        CompoundCondition condition = new CompoundCondition(
                CompoundCondition.BooleanOperator.AND,
                Arrays.asList(
                        new SimpleCondition("charField", Operator.EQUAL, 'x'),
                        nested));

        List<TestEntity> result = condition.evaluate(
                Arrays.asList(
                        matchingNonNestedConditionEntity,
                        matchingNestedConditionEntity0,
                        matchingNestedConditionEntity1,
                        nonMatchingEntity0,
                        nonMatchingEntity1,
                        nonMatchingEntity2));

        assertThat(result).containsExactlyInAnyOrder(matchingNestedConditionEntity0);
    }

    MusicArtist beatles() {
        MusicArtist beatles = new MusicArtist();
        beatles.name = "Beatles";
        beatles.genre = "Rock";
        beatles.numberOfComponents = 4;
        beatles.country = "UK";
        return beatles;
    }

    MusicArtist rollingStones() {
        MusicArtist rollingStones = new MusicArtist();
        rollingStones.name = "Rolling Stones";
        rollingStones.genre = "Rock";
        rollingStones.numberOfComponents = 4;
        rollingStones.country = "UK";
        return rollingStones;
    }

    MusicArtist madonna() {
        MusicArtist madonna = new MusicArtist();
        madonna.name = "Madonna";
        madonna.genre = "Pop";
        madonna.numberOfComponents = 1;
        madonna.country = "USA";
        return madonna;
    }

    MusicArtist marvinGaye() {
        MusicArtist marvinGaye = new MusicArtist();
        marvinGaye.name = "Marvin Gaye";
        marvinGaye.genre = "R&B";
        marvinGaye.numberOfComponents = 1;
        marvinGaye.country = "USA";
        return marvinGaye;
    }

    MusicArtist bjork() {
        MusicArtist bjork = new MusicArtist();
        bjork.name = "Bjork";
        bjork.genre = "Art Pop";
        bjork.numberOfComponents = 1;
        bjork.country = "Iceland";
        return bjork;
    }

    MusicArtist edithPiaf() {
        MusicArtist edithPiaf = new MusicArtist();
        edithPiaf.name = "Edith Piaf";
        edithPiaf.genre = "Cabaret";
        edithPiaf.numberOfComponents = 1;
        edithPiaf.country = "France";
        return edithPiaf;
    }

    MusicArtist nirvana() {
        MusicArtist nirvana = new MusicArtist();
        nirvana.name = "Nirvana";
        nirvana.genre = "Rock";
        nirvana.numberOfComponents = 3;
        nirvana.country = "USA";
        return nirvana;
    }

    MusicArtist bruceSpringsteen() {
        MusicArtist bruceSpringsteen = new MusicArtist();
        bruceSpringsteen.name = "Bruce Springsteen";
        bruceSpringsteen.genre = "Rock";
        bruceSpringsteen.numberOfComponents = 1;
        bruceSpringsteen.country = "USA";
        return bruceSpringsteen;
    }

    MusicArtist eltonJohn() {
        MusicArtist eltonJohn = new MusicArtist();
        eltonJohn.name = "Elton John";
        eltonJohn.genre = "Pop";
        eltonJohn.numberOfComponents = 1;
        eltonJohn.country = "UK";
        return eltonJohn;
    }
}