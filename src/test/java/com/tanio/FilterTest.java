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
        Entity tenFieldEntity = new Entity();
        tenFieldEntity.integerField = 10;

        List<Entity> result =
                sut.perform(
                        singletonList(tenFieldEntity),
                        condition("integerField", "equal", 10));

        assertThat(result).isEqualTo(singletonList(tenFieldEntity));
    }

    @Test
    void filterSingletonList_rejectingFilter() {
        Entity tenFieldEntity = new Entity();
        tenFieldEntity.integerField = 10;

        List<Entity> result =
                sut.perform(
                        singletonList(tenFieldEntity),
                        condition("integerField", "equal", 13));

        assertThat(result).isEqualTo(emptyList());
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 13, 0, -5})
    void filterWithEqualIntegerField_firstElement(int value) {
        Entity matchingEntity = new Entity();
        matchingEntity.integerField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.integerField = value + 1;

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("integerField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 13, 0, -5})
    void filterWithEqualIntegerField_secondElement(int value) {
        Entity matchingEntity = new Entity();
        matchingEntity.integerField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.integerField = value + 1;

        List<Entity> result = sut.perform(
                Arrays.asList(nonMatchingEntity, matchingEntity),
                condition("integerField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 13, 0, -5})
    void filterWithEqualIntegerPrimitiveField(int value) {
        Entity matchingEntity = new Entity();
        matchingEntity.integerPrimitiveField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.integerPrimitiveField = value + 1;

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("integerPrimitiveField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(longs = {7, 13, 0, -5})
    void filterWithEqualLongField(long value) {
        Entity matchingEntity = new Entity();
        matchingEntity.longField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.longField = value + 1;

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("longField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(longs = {7, 13, 0, -5})
    void filterWithEqualLongPrimitiveField(long value) {
        Entity matchingEntity = new Entity();
        matchingEntity.longPrimitiveField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.longPrimitiveField = value + 1;

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("longPrimitiveField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(shorts = {7, 13, 0, -5})
    void filterWithEqualShortField(short value) {
        Entity matchingEntity = new Entity();
        matchingEntity.shortField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.shortField = ((short) (value + 1));

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("shortField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(shorts = {7, 13, 0, -5})
    void filterWithEqualShortPrimitiveField(short value) {
        Entity matchingEntity = new Entity();
        matchingEntity.shortPrimitiveField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.shortPrimitiveField = ((short) (value + 1));

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("shortPrimitiveField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @Test
    void filterWithEqualStringField() {
        Entity matchingEntity = new Entity();
        matchingEntity.stringField = "Hello";

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.stringField = "Bye";

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("stringField", "equal", "Hello"));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void filterWithEqualBooleanPrimitiveField(boolean value) {
        Entity matchingEntity = new Entity();
        matchingEntity.booleanPrimitiveField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.booleanPrimitiveField = !value;

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("booleanPrimitiveField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void filterWithEqualBooleanField(boolean value) {
        Entity matchingEntity = new Entity();
        matchingEntity.booleanField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.booleanField = !value;

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("booleanField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(floats = {1.3f, -0.5f, 17.11f})
    void filterWithEqualFloatField(float value) {
        Entity matchingEntity = new Entity();
        matchingEntity.floatField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.floatField = value + 1;

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("floatField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(floats = {1.3f, -0.5f, 17.11f})
    void filterWithEqualFloatPrimitiveField(float value) {
        Entity matchingEntity = new Entity();
        matchingEntity.floatPrimitiveField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.floatPrimitiveField = value + 1;

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("floatPrimitiveField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.3, -0.5, 17.11})
    void filterWithEqualDoubleField(double value) {
        Entity matchingEntity = new Entity();
        matchingEntity.doubleField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.doubleField = value + 1;

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("doubleField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.3, -0.5, 17.11})
    void filterWithEqualDoublePrimitiveField(double value) {
        Entity matchingEntity = new Entity();
        matchingEntity.doublePrimitiveField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.doublePrimitiveField = value + 1;

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("doublePrimitiveField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(chars = {'a', 'z', '@'})
    void filterWithEqualCharacterField(char value) {
        Entity matchingEntity = new Entity();
        matchingEntity.charField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.charField = (char) (value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("charField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(chars = {'a', 'z', '@'})
    void filterWithEqualCharacterPrimitiveField(char value) {
        Entity matchingEntity = new Entity();
        matchingEntity.charPrimitiveField = value;

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.charPrimitiveField = (char) (value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("charPrimitiveField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    Condition condition(String fieldName, String operator, Object value) {
        Condition result = new Condition();
        result.fieldName = fieldName;
        result.operator = operator;
        result.value = value;
        return result;
    }
}