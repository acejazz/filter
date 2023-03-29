package com.tanio;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.tanio.CompoundCondition.*;
import static com.tanio.Fixture.*;
import static com.tanio.SimpleCondition.*;
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

        assertThat(result).containsExactlyInAnyOrder(madonna(), marvinGaye(), bjork(), edithPiaf(), bruceSpringsteen());
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

        assertThat(result).containsExactlyInAnyOrder(marvinGaye(), bruceSpringsteen(), eltonJohn(), madonna());
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
}