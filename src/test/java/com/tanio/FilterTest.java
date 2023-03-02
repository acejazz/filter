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
        List<?> result = sut.perform(emptyList(), new Condition());
        assertThat(result).isEqualTo(emptyList());
    }

    @Test
    void filterSingletonList() {
        Entity tenFieldEntity = new Entity();
        tenFieldEntity.integerField = 10;

        Condition integerFieldEqualToTen = new Condition();
        integerFieldEqualToTen.fieldName = "integer_field";
        integerFieldEqualToTen.operator = "equal";
        integerFieldEqualToTen.value = 10;

        List<Entity> result = sut.perform(singletonList(tenFieldEntity), integerFieldEqualToTen);
        assertThat(result).isEqualTo(singletonList(tenFieldEntity));
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 13, 0, -5})
    void filterWithEqualIntegerField_firstElement(int value) {
        Entity equalValueEntity = new Entity();
        equalValueEntity.integerField = value;

        Entity notEqualValueEntity = new Entity();
        notEqualValueEntity.integerField = value + 1;

        Condition integerFieldEqualToValue = new Condition();
        integerFieldEqualToValue.fieldName = "integer_field";
        integerFieldEqualToValue.operator = "equal";
        integerFieldEqualToValue.value = value;

        List<Entity> result = sut.perform(
                Arrays.asList(equalValueEntity, notEqualValueEntity),
                integerFieldEqualToValue);

        assertThat(result).isEqualTo(singletonList(equalValueEntity));
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 13, 0, -5})
    void filterWithEqualIntegerField_secondElement(int value) {
        Entity equalValueEntity = new Entity();
        equalValueEntity.integerField = value;

        Entity notEqualValueEntity = new Entity();
        notEqualValueEntity.integerField = value + 1;

        Condition integerFieldEqualToValue = new Condition();
        integerFieldEqualToValue.fieldName = "integer_field";
        integerFieldEqualToValue.operator = "equal";
        integerFieldEqualToValue.value = value;

        List<Entity> result = sut.perform(
                Arrays.asList(notEqualValueEntity, equalValueEntity),
                integerFieldEqualToValue);

        assertThat(result).isEqualTo(singletonList(equalValueEntity));
    }
}