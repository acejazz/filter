package com.tanio;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.tanio.CompoundConditionDto.BooleanOperator.*;
import static com.tanio.SimpleConditionDto.Operator.*;
import static com.tanio.TestPlatform.*;
import static org.assertj.core.api.Assertions.assertThat;

class FilterTest {
    Filter sut = new Filter();

    @Test
    void performCompoundCondition_or() {
        CompoundConditionDto condition = new CompoundConditionDto(
                OR,
                Arrays.asList(
                        new SimpleConditionDto("country", EQUAL, "UK"),
                        new SimpleConditionDto("numberOfComponents", GREATER_THAN, 1)));

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

        assertThat(result)
                .containsExactlyInAnyOrder(
                        beatles(),
                        rollingStones(),
                        eltonJohn(),
                        nirvana());
    }

    @Test
    void performCompoundCondition_and() {
        CompoundConditionDto condition = new CompoundConditionDto(
                AND,
                Arrays.asList(
                        new SimpleConditionDto("country", NOT_EQUAL, "UK"),
                        new SimpleConditionDto("numberOfComponents", LOWER_THAN, 3)));

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

        assertThat(result).containsExactlyInAnyOrder(
                madonna(),
                marvinGaye(),
                bjork(),
                edithPiaf(),
                bruceSpringsteen());
    }

    @Test
    void performCompoundCondition_not() {
        CompoundConditionDto condition = new CompoundConditionDto(
                NOT,
                Arrays.asList(
                        new SimpleConditionDto("genre", EQUAL, "Rock"),
                        new SimpleConditionDto("country", EQUAL, "USA")));

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
        CompoundConditionDto first = new CompoundConditionDto(
                OR,
                Arrays.asList(
                        new SimpleConditionDto("name", EQUAL, "Rolling Stones"),
                        new SimpleConditionDto("name", EQUAL, "Madonna")));

        CompoundConditionDto second = new CompoundConditionDto(
                OR,
                Arrays.asList(
                        new SimpleConditionDto("country", EQUAL, "Iceland"),
                        new SimpleConditionDto("country", EQUAL, "France")));

        CompoundConditionDto condition = new CompoundConditionDto(
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

        assertThat(result)
                .containsExactlyInAnyOrder(rollingStones(), madonna(), edithPiaf(), bjork());
    }

    @Test
    void performCompoundConditionNested_and() {
        CompoundConditionDto first = new CompoundConditionDto(
                AND,
                Arrays.asList(
                        new SimpleConditionDto("country", NOT_EQUAL, "UK"),
                        new SimpleConditionDto("genre", NOT_EQUAL, "Rock")));

        CompoundConditionDto second = new CompoundConditionDto(
                AND,
                Arrays.asList(
                        new SimpleConditionDto("name", NOT_EQUAL, "Marvin Gaye"),
                        new SimpleConditionDto("name", NOT_EQUAL, "Bjork")));

        CompoundConditionDto condition = new CompoundConditionDto(
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
        CompoundConditionDto first = new CompoundConditionDto(
                AND,
                Arrays.asList(
                        new SimpleConditionDto("country", EQUAL, "UK"),
                        new SimpleConditionDto("numberOfComponents", EQUAL, 1)));

        CompoundConditionDto second = new CompoundConditionDto(
                OR,
                Arrays.asList(
                        new SimpleConditionDto("genre", EQUAL, "Rock"),
                        new SimpleConditionDto("numberOfComponents", GREATER_THAN, 1)));

        CompoundConditionDto condition = new CompoundConditionDto(
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

        CompoundConditionDto first = new CompoundConditionDto(
                OR,
                Arrays.asList(
                        new SimpleConditionDto("country", EQUAL, "UK"),
                        new SimpleConditionDto("country", EQUAL, "USA")));

        CompoundConditionDto second = new CompoundConditionDto(
                OR,
                Arrays.asList(
                        new SimpleConditionDto("numberOfComponents", EQUAL, 3),
                        new SimpleConditionDto("numberOfComponents", EQUAL, 4)));

        CompoundConditionDto condition = new CompoundConditionDto(
                AND,
                Arrays.asList(first, second));

        Set<MusicArtist> result = sut.apply(condition, musicArtists);

        assertThat(result).containsExactlyInAnyOrder(beatles(), rollingStones(), nirvana());
    }

    @Test
    void performCompoundConditionNested_mixedOrAnd() {
        CompoundConditionDto first = new CompoundConditionDto(
                AND,
                Arrays.asList(
                        new SimpleConditionDto("country", EQUAL, "USA"),
                        new SimpleConditionDto("numberOfComponents", NOT_EQUAL, 3),
                        new SimpleConditionDto("numberOfComponents", NOT_EQUAL, 4)));

        CompoundConditionDto second = new CompoundConditionDto(
                AND,
                Arrays.asList(
                        new SimpleConditionDto("country", EQUAL, "UK"),
                        new SimpleConditionDto("genre", EQUAL, "Pop")));

        CompoundConditionDto condition = new CompoundConditionDto(
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

        assertThat(result)
                .containsExactlyInAnyOrder(
                        marvinGaye(),
                        bruceSpringsteen(),
                        eltonJohn(),
                        madonna());
    }

    @Test
    void performCompoundConditionNested_mixedOrAndNot() {
        CompoundConditionDto first = new CompoundConditionDto(
                NOT,
                Arrays.asList(
                        new SimpleConditionDto("country", EQUAL, "USA"),
                        new SimpleConditionDto("numberOfComponents", EQUAL, 1)));

        CompoundConditionDto second = new CompoundConditionDto(
                NOT,
                Arrays.asList(
                        new SimpleConditionDto("country", EQUAL, "UK"),
                        new SimpleConditionDto("numberOfComponents", EQUAL, 1)));

        CompoundConditionDto condition = new CompoundConditionDto(
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
        CompoundConditionDto nested = new CompoundConditionDto(
                AND,
                Arrays.asList(
                        new SimpleConditionDto("country", EQUAL, "UK"),
                        new SimpleConditionDto("numberOfComponents", LOWER_THAN, 4)));

        CompoundConditionDto condition = new CompoundConditionDto(
                OR,
                Arrays.asList(
                        new SimpleConditionDto("name", EQUAL, "Edith Piaf"),
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
        CompoundConditionDto nested = new CompoundConditionDto(
                OR,
                Arrays.asList(
                        new SimpleConditionDto("country", NOT_EQUAL, "UK"),
                        new SimpleConditionDto("genre", NOT_EQUAL, "Rock")));

        CompoundConditionDto condition = new CompoundConditionDto(
                AND,
                Arrays.asList(
                        new SimpleConditionDto("numberOfComponents", NOT_EQUAL, 1),
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