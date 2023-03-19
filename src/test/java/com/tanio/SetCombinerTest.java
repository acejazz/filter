package com.tanio;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SetCombinerTest {
    SetCombiner sut = new SetCombiner();

    @Test
    void or() {
        Set<String> set0 = Set.of("first", "second");
        Set<String> set1 = Set.of("second", "third");

        Set<String> result = sut.or(List.of(set0, set1));

        assertThat(result).containsExactlyInAnyOrder("first", "second", "third");
    }

    @Test
    void and() {
        Set<String> set0 = Set.of("first", "second");
        Set<String> set1 = Set.of("second", "third");

        Set<String> result = sut.and(List.of(set0, set1));

        assertThat(result).containsExactlyInAnyOrder("second");
    }

    @Test
    void not() {
        List<String> universe = List.of("first", "second", "third", "fourth");
        Set<String> set0 = Set.of("first", "second");
        Set<String> set1 = Set.of("first", "fourth");

        Set<String> result = sut.not(universe, List.of(set0, set1));

        assertThat(result).containsExactlyInAnyOrder("third");
    }
}