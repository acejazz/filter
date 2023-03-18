package com.tanio;

import com.tanio.Condition.Operator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.tanio.Condition.has;
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
                    new Condition("stringField", Operator.EQUAL, "hello"),
                    new Condition("stringField", Operator.EQUAL, "bye"));

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
                    new Condition("stringField", Operator.EQUAL, "hello"),
                    new Condition("integerField", Operator.EQUAL, 13));

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
                    new Condition("stringField", Operator.EQUAL, "hello"),
                    new Condition("integerField", Operator.EQUAL, 13));

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

            CompoundCondition condition =
                    CompoundCondition.or(
                            CompoundCondition.or(
                                    has("stringField", Operator.EQUAL, "hello"),
                                    has("stringField", Operator.EQUAL, "bye")),
                            CompoundCondition.or(
                                    has("stringField", Operator.EQUAL, "good morning"),
                                    has("stringField", Operator.EQUAL, "good night")));

            List<TestEntity> result = sut.perform(
                    Arrays.asList(testEntity0, testEntity1, testEntity2, testEntity3, testEntity4),
                    condition);

            assertThat(result).containsExactlyInAnyOrder(testEntity0, testEntity1, testEntity2, testEntity3);
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

            CompoundCondition condition = CompoundCondition.and(
                    CompoundCondition.and(
                            has("stringField", Operator.EQUAL, "hello"),
                            has("integerField", Operator.EQUAL, 13)
                    ),
                    CompoundCondition.and(
                            has("booleanField", Operator.EQUAL, true),
                            has("charField", Operator.EQUAL, 'a')));

            List<TestEntity> result =
                    sut.perform(
                            Arrays.asList(
                                    testEntity0,
                                    testEntity1,
                                    testEntity2,
                                    testEntity3,
                                    testEntity4),
                            condition);

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

            CompoundCondition condition = CompoundCondition.not(
                    CompoundCondition.or(
                            has("stringField", Operator.EQUAL, "hello"),
                            has("stringField", Operator.EQUAL, "bye")),
                    CompoundCondition.or(
                            has("stringField", Operator.EQUAL, "good morning"),
                            has("stringField", Operator.EQUAL, "good night")));

            List<TestEntity> result = sut.perform(
                    Arrays.asList(
                            testEntity0,
                            testEntity1,
                            testEntity2,
                            testEntity3,
                            testEntity4),
                    condition);

            assertThat(result).containsExactlyInAnyOrder(testEntity4);
        }

        @Nested
        class CompoundConditions {
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
                CompoundCondition condition = CompoundCondition.and(
                        CompoundCondition.or(
                                has("country", Operator.EQUAL, "UK"),
                                has("country", Operator.EQUAL, "USA")
                        ),
                        CompoundCondition.or(
                                has("numberOfComponents", Operator.EQUAL, 3),
                                has("numberOfComponents", Operator.EQUAL, 4)));
                List<MusicArtist> result = sut.perform(musicArtists, condition);
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
                CompoundCondition condition = CompoundCondition.or(
                        CompoundCondition.and(
                                has("country", Operator.EQUAL, "USA"),
                                has("numberOfComponents", Operator.NOT_EQUAL, 3),
                                has("numberOfComponents", Operator.NOT_EQUAL, 4)
                        ),
                        CompoundCondition.and(
                                has("country", Operator.EQUAL, "UK"),
                                has("genre", Operator.EQUAL, "Pop")));
                List<MusicArtist> result = sut.perform(musicArtists, condition);
                assertThat(result).containsExactlyInAnyOrder(marvinGaye(), bruceSpringsteen(), eltonJohn(), madonna());
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

                CompoundCondition condition = CompoundCondition.or(
                        CompoundCondition.not(
                                has("country", Operator.EQUAL, "USA"),
                                has("numberOfComponents", Operator.EQUAL, 1)),
                        CompoundCondition.not(
                                has("country", Operator.EQUAL, "UK"),
                                has("numberOfComponents", Operator.EQUAL, 1)));

                List<MusicArtist> result = sut.perform(musicArtists, condition);
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

            CompoundCondition condition = CompoundCondition.or(
                    singletonList(
                            has("stringField", Operator.EQUAL, "anything")),
                    CompoundCondition.and(
                            has("integerField", Operator.EQUAL, 13),
                            has("charField", Operator.EQUAL, 'x')));

            List<TestEntity> result =
                    sut.perform(
                            Arrays.asList(
                                    matchingNestedConditionEntity,
                                    matchingNonNestedConditionEntity,
                                    nonMatchingNonNestedConditionEntity,
                                    nonMatchingNestedConditionEntity0,
                                    nonMatchingNestedConditionEntity1,
                                    nonMatchingNestedConditionEntity2),
                            condition);

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

            CompoundCondition condition = CompoundCondition.and(
                    singletonList(
                            has("charField", Operator.EQUAL, 'x')),
                    CompoundCondition.or(
                            has("stringField", Operator.EQUAL, "anything"),
                            has("integerField", Operator.EQUAL, 13)));

            List<TestEntity> result = sut.perform(
                    Arrays.asList(
                            matchingNonNestedConditionEntity,
                            matchingNestedConditionEntity0,
                            matchingNestedConditionEntity1,
                            nonMatchingEntity0,
                            nonMatchingEntity1,
                            nonMatchingEntity2),
                    condition);

            assertThat(result).containsExactlyInAnyOrder(matchingNestedConditionEntity0);
        }
    }
}