package com.tanio;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FilterTest {
    @Test
    void filter() {
        Filter filter = new Filter();
        List result = filter.perform(emptyList(), new Condition());
        assertThat(result).isEqualTo(emptyList());
    }
}