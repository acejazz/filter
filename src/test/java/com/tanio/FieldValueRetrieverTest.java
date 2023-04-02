package com.tanio;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.tanio.FieldValueRetriever.BooleanFieldNameHandling.GETTER;
import static com.tanio.FieldValueRetriever.BooleanFieldNameHandling.IS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FieldValueRetrieverTest {
    FieldValueRetriever sut =
            new FieldValueRetriever(new GetterMethodNameBuilderFromCamelCase(), GETTER, ".");

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
        assertThat(sut.retrieveFieldValue("nestedEntity.nestedNestedEntity", testEntity))
                .isEqualTo(nestedNestedEntity);
    }

    @Test
    void handleNonExistingMethodName() {
        assertThatThrownBy(() -> sut.retrieveFieldValue("anyNonExistingFieldName", new Object()))
                .isInstanceOf(FilterException.class)
                .hasMessage("Method [getAnyNonExistingFieldName] does not exist in class [java.lang.Object]");
    }

    @Nested
    class WithSnakeCaseFieldNames {
        FieldValueRetriever sut =
                new FieldValueRetriever(new GetterMethodNameBuilderFromSnakeCase(), IS, ".");

        @Test
        void useGetterMethodNameBuilderFromSnakeCase() {
            TestEntity testEntity = new TestEntity();
            testEntity.setIntegerField(17);
            assertThat(sut.retrieveFieldValue("integer_field", testEntity)).isEqualTo(17);
        }

        @Test
        void retrieveNestedInstance_twoLevels() {
            NestedNestedEntity nestedNestedEntity = new NestedNestedEntity();
            NestedEntity nestedEntity = new NestedEntity();
            nestedEntity.setNestedNestedEntity(nestedNestedEntity);
            TestEntity testEntity = new TestEntity();
            testEntity.setNestedEntity(nestedEntity);
            assertThat(sut.retrieveFieldValue("nested_entity.nested_nested_entity", testEntity))
                    .isEqualTo(nestedNestedEntity);
        }

    }

    @Nested
    class WithGetterBooleanFieldNameHandling {

        FieldValueRetriever sut = new FieldValueRetriever(new GetterMethodNameBuilderFromCamelCase(), GETTER, ".");

        @Test
        void retrieveBooleanFieldValue() {
            BooleanTestEntityGet testEntity = new BooleanTestEntityGet();
            assertThat(sut.retrieveFieldValue("valid", testEntity)).isEqualTo(true);
        }

        @Test
        void retrieveBooleanFieldValueWithWrapper() {
            BooleanTestEntityGet testEntity = new BooleanTestEntityGet();
            assertThat(sut.retrieveFieldValue("validWithWrapper", testEntity)).isEqualTo(true);
        }
    }

    @Nested
    class WithDifferentNestingSeparator {
        FieldValueRetriever sut =
                new FieldValueRetriever(new GetterMethodNameBuilderFromCamelCase(), GETTER, "/");

        @Test
        void retrieveNestedInstance_twoLevels() {
            NestedNestedEntity nestedNestedEntity = new NestedNestedEntity();
            NestedEntity nestedEntity = new NestedEntity();
            nestedEntity.setNestedNestedEntity(nestedNestedEntity);
            TestEntity testEntity = new TestEntity();
            testEntity.setNestedEntity(nestedEntity);
            assertThat(sut.retrieveFieldValue("nestedEntity/nestedNestedEntity", testEntity))
                    .isEqualTo(nestedNestedEntity);
        }
    }
}