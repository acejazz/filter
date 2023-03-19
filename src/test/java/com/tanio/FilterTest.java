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
    void performSimpleCondition() {
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

        Set<MusicArtist> result = sut.apply(new SimpleCondition("country", EQUAL, "UK"), musicArtists);

        assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), eltonJohn());
    }

    @Test
    void performCompoundCondition_or() {
        CompoundCondition condition = new CompoundCondition(
                OR,
                Arrays.asList(
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

        Set<MusicArtist> result = sut.apply(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), eltonJohn(), nirvana());
    }

    @Test
    void performCompoundCondition_and() {
        CompoundCondition condition = new CompoundCondition(
                AND,
                Arrays.asList(
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

        Set<MusicArtist> result = sut.apply(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(madonna(), marvinGaye(), bjork(), edithPiaf(), bruceSpringsteen());
    }

    @Test
    void performCompoundCondition_not() {
        CompoundCondition condition = new CompoundCondition(
                NOT,
                Arrays.asList(
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

        Set<MusicArtist> result = sut.apply(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(bjork(), edithPiaf(), eltonJohn());
    }

    @Test
    void performCompoundConditionNested_or() {
        CompoundCondition first = new CompoundCondition(
                OR,
                Arrays.asList(
                        new SimpleCondition("name", EQUAL, "Rolling Stones"),
                        new SimpleCondition("name", EQUAL, "Madonna")));

        CompoundCondition second = new CompoundCondition(
                OR,
                Arrays.asList(
                        new SimpleCondition("country", EQUAL, "Iceland"),
                        new SimpleCondition("country", EQUAL, "France")));

        CompoundCondition condition = new CompoundCondition(
                OR,
                Arrays.asList(first, second));

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

        Set<MusicArtist> result = sut.apply(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(rollingStones(), madonna(), edithPiaf(), bjork());
    }

    @Test
    void performCompoundConditionNested_and() {
        CompoundCondition first = new CompoundCondition(
                AND,
                Arrays.asList(
                        new SimpleCondition("country", NOT_EQUAL, "UK"),
                        new SimpleCondition("genre", NOT_EQUAL, "Rock")));

        CompoundCondition second = new CompoundCondition(
                AND,
                Arrays.asList(
                        new SimpleCondition("name", NOT_EQUAL, "Marvin Gaye"),
                        new SimpleCondition("name", NOT_EQUAL, "Bjork")));

        CompoundCondition condition = new CompoundCondition(
                AND,
                Arrays.asList(first, second));

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

        Set<MusicArtist> result = sut.apply(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(edithPiaf(), madonna());
    }

    @Test
    void performCompoundConditionNested_not() {
        CompoundCondition first = new CompoundCondition(
                AND,
                Arrays.asList(
                        new SimpleCondition("country", EQUAL, "UK"),
                        new SimpleCondition("numberOfComponents", EQUAL, 1)));

        CompoundCondition second = new CompoundCondition(
                OR,
                Arrays.asList(
                        new SimpleCondition("genre", EQUAL, "Rock"),
                        new SimpleCondition("numberOfComponents", GREATER_THAN, 1)));

        CompoundCondition condition = new CompoundCondition(
                NOT,
                Arrays.asList(first, second));

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

        Set<MusicArtist> result = sut.apply(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(marvinGaye(), bjork(), madonna(), edithPiaf());
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
                OR,
                Arrays.asList(
                        new SimpleCondition("country", EQUAL, "UK"),
                        new SimpleCondition("country", EQUAL, "USA")));

        CompoundCondition second = new CompoundCondition(
                OR,
                Arrays.asList(
                        new SimpleCondition("numberOfComponents", EQUAL, 3),
                        new SimpleCondition("numberOfComponents", EQUAL, 4)));

        CompoundCondition condition = new CompoundCondition(
                AND,
                Arrays.asList(first, second));

        Set<MusicArtist> result = sut.apply(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), nirvana());
    }

    @Test
    void performCompoundConditionNested_mixedOrAnd() {
        CompoundCondition first = new CompoundCondition(
                AND,
                Arrays.asList(
                        new SimpleCondition("country", EQUAL, "USA"),
                        new SimpleCondition("numberOfComponents", NOT_EQUAL, 3),
                        new SimpleCondition("numberOfComponents", NOT_EQUAL, 4)));

        CompoundCondition second = new CompoundCondition(
                AND,
                Arrays.asList(
                        new SimpleCondition("country", EQUAL, "UK"),
                        new SimpleCondition("genre", EQUAL, "Pop")));

        CompoundCondition condition = new CompoundCondition(
                OR,
                Arrays.asList(first, second));

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

        Set<MusicArtist> result = sut.apply(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(marvinGaye(), bruceSpringsteen(), eltonJohn(), madonna());
    }

    @Test
    void performCompoundConditionNested_mixedOrAndNot() {
        CompoundCondition first = new CompoundCondition(
                NOT,
                Arrays.asList(
                        new SimpleCondition("country", EQUAL, "USA"),
                        new SimpleCondition("numberOfComponents", EQUAL, 1)));

        CompoundCondition second = new CompoundCondition(
                NOT,
                Arrays.asList(
                        new SimpleCondition("country", EQUAL, "UK"),
                        new SimpleCondition("numberOfComponents", EQUAL, 1)));

        CompoundCondition condition = new CompoundCondition(
                OR,
                Arrays.asList(first, second));

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

        Set<MusicArtist> result = sut.apply(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), nirvana());
    }

    @Test
    void performAndOr() {
        CompoundCondition nested = new CompoundCondition(
                AND,
                Arrays.asList(
                        new SimpleCondition("country", EQUAL, "UK"),
                        new SimpleCondition("numberOfComponents", LESS_THAN, 4)));

        CompoundCondition condition = new CompoundCondition(
                OR,
                Arrays.asList(
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

        Set<MusicArtist> result = sut.apply(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(eltonJohn(), edithPiaf());
    }

    @Test
    void performOrAnd() {
        CompoundCondition nested = new CompoundCondition(
                OR,
                Arrays.asList(
                        new SimpleCondition("country", NOT_EQUAL, "UK"),
                        new SimpleCondition("genre", NOT_EQUAL, "Rock")));

        CompoundCondition condition = new CompoundCondition(
                AND,
                Arrays.asList(
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

        Set<MusicArtist> result = sut.apply(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(nirvana());
    }
}