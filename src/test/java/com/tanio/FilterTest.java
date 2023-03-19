package com.tanio;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.tanio.CompoundCondition.BooleanOperator.*;
import static com.tanio.SimpleCondition.Operator.*;
import static com.tanio.TestPlatform.*;
import static org.assertj.core.api.Assertions.assertThat;

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

        Set<MusicArtist> result = sut.evaluate(new SimpleCondition("country", EQUAL, "UK"), musicArtists);

        assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), eltonJohn());
    }

    @Test
    void performEvaluableCompoundCondition_or() {
        CompoundCondition condition = new CompoundCondition(
                OR,
                Set.of(
                        new SimpleCondition("country", EQUAL, "UK"),
                        new SimpleCondition("numberOfComponents", GREATER_THAN, 1)));

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
        CompoundCondition condition = new CompoundCondition(
                AND,
                Set.of(
                        new SimpleCondition("country", NOT_EQUAL, "UK"),
                        new SimpleCondition("numberOfComponents", LESS_THAN, 3)));

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

        assertThat(result).containsExactlyInAnyOrder(madonna(), marvinGaye(), bjork(), edithPiaf(), bruceSpringsteen());
    }

    @Test
    void performEvaluableCompoundCondition_not() {
        CompoundCondition condition = new CompoundCondition(
                NOT,
                Set.of(
                        new SimpleCondition("genre", EQUAL, "Rock"),
                        new SimpleCondition("country", EQUAL, "USA")));

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
        CompoundCondition first = new CompoundCondition(
                OR,
                Set.of(
                        new SimpleCondition("name", EQUAL, "Rolling Stones"),
                        new SimpleCondition("name", EQUAL, "Madonna")));

        CompoundCondition second = new CompoundCondition(
                OR,
                Set.of(
                        new SimpleCondition("country", EQUAL, "Iceland"),
                        new SimpleCondition("country", EQUAL, "France")));

        CompoundCondition condition = new CompoundCondition(
                OR,
                Set.of(first, second));

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
        CompoundCondition first = new CompoundCondition(
                AND,
                Set.of(
                        new SimpleCondition("country", NOT_EQUAL, "UK"),
                        new SimpleCondition("genre", NOT_EQUAL, "Rock")));

        CompoundCondition second = new CompoundCondition(
                AND,
                Set.of(
                        new SimpleCondition("name", NOT_EQUAL, "Marvin Gaye"),
                        new SimpleCondition("name", NOT_EQUAL, "Bjork")));

        CompoundCondition condition = new CompoundCondition(
                AND,
                Set.of(first, second));

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
        CompoundCondition first = new CompoundCondition(
                AND,
                Set.of(
                        new SimpleCondition("country", EQUAL, "UK"),
                        new SimpleCondition("numberOfComponents", EQUAL, 1)));

        CompoundCondition second = new CompoundCondition(
                OR,
                Set.of(
                        new SimpleCondition("genre", EQUAL, "Rock"),
                        new SimpleCondition("numberOfComponents", GREATER_THAN, 1)));

        CompoundCondition condition = new CompoundCondition(
                NOT,
                Set.of(first, second));

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

        CompoundCondition first = new CompoundCondition(
                OR,
                Set.of(
                        new SimpleCondition("country", EQUAL, "UK"),
                        new SimpleCondition("country", EQUAL, "USA")));

        CompoundCondition second = new CompoundCondition(
                OR,
                Set.of(
                        new SimpleCondition("numberOfComponents", EQUAL, 3),
                        new SimpleCondition("numberOfComponents", EQUAL, 4)));

        CompoundCondition condition = new CompoundCondition(
                AND,
                Set.of(first, second));

        Set<MusicArtist> result = sut.evaluate(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), nirvana());
    }

    @Test
    void performEvaluableCompoundConditionNested_mixedOrAnd() {
        CompoundCondition first = new CompoundCondition(
                AND,
                Set.of(
                        new SimpleCondition("country", EQUAL, "USA"),
                        new SimpleCondition("numberOfComponents", NOT_EQUAL, 3),
                        new SimpleCondition("numberOfComponents", NOT_EQUAL, 4)));

        CompoundCondition second = new CompoundCondition(
                AND,
                Set.of(
                        new SimpleCondition("country", EQUAL, "UK"),
                        new SimpleCondition("genre", EQUAL, "Pop")));

        CompoundCondition condition = new CompoundCondition(
                OR,
                Set.of(first, second));

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

        assertThat(result).containsExactlyInAnyOrder(marvinGaye(), bruceSpringsteen(), eltonJohn(), madonna());
    }

    @Test
    void performEvaluableCompoundConditionNested_mixedOrAndNot() {
        CompoundCondition first = new CompoundCondition(
                NOT,
                Set.of(
                        new SimpleCondition("country", EQUAL, "USA"),
                        new SimpleCondition("numberOfComponents", EQUAL, 1)));

        CompoundCondition second = new CompoundCondition(
                NOT,
                Set.of(
                        new SimpleCondition("country", EQUAL, "UK"),
                        new SimpleCondition("numberOfComponents", EQUAL, 1)));

        CompoundCondition condition = new CompoundCondition(
                OR,
                Set.of(first, second));

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
        CompoundCondition nested = new CompoundCondition(
                AND,
                Set.of(
                        new SimpleCondition("country", EQUAL, "UK"),
                        new SimpleCondition("numberOfComponents", LESS_THAN, 4)));

        CompoundCondition condition = new CompoundCondition(
                OR,
                Set.of(
                        new SimpleCondition("name", EQUAL, "Edith Piaf"),
                        nested));

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
        CompoundCondition nested = new CompoundCondition(
                OR,
                Set.of(
                        new SimpleCondition("country", NOT_EQUAL, "UK"),
                        new SimpleCondition("genre", NOT_EQUAL, "Rock")));

        CompoundCondition condition = new CompoundCondition(
                AND,
                Set.of(
                        new SimpleCondition("numberOfComponents", NOT_EQUAL, 1),
                        nested));

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

        assertThat(result).containsExactlyInAnyOrder(nirvana());
    }
}