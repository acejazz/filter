package com.tanio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterTest {
    Filter sut = new Filter();

    @Test
    void filterEmptyList() {
        Condition anyCondition = new Condition();
        List<Entity> result = sut.perform(emptyList(), anyCondition);
        assertThat(result).isEqualTo(emptyList());
    }

    @Test
    void filterSingletonList_passingFilter() {
        Entity tenFieldEntity = entity(10);

        List<Entity> result =
                sut.perform(
                        singletonList(tenFieldEntity),
                        condition("integer_field", "equal", 10));
        assertThat(result).isEqualTo(singletonList(tenFieldEntity));
    }

    @Test
    void filterSingletonList_rejectingFilter() {
        Entity tenFieldEntity = entity(10);

        List<Entity> result =
                sut.perform(
                        singletonList(tenFieldEntity),
                        condition("integer_field", "equal", 13));
        assertThat(result).isEqualTo(emptyList());
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 13, 0, -5})
    void filterWithEqualIntegerField_firstElement(int value) {
        Entity equalValueEntity = entity(value);
        Entity notEqualValueEntity = entity(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(equalValueEntity, notEqualValueEntity),
                condition("integer_field", "equal", value));

        assertThat(result).isEqualTo(singletonList(equalValueEntity));
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 13, 0, -5})
    void filterWithEqualIntegerField_secondElement(int value) {
        Entity equalValueEntity = entity(value);
        Entity notEqualValueEntity = entity(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(notEqualValueEntity, equalValueEntity),
                condition("integer_field", "equal", value));

        assertThat(result).isEqualTo(singletonList(equalValueEntity));
    }

    Entity entity(int value) {
        Entity result = new Entity();
        result.integerField = value;
        return result;
    }

    Condition condition(String fieldName, String operator, int value) {
        Condition result = new Condition();
        result.fieldName = fieldName;
        result.operator = operator;
        result.value = value;
        return result;
    }
}