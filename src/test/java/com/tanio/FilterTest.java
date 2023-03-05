package com.tanio;

import com.tanio.Condition.Operator;
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
        tenFieldEntity.setIntegerField(10);

        List<Entity> result =
                sut.perform(
                        singletonList(tenFieldEntity),
                        condition("integerField", Operator.EQUAL, 10));

        assertThat(result).isEqualTo(singletonList(tenFieldEntity));
    }

    @Test
    void filterSingletonList_rejectingFilter() {
        Entity tenFieldEntity = new Entity();
        tenFieldEntity.setIntegerField(10);

        List<Entity> result =
                sut.perform(
                        singletonList(tenFieldEntity),
                        condition("integerField", Operator.EQUAL, 13));

        assertThat(result).isEqualTo(emptyList());
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 13, 0, -5})
    void filterWithEqualIntegerField_firstElement(int value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setIntegerField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setIntegerField(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("integerField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 13, 0, -5})
    void filterWithEqualIntegerField_secondElement(int value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setIntegerField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setIntegerField(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(nonMatchingEntity, matchingEntity),
                condition("integerField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 13, 0, -5})
    void filterWithEqualIntegerPrimitiveField(int value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setIntegerPrimitiveField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setIntegerPrimitiveField(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("integerPrimitiveField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(longs = {7, 13, 0, -5})
    void filterWithEqualLongField(long value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setLongField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setLongField(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("longField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(longs = {7, 13, 0, -5})
    void filterWithEqualLongPrimitiveField(long value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setLongPrimitiveField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setLongPrimitiveField(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("longPrimitiveField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(shorts = {7, 13, 0, -5})
    void filterWithEqualShortField(short value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setShortField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setShortField((short) (value + 1));

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("shortField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(shorts = {7, 13, 0, -5})
    void filterWithEqualShortPrimitiveField(short value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setShortPrimitiveField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setShortPrimitiveField(((short) (value + 1)));

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("shortPrimitiveField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @Test
    void filterWithEqualStringField() {
        Entity matchingEntity = new Entity();
        matchingEntity.setStringField("Hello");

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setStringField("Bye");

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("stringField", Operator.EQUAL, "Hello"));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void filterWithEqualBooleanPrimitiveField(boolean value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setBooleanPrimitiveField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setBooleanPrimitiveField(!value);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("booleanPrimitiveField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void filterWithEqualBooleanField(boolean value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setBooleanField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setBooleanField(!value);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("booleanField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(floats = {1.3f, -0.5f, 17.11f})
    void filterWithEqualFloatField(float value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setFloatField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setFloatField(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("floatField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(floats = {1.3f, -0.5f, 17.11f})
    void filterWithEqualFloatPrimitiveField(float value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setFloatPrimitiveField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setFloatPrimitiveField(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("floatPrimitiveField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.3, -0.5, 17.11})
    void filterWithEqualDoubleField(double value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setDoubleField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setDoubleField(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("doubleField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.3, -0.5, 17.11})
    void filterWithEqualDoublePrimitiveField(double value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setDoublePrimitiveField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setDoublePrimitiveField(value + 1);

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("doublePrimitiveField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(chars = {'a', 'z', '@'})
    void filterWithEqualCharacterField(char value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setCharField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setCharField((char) (value + 1));

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("charField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    @ParameterizedTest
    @ValueSource(chars = {'a', 'z', '@'})
    void filterWithEqualCharacterPrimitiveField(char value) {
        Entity matchingEntity = new Entity();
        matchingEntity.setCharPrimitiveField(value);

        Entity nonMatchingEntity = new Entity();
        nonMatchingEntity.setCharPrimitiveField((char) (value + 1));

        List<Entity> result = sut.perform(
                Arrays.asList(matchingEntity, nonMatchingEntity),
                condition("charPrimitiveField", Operator.EQUAL, value));

        assertThat(result).isEqualTo(singletonList(matchingEntity));
    }

    Condition condition(String fieldName, Operator operator, Object value) {
        Condition result = new Condition();
        result.fieldName = fieldName;
        result.operator = operator;
        result.value = value;
        return result;
    }
}