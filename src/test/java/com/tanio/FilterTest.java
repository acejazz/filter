package com.tanio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.tanio.CompoundCondition.*;
import static com.tanio.FieldValueRetriever.BooleanFieldNameHandling.GETTER;
import static com.tanio.Filter.FieldNameCase.SNAKE_CASE;
import static com.tanio.Fixture.*;
import static com.tanio.SimpleCondition.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterTest {
    Filter sut = new Filter();

    @Test
    void performEvaluableSimpleCondition() {
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

        Set<MusicArtist> result = sut.evaluate(contains("country", "UK"), musicArtists);

        assertThat(result).containsExactlyInAnyOrder(rollingStones(), eltonJohn(), beatles());
    }

    @Test
    void performEvaluableCompoundCondition_or() {
        CompoundCondition condition = or(
                contains("country", "UK"),
                greaterThan("numberOfComponents", 1));

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

        Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), eltonJohn(), nirvana());
    }

    @Test
    void performEvaluableCompoundCondition_and() {
        CompoundCondition condition = and(
                notContains("country", "UK"),
                lessThan("numberOfComponents", 3));

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

        Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

        assertThat(result)
                .containsExactlyInAnyOrder(
                        madonna(),
                        marvinGaye(),
                        bjork(),
                        edithPiaf(),
                        bruceSpringsteen());
    }

    @Test
    void performEvaluableCompoundCondition_not() {
        CompoundCondition condition = not(
                equal("genre", "Rock"),
                equal("country", "USA"));

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

        Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(bjork(), edithPiaf(), eltonJohn());
    }

    @Test
    void performEvaluableCompoundConditionNested_or() {
        CompoundCondition condition =
                or(
                        or(
                                equal("name", "Rolling Stones"),
                                equal("name", "Madonna")),
                        or(
                                equal("country", "Iceland"),
                                equal("country", "France")));

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

        Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(rollingStones(), madonna(), edithPiaf(), bjork());
    }

    @Test
    void performEvaluableCompoundConditionNested_and() {
        CompoundCondition condition = and(
                and(
                        notEqual("country", "UK"),
                        notEqual("genre", "Rock")),
                and(
                        notEqual("name", "Marvin Gaye"),
                        notEqual("name", "Bjork")));

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

        Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(edithPiaf(), madonna());
    }

    @Test
    void performEvaluableCompoundConditionNested_not() {
        CompoundCondition condition = not(
                and(
                        contains("country", "UK"),
                        equal("numberOfComponents", 1)),
                or(
                        equal("genre", "Rock"),
                        greaterThan("numberOfComponents", 1)));

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

        Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(marvinGaye(), bjork(), madonna(), edithPiaf());
    }

    @Test
    void performEvaluableCompoundConditionNested_mixedAndOr() {
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

        CompoundCondition condition = and(
                or(
                        contains("country", "UK"),
                        equal("country", "USA")),
                or(
                        equal("numberOfComponents", 3),
                        equal("numberOfComponents", 4)));

        Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), nirvana());
    }

    @Test
    void performEvaluableCompoundConditionNested_mixedOrAnd() {

        CompoundCondition condition = or(
                and(
                        equal("country", "USA"),
                        notEqual("numberOfComponents", 3),
                        notEqual("numberOfComponents", 4)),
                and(
                        contains("country", "UK"),
                        equal("genre", "Pop")));

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

        Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

        assertThat(result)
                .containsExactlyInAnyOrder(marvinGaye(), bruceSpringsteen(), eltonJohn(), madonna());
    }

    @Test
    void performEvaluableCompoundConditionNested_mixedOrAndNot() {
        CompoundCondition condition = or(
                not(
                        equal("country", "USA"),
                        equal("numberOfComponents", 1)),
                not(
                        contains("country", "UK"),
                        equal("numberOfComponents", 1)));

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

        Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), nirvana());
    }

    @Test
    void performAndOr() {
        CompoundCondition condition = or(
                equal("name", "Edith Piaf"),
                and(
                        contains("country", "UK"),
                        lessThan("numberOfComponents", 4)));

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

        Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(eltonJohn(), edithPiaf());
    }

    @Test
    void performOrAnd() {
        CompoundCondition condition = and(
                notEqual("numberOfComponents", 1),
                or(
                        notEqual("country", "UK"),
                        notEqual("genre", "Rock")));

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

        Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(nirvana(), beatles());
    }

    @Test
    void filterByNull() {
        SimpleCondition condition = notEqual("realName", null);

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

        Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(madonna(), marvinGaye(), bjork(), edithPiaf(), eltonJohn());
    }

    @Test
    void filterNonExistingField() {
        SimpleCondition condition = equal("nonExistingField", "anything");

        assertThatThrownBy(() -> sut.evaluate(condition, singletonList(new Object())))
                .isInstanceOf(FilterException.class)
                .hasMessage("Method [getNonExistingField] does not exist in class [java.lang.Object]");
    }

    @Test
    void filterWithInvalidStringifiedBoolean() {
        SimpleCondition condition = equal("stillPlaying", "anything");

        List<MusicArtist> musicArtists = Arrays.asList(beatles(), rollingStones());

        assertThatThrownBy(() -> sut.evaluate(condition, musicArtists))
                .isInstanceOf(FilterException.class)
                .hasMessage("[anything] is not a valid boolean value");
    }

    @Test
    void filterWithInvalidNumber() {
        SimpleCondition condition = equal("numberOfComponents", "anything");

        List<MusicArtist> musicArtists = Arrays.asList(beatles(), rollingStones());

        assertThatThrownBy(() -> sut.evaluate(condition, musicArtists))
                .isInstanceOf(FilterException.class)
                .hasMessage("[anything] is not a valid numeric value");
    }

    @Nested
    class WithSnakeCaseFieldName {
        Filter sut;

        @BeforeEach
        void setUp() {
            Filter.Settings settings = new Filter.Settings();
            settings.fieldNameCase = SNAKE_CASE;
            sut = new Filter(settings);
        }

        @Test
        void useSnakeCaseForSimpleCondition() {
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

            Set<MusicArtist> result = sut.evaluate(greaterThan("number_of_components", 1), musicArtists);

            assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), nirvana());
        }

        @Test
        void useSnakeCaseForCompoundCondition() {
            CompoundCondition condition = or(
                    contains("country", "UK"),
                    greaterThan("number_of_components", 1));

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

            Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

            assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), eltonJohn(), nirvana());
        }
    }

    @Nested
    class WithGetterBooleanFieldHandling {
        Filter sut = new Filter();

        @BeforeEach
        void setUp() {
            Filter.Settings settings = new Filter.Settings();
            settings.booleanFieldNameHandling = GETTER;
            sut = new Filter(settings);
        }

        @Test
        void useGetterBooleanFieldNameWithSimpleCondition() {
            assertTrue(methodExists(MusicArtist.class, "getStillPlaying"));
            assertFalse(methodExists(MusicArtist.class, "isStillPlaying"));

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

            Set<MusicArtist> result = sut.evaluate(equal("stillPlaying", false), musicArtists);

            assertThat(result).containsExactlyInAnyOrder(edithPiaf(), beatles(), nirvana(), marvinGaye());
        }

        @Test
        void useGetterBooleanFieldNameWithCompoundCondition() {
            assertTrue(methodExists(MusicArtist.class, "getStillPlaying"));
            assertFalse(methodExists(MusicArtist.class, "isStillPlaying"));

            CompoundCondition condition = and(
                    equal("stillPlaying", false),
                    greaterThan("numberOfComponents", 1));

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

            Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

            assertThat(result).containsExactlyInAnyOrder(nirvana(), beatles());
        }

        boolean methodExists(Class<?> clazz, String methodName) {
            try {
                clazz.getMethod(methodName);
            } catch (NoSuchMethodException e) {
                return false;
            }
            return true;
        }
    }

    @Nested
    class WithDifferentNestingSeparator {
        @Test
        void filterWithDifferentNestingSeparator() {
            NestedNestedEntity matchingNestedNestedEntity = new NestedNestedEntity();
            matchingNestedNestedEntity.setStringField("anything");
            NestedEntity matchingNestedEntity = new NestedEntity();
            matchingNestedEntity.setNestedNestedEntity(matchingNestedNestedEntity);
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setNestedEntity(matchingNestedEntity);

            NestedNestedEntity nonMatchingNestedNestedEntity = new NestedNestedEntity();
            nonMatchingNestedNestedEntity.setStringField("notAnything");
            NestedEntity nonMatchingNestedEntity = new NestedEntity();
            nonMatchingNestedEntity.setNestedNestedEntity(nonMatchingNestedNestedEntity);
            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setNestedEntity(nonMatchingNestedEntity);

            Filter.Settings settings = new Filter.Settings();
            settings.nestingSeparator = "/";
            Filter sut = new Filter(settings);

            Set<TestEntity> result =
                    sut.evaluate(
                            equal("nestedEntity/nestedNestedEntity/stringField", "anything"),
                            Arrays.asList(matchingTestEntity, nonMatchingTestEntity));

            assertThat(result).containsExactly(matchingTestEntity);
        }
    }
}