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
        Entity tenFieldEntity = entityWithInteger(10);

        List<Entity> result =
                sut.perform(
                        singletonList(tenFieldEntity),
                        condition("integerField", "equal", 10));
        assertThat(result).isEqualTo(singletonList(tenFieldEntity));
    }

    @Test
    void filterSingletonList_rejectingFilter() {
        Entity tenFieldEntity = entityWithInteger(10);

        List<Entity> result =
                sut.perform(
                        singletonList(tenFieldEntity),
                        condition("integerField", "equal", 13));
        assertThat(result).isEqualTo(emptyList());
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 13, 0, -5})
    void filterWithEqualIntegerField_firstElement(int value) {
        Entity matchingEntity = entityWithInteger(value);
        Entity notMatchingEntity = entityWithInteger(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, notMatchingEntity),
                condition("integerField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 13, 0, -5})
    void filterWithEqualIntegerField_secondElement(int value) {
        Entity matchingEntity = entityWithInteger(value);
        Entity notMatchingEntity = entityWithInteger(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(notMatchingEntity, matchingEntity),
                condition("integerField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 13, 0, -5})
    void filterWithEqualIntegerPrimitiveField(int value) {
        Entity matchingEntity = entityWithPrimitiveInteger(value);
        Entity notMatchingEntity = entityWithPrimitiveInteger(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, notMatchingEntity),
                condition("integerPrimitiveField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(longs = {7, 13, 0, -5})
    void filterWithEqualLongField(long value) {
        Entity matchingEntity = entityWithLong(value);
        Entity notMatchingEntity = entityWithLong(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, notMatchingEntity),
                condition("longField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(longs = {7, 13, 0, -5})
    void filterWithEqualLongPrimitiveField(long value) {
        Entity matchingEntity = entityWithPrimitiveLong(value);
        Entity notMatchingEntity = entityWithPrimitiveLong(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, notMatchingEntity),
                condition("longPrimitiveField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(shorts = {7, 13, 0, -5})
    void filterWithEqualShortField(short value) {
        Entity matchingEntity = entityWithShort(value);
        Entity notMatchingEntity = entityWithShort((short) (value + 1));

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, notMatchingEntity),
                condition("shortField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(shorts = {7, 13, 0, -5})
    void filterWithEqualShortPrimitiveField(short value) {
        Entity matchingEntity = entityWithPrimitiveShort(value);
        Entity notMatchingEntity = entityWithPrimitiveShort((short) (value + 1));

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, notMatchingEntity),
                condition("shortPrimitiveField", "equal", value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    Entity entityWithInteger(Integer value) {
        Entity result = new Entity();
        result.integerField = value;
        return result;
    }

    Entity entityWithPrimitiveInteger(int value) {
        Entity result = new Entity();
        result.integerPrimitiveField = value;
        return result;
    }

    Entity entityWithLong(Long value) {
        Entity result = new Entity();
        result.longField = value;
        return result;
    }

    Entity entityWithPrimitiveLong(long value) {
        Entity result = new Entity();
        result.longPrimitiveField = value;
        return result;
    }

    Entity entityWithShort(Short value) {
        Entity result = new Entity();
        result.shortField = value;
        return result;
    }

    Entity entityWithPrimitiveShort(short value) {
        Entity result = new Entity();
        result.shortPrimitiveField = value;
        return result;
    }

    Condition condition(String fieldName, String operator, long value) {
        Condition result = new Condition();
        result.fieldName = fieldName;
        result.operator = operator;
        result.value = value;
        return result;
    }
}