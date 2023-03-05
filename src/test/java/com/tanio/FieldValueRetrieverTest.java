package com.tanio;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FieldValueRetrieverTest {
    FieldValueRetriever sut = new FieldValueRetriever();

    @Test
    void retrieveIntegerFieldValue() {
        Entity entity = new Entity();
        entity.setIntegerField(17);
        assertThat(sut.retrieveFieldValue("integerField", entity)).isEqualTo(17);
    }

    @Test
    void retrieveIntegerPrimitiveFieldValue() {
        Entity entity = new Entity();
        entity.setIntegerPrimitiveField(17);
        assertThat(sut.retrieveFieldValue("integerPrimitiveField", entity)).isEqualTo(17);
    }

    @Test
    void retrieveLongFieldValue() {
        Entity entity = new Entity();
        entity.setLongField(17L);
        assertThat(sut.retrieveFieldValue("longField", entity)).isEqualTo(17L);
    }

    @Test
    void retrieveLongPrimitiveFieldValue() {
        Entity entity = new Entity();
        entity.setLongPrimitiveField(17L);
        assertThat(sut.retrieveFieldValue("longPrimitiveField", entity)).isEqualTo(17L);
    }

    @Test
    void retrieveShortFieldValue() {
        Entity entity = new Entity();
        entity.setShortField((short) 17);
        assertThat(sut.retrieveFieldValue("shortField", entity)).isEqualTo((short) 17);
    }

    @Test
    void retrieveShortPrimitiveFieldValue() {
        Entity entity = new Entity();
        entity.setShortPrimitiveField((short) 17);
        assertThat(sut.retrieveFieldValue("shortPrimitiveField", entity)).isEqualTo((short) 17);
    }

    @Test
    void retrieveBooleanFieldValue() {
        Entity entity = new Entity();
        entity.setBooleanField(true);
        assertThat(sut.retrieveFieldValue("booleanField", entity)).isEqualTo(true);
    }

    @Test
    void retrieveBooleanPrimitiveFieldValue() {
        Entity entity = new Entity();
        entity.setBooleanPrimitiveField(true);
        assertThat(sut.retrieveFieldValue("booleanPrimitiveField", entity)).isEqualTo(true);
    }

    @Test
    void retrieveFloatFieldValue() {
        Entity entity = new Entity();
        entity.setFloatField(1.5F);
        assertThat(sut.retrieveFieldValue("floatField", entity)).isEqualTo(1.5F);
    }

    @Test
    void retrieveFloatPrimitiveFieldValue() {
        Entity entity = new Entity();
        entity.setFloatPrimitiveField(1.5F);
        assertThat(sut.retrieveFieldValue("floatPrimitiveField", entity)).isEqualTo(1.5F);
    }

    @Test
    void retrieveDoubleFieldValue() {
        Entity entity = new Entity();
        entity.setDoubleField(1.5);
        assertThat(sut.retrieveFieldValue("doubleField", entity)).isEqualTo(1.5);
    }

    @Test
    void retrieveDoublePrimitiveFieldValue() {
        Entity entity = new Entity();
        entity.setDoublePrimitiveField(1.5);
        assertThat(sut.retrieveFieldValue("doublePrimitiveField", entity)).isEqualTo(1.5);
    }

    @Test
    void retrieveCharacterFieldValue() {
        Entity entity = new Entity();
        entity.setCharField('a');
        assertThat(sut.retrieveFieldValue("charField", entity)).isEqualTo('a');
    }

    @Test
    void retrieveCharPrimitiveFieldValue() {
        Entity entity = new Entity();
        entity.setCharPrimitiveField('a');
        assertThat(sut.retrieveFieldValue("charPrimitiveField", entity)).isEqualTo('a');
    }

    @Test
    void retrieveStringFieldValue() {
        Entity entity = new Entity();
        entity.setStringField("Hello");
        assertThat(sut.retrieveFieldValue("stringField", entity)).isEqualTo("Hello");
    }

    @Test
    void retrieveNestedInstance() {
        NestedEntity nestedEntity = new NestedEntity();
        Entity entity = new Entity();
        entity.setNestedEntity(nestedEntity);
        assertThat(sut.retrieveFieldValue("nestedEntity", entity)).isEqualTo(nestedEntity);
    }

    @Test
    void retrieveNestedInstance_twoLevels() {
        NestedNestedEntity nestedNestedEntity = new NestedNestedEntity();
        NestedEntity nestedEntity = new NestedEntity();
        nestedEntity.setNestedNestedEntity(nestedNestedEntity);
        Entity entity = new Entity();
        entity.setNestedEntity(nestedEntity);
        assertThat(sut.retrieveFieldValue("nestedEntity.nestedNestedEntity", entity)).isEqualTo(nestedNestedEntity);
    }
}