package com.tanio;

import com.tanio.Condition.Operator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class FilterTest {
    Filter sut = new Filter();

    @Nested
    class MultipleConditions {
        @Test
        void performCompoundCondition_or() {
            TestEntity firstConditionMatchingTestEntity = new TestEntity();
            firstConditionMatchingTestEntity.setStringField("hello");

            TestEntity secondConditionMatchingTestEntity = new TestEntity();
            secondConditionMatchingTestEntity.setStringField("bye");

            TestEntity notMatchingTestEntity = new TestEntity();
            notMatchingTestEntity.setStringField("sup");

            CompoundCondition compoundCondition = new CompoundCondition();
            compoundCondition.booleanOperator = BooleanOperator.OR;
            compoundCondition.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("stringField", Operator.EQUAL, "bye"));

            List<TestEntity> result = sut.perform(
                    Arrays.asList(
                            firstConditionMatchingTestEntity,
                            secondConditionMatchingTestEntity,
                            notMatchingTestEntity),
                    compoundCondition);

            assertThat(result).containsExactlyInAnyOrder(firstConditionMatchingTestEntity, secondConditionMatchingTestEntity);
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

            CompoundCondition compoundCondition = new CompoundCondition();
            compoundCondition.booleanOperator = BooleanOperator.AND;
            compoundCondition.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("integerField", Operator.EQUAL, 13));

            List<TestEntity> result = sut.perform(
                    Arrays.asList(
                            conditionMatchingTestEntity,
                            notMatchingTestEntity0,
                            notMatchingTestEntity1),
                    compoundCondition);

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

            CompoundCondition compoundCondition = new CompoundCondition();
            compoundCondition.booleanOperator = BooleanOperator.NOT;
            compoundCondition.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("integerField", Operator.EQUAL, 13));

            List<TestEntity> result = sut.perform(
                    Arrays.asList(
                            conditionMatchingTestEntity,
                            notMatchingTestEntity0,
                            notMatchingTestEntity1,
                            notMatchingTestEntity2),
                    compoundCondition);

            assertThat(result).containsExactlyInAnyOrder(conditionMatchingTestEntity);
        }
    }

    @Nested
    class NestedConditions {
        @Test
        void performCompoundConditionNested_or() {
            CompoundCondition nestedCondition0 = new CompoundCondition();
            nestedCondition0.booleanOperator = BooleanOperator.OR;
            nestedCondition0.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("stringField", Operator.EQUAL, "bye"));

            CompoundCondition nestedCondition1 = new CompoundCondition();
            nestedCondition1.booleanOperator = BooleanOperator.OR;
            nestedCondition1.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "good morning"),
                    condition("stringField", Operator.EQUAL, "good night"));

            CompoundCondition outerCondition = new CompoundCondition();
            outerCondition.booleanOperator = BooleanOperator.OR;
            outerCondition.nestedConditions = new ArrayList<>();
            outerCondition.nestedConditions.add(nestedCondition0);
            outerCondition.nestedConditions.add(nestedCondition1);

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

            List<TestEntity> result = sut.perform(Arrays.asList(testEntity0, testEntity1, testEntity2, testEntity3, testEntity4), outerCondition);

            assertThat(result).containsExactlyInAnyOrder(testEntity0, testEntity1, testEntity2, testEntity3);
        }

        @Test
        void performCompoundConditionNested_and() {
            CompoundCondition nestedCondition0 = new CompoundCondition();
            nestedCondition0.booleanOperator = BooleanOperator.AND;
            nestedCondition0.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("integerField", Operator.EQUAL, 13));

            CompoundCondition nestedCondition1 = new CompoundCondition();
            nestedCondition1.booleanOperator = BooleanOperator.AND;
            nestedCondition1.conditions = Arrays.asList(
                    condition("booleanField", Operator.EQUAL, true),
                    condition("charField", Operator.EQUAL, 'a'));

            CompoundCondition outerCondition = new CompoundCondition();
            outerCondition.booleanOperator = BooleanOperator.AND;
            outerCondition.nestedConditions = new ArrayList<>();
            outerCondition.nestedConditions.add(nestedCondition0);
            outerCondition.nestedConditions.add(nestedCondition1);

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

            List<TestEntity> result = sut.perform(Arrays.asList(testEntity0, testEntity1, testEntity2, testEntity3, testEntity4), outerCondition);

            assertThat(result).containsExactly(testEntity0);
        }

        @Test
        void performCompoundConditionNested_not() {
            CompoundCondition nestedCondition0 = new CompoundCondition();
            nestedCondition0.booleanOperator = BooleanOperator.OR;
            nestedCondition0.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("stringField", Operator.EQUAL, "bye"));

            CompoundCondition nestedCondition1 = new CompoundCondition();
            nestedCondition1.booleanOperator = BooleanOperator.OR;
            nestedCondition1.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "good morning"),
                    condition("stringField", Operator.EQUAL, "good night"));

            CompoundCondition outerCondition = new CompoundCondition();
            outerCondition.booleanOperator = BooleanOperator.NOT;
            outerCondition.nestedConditions = new ArrayList<>();
            outerCondition.nestedConditions.add(nestedCondition0);
            outerCondition.nestedConditions.add(nestedCondition1);

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

            List<TestEntity> result = sut.perform(Arrays.asList(testEntity0, testEntity1, testEntity2, testEntity3, testEntity4), outerCondition);

            assertThat(result).containsExactlyInAnyOrder(testEntity4);
        }

        @Nested
        class CompoundConditions {
            @Test
            void performCompoundConditionNested_mixedAndOr() {
                CompoundCondition isAnglophone = new CompoundCondition();
                isAnglophone.booleanOperator = BooleanOperator.OR;
                isAnglophone.conditions = Arrays.asList(
                        condition("country", Operator.EQUAL, "UK"),
                        condition("country", Operator.EQUAL, "USA"));

                CompoundCondition isBand = new CompoundCondition();
                isBand.booleanOperator = BooleanOperator.OR;
                isBand.conditions = Arrays.asList(
                        condition("numberOfComponents", Operator.EQUAL, 3),
                        condition("numberOfComponents", Operator.EQUAL, 4));

                CompoundCondition isAnglophoneBand = new CompoundCondition();
                isAnglophoneBand.booleanOperator = BooleanOperator.AND;
                isAnglophoneBand.nestedConditions = Arrays.asList(isAnglophone, isBand);

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
                List<MusicArtist> result = sut.perform(musicArtists, isAnglophoneBand);
                assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), nirvana());
            }

            @Test
            void performCompoundConditionNested_mixedOrAnd() {
                CompoundCondition isAmericanSingleSinger = new CompoundCondition();
                isAmericanSingleSinger.booleanOperator = BooleanOperator.AND;
                isAmericanSingleSinger.conditions = Arrays.asList(
                        condition("country", Operator.EQUAL, "USA"),
                        condition("numberOfComponents", Operator.NOT_EQUAL, 3),
                        condition("numberOfComponents", Operator.NOT_EQUAL, 4));

                CompoundCondition isEnglishPopArtist = new CompoundCondition();
                isEnglishPopArtist.booleanOperator = BooleanOperator.AND;
                isEnglishPopArtist.conditions = Arrays.asList(
                        condition("country", Operator.EQUAL, "UK"),
                        condition("genre", Operator.EQUAL, "Pop"));

                CompoundCondition isAnglophoneBand = new CompoundCondition();
                isAnglophoneBand.booleanOperator = BooleanOperator.OR;
                isAnglophoneBand.nestedConditions = Arrays.asList(isAmericanSingleSinger, isEnglishPopArtist);

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
                List<MusicArtist> result = sut.perform(musicArtists, isAnglophoneBand);
                assertThat(result).containsExactlyInAnyOrder(marvinGaye(), bruceSpringsteen(), eltonJohn(), madonna());
            }

            @Test
            void performCompoundConditionNested_mixedOrAndNot() {
                CompoundCondition isNotAmericanBand = new CompoundCondition();
                isNotAmericanBand.booleanOperator = BooleanOperator.NOT;
                isNotAmericanBand.conditions = Arrays.asList(
                        condition("country", Operator.EQUAL, "USA"),
                        condition("numberOfComponents", Operator.EQUAL, 1));

                CompoundCondition isNotEnglishBand = new CompoundCondition();
                isNotEnglishBand.booleanOperator = BooleanOperator.NOT;
                isNotEnglishBand.conditions = Arrays.asList(
                        condition("country", Operator.EQUAL, "UK"),
                        condition("numberOfComponents", Operator.EQUAL, 1));

                CompoundCondition isNotAnglophoneBand = new CompoundCondition();
                isNotAnglophoneBand.booleanOperator = BooleanOperator.OR;
                isNotAnglophoneBand.nestedConditions = Arrays.asList(isNotAmericanBand, isNotEnglishBand);

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
                List<MusicArtist> result = sut.perform(musicArtists, isNotAnglophoneBand);
                assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), nirvana());
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
    }

    @Nested
    class NestedAndNonNestedConditions {
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

            Condition nonNestedCondition = new Condition();
            nonNestedCondition.fieldName = "stringField";
            nonNestedCondition.operator = Operator.EQUAL;
            nonNestedCondition.value = "anything";

            Condition nestedCondition0 = new Condition();
            nestedCondition0.fieldName = "integerField";
            nestedCondition0.operator = Operator.EQUAL;
            nestedCondition0.value = 13;

            Condition nestedCondition1 = new Condition();
            nestedCondition1.fieldName = "charField";
            nestedCondition1.operator = Operator.EQUAL;
            nestedCondition1.value = 'x';

            CompoundCondition nestedConditions = new CompoundCondition();
            nestedConditions.booleanOperator = BooleanOperator.AND;
            nestedConditions.conditions = Arrays.asList(nestedCondition0, nestedCondition1);

            CompoundCondition compoundCondition = new CompoundCondition();
            compoundCondition.booleanOperator = BooleanOperator.OR;
            compoundCondition.conditions = singletonList(nonNestedCondition);
            compoundCondition.nestedConditions = singletonList(nestedConditions);

            List<TestEntity> result =
                    sut.perform(
                            Arrays.asList(
                                    matchingNestedConditionEntity,
                                    matchingNonNestedConditionEntity,
                                    nonMatchingNonNestedConditionEntity,
                                    nonMatchingNestedConditionEntity0,
                                    nonMatchingNestedConditionEntity1,
                                    nonMatchingNestedConditionEntity2
                            ), compoundCondition);

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

            Condition nestedCondition0 = new Condition();
            nestedCondition0.fieldName = "stringField";
            nestedCondition0.operator = Operator.EQUAL;
            nestedCondition0.value = "anything";

            Condition nestedCondition1 = new Condition();
            nestedCondition1.fieldName = "integerField";
            nestedCondition1.operator = Operator.EQUAL;
            nestedCondition1.value = 13;

            Condition nonNestedCondition = new Condition();
            nonNestedCondition.fieldName = "charField";
            nonNestedCondition.operator = Operator.EQUAL;
            nonNestedCondition.value = 'x';

            CompoundCondition nestedConditions = new CompoundCondition();
            nestedConditions.booleanOperator = BooleanOperator.OR;
            nestedConditions.conditions = Arrays.asList(nestedCondition0, nestedCondition1);

            CompoundCondition condition = new CompoundCondition();
            condition.booleanOperator = BooleanOperator.AND;
            condition.conditions = singletonList(nonNestedCondition);
            condition.nestedConditions = singletonList(nestedConditions);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(
                            matchingNonNestedConditionEntity,
                            matchingNestedConditionEntity0,
                            matchingNestedConditionEntity1,
                            nonMatchingEntity0,
                            nonMatchingEntity1,
                            nonMatchingEntity2
                    ), condition);

            assertThat(result).containsExactlyInAnyOrder(matchingNestedConditionEntity0);
        }
    }

    Condition condition(String fieldName, Operator operator, Object value) {
        Condition result = new Condition();
        result.fieldName = fieldName;
        result.operator = operator;
        result.value = value;
        return result;
    }
}