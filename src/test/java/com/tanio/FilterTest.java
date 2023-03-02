package com.tanio;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FilterTest {
    Filter sut = new Filter();

    @Test
    void filterEmptyList() {
        List<?> result = sut.perform(emptyList(), new Condition());
        assertThat(result).isEqualTo(emptyList());
    }

    @Test
    void filterSingletonList() {
        Entity anyEntity = new Entity();
        List<Entity> result = sut.perform(singletonList(anyEntity), new Condition());
        assertThat(result).isEqualTo(singletonList(anyEntity));
    }
}

class Entity {

}