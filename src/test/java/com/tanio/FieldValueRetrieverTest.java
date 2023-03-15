package com.tanio;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FieldValueRetrieverTest {
    FieldValueRetriever sut = new FieldValueRetriever();

    @Test
    void retrieveIntegerFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setIntegerField(17);
        assertThat(sut.retrieveFieldValue("integerField", testEntity)).isEqualTo(17);
    }

    @Test
    void retrieveIntegerPrimitiveFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setIntegerPrimitiveField(17);
        assertThat(sut.retrieveFieldValue("integerPrimitiveField", testEntity)).isEqualTo(17);
    }

    @Test
    void retrieveLongFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setLongField(17L);
        assertThat(sut.retrieveFieldValue("longField", testEntity)).isEqualTo(17L);
    }

    @Test
    void retrieveLongPrimitiveFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setLongPrimitiveField(17L);
        assertThat(sut.retrieveFieldValue("longPrimitiveField", testEntity)).isEqualTo(17L);
    }

    @Test
    void retrieveShortFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setShortField((short) 17);
        assertThat(sut.retrieveFieldValue("shortField", testEntity)).isEqualTo((short) 17);
    }

    @Test
    void retrieveShortPrimitiveFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setShortPrimitiveField((short) 17);
        assertThat(sut.retrieveFieldValue("shortPrimitiveField", testEntity)).isEqualTo((short) 17);
    }

    @Test
    void retrieveBooleanFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setBooleanField(true);
        assertThat(sut.retrieveFieldValue("booleanField", testEntity)).isEqualTo(true);
    }

    @Test
    void retrieveBooleanFieldValue_is() {
        BooleanTestEntity testEntity = new BooleanTestEntity();
        assertThat(sut.retrieveFieldValue("isValid", testEntity)).isEqualTo(true);
    }

    @Test
    void retrieveBooleanFieldValue_is_withWrapper() {
        BooleanTestEntity testEntity = new BooleanTestEntity();
        assertThat(sut.retrieveFieldValue("isValidWithWrapper", testEntity)).isEqualTo(true);
    }

    @Test
    void retrieveBooleanPrimitiveFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setBooleanPrimitiveField(true);
        assertThat(sut.retrieveFieldValue("booleanPrimitiveField", testEntity)).isEqualTo(true);
    }

    @Test
    void retrieveFloatFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setFloatField(1.5F);
        assertThat(sut.retrieveFieldValue("floatField", testEntity)).isEqualTo(1.5F);
    }

    @Test
    void retrieveFloatPrimitiveFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setFloatPrimitiveField(1.5F);
        assertThat(sut.retrieveFieldValue("floatPrimitiveField", testEntity)).isEqualTo(1.5F);
    }

    @Test
    void retrieveDoubleFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setDoubleField(1.5);
        assertThat(sut.retrieveFieldValue("doubleField", testEntity)).isEqualTo(1.5);
    }

    @Test
    void retrieveDoublePrimitiveFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setDoublePrimitiveField(1.5);
        assertThat(sut.retrieveFieldValue("doublePrimitiveField", testEntity)).isEqualTo(1.5);
    }

    @Test
    void retrieveCharacterFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setCharField('a');
        assertThat(sut.retrieveFieldValue("charField", testEntity)).isEqualTo('a');
    }

    @Test
    void retrieveCharPrimitiveFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setCharPrimitiveField('a');
        assertThat(sut.retrieveFieldValue("charPrimitiveField", testEntity)).isEqualTo('a');
    }

    @Test
    void retrieveStringFieldValue() {
        TestEntity testEntity = new TestEntity();
        testEntity.setStringField("Hello");
        assertThat(sut.retrieveFieldValue("stringField", testEntity)).isEqualTo("Hello");
    }

    @Test
    void retrieveNestedInstance() {
        NestedEntity nestedEntity = new NestedEntity();
        TestEntity testEntity = new TestEntity();
        testEntity.setNestedEntity(nestedEntity);
        assertThat(sut.retrieveFieldValue("nestedEntity", testEntity)).isEqualTo(nestedEntity);
    }

    @Test
    void retrieveNestedInstance_twoLevels() {
        NestedNestedEntity nestedNestedEntity = new NestedNestedEntity();
        NestedEntity nestedEntity = new NestedEntity();
        nestedEntity.setNestedNestedEntity(nestedNestedEntity);
        TestEntity testEntity = new TestEntity();
        testEntity.setNestedEntity(nestedEntity);
        assertThat(sut.retrieveFieldValue("nestedEntity.nestedNestedEntity", testEntity)).isEqualTo(nestedNestedEntity);
    }
}

class BooleanTestEntity {
    public boolean isValid() {
        return true;
    }
    public Boolean isValidWithWrapper() {
        return true;
    }
}