package com.tanio;

import com.tanio.Condition.Operator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class FilterTest {
    Filter sut = new Filter();

    @Nested
    class SingleCondition {
        @Test
        void filterEmptyList() {
            Condition anyCondition = new Condition();
            List<TestEntity> result = sut.perform(emptyList(), anyCondition);
            assertThat(result).isEqualTo(emptyList());
        }

        @Test
        void filterWithEqualIntegerField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setIntegerField(7);

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setIntegerField(11);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(matchingTestEntity, nonMatchingTestEntity),
                    condition("integerField", Operator.EQUAL, 7));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualIntegerPrimitiveField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setIntegerPrimitiveField(7);

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setIntegerPrimitiveField(13);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(nonMatchingTestEntity, matchingTestEntity),
                    condition("integerPrimitiveField", Operator.EQUAL, 7));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualLongField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setLongField(7L);

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setLongField(13L);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(matchingTestEntity, nonMatchingTestEntity),
                    condition("longField", Operator.EQUAL, 7L));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualLongPrimitiveField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setLongPrimitiveField(7);

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setLongPrimitiveField(13);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(nonMatchingTestEntity, matchingTestEntity),
                    condition("longPrimitiveField", Operator.EQUAL, 7));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualShortField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setShortField((short) 7);

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setShortField((short) 13);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(matchingTestEntity, nonMatchingTestEntity),
                    condition("shortField", Operator.EQUAL, (short) 7));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualShortPrimitiveField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setShortPrimitiveField((short) 7);

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setShortPrimitiveField((short) 13);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(nonMatchingTestEntity, matchingTestEntity),
                    condition("shortPrimitiveField", Operator.EQUAL, (short) 7));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualStringField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setStringField("Hello");

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setStringField("Bye");

            List<TestEntity> result = sut.perform(
                    Arrays.asList(matchingTestEntity, nonMatchingTestEntity),
                    condition("stringField", Operator.EQUAL, "Hello"));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualBooleanField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setBooleanField(true);

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setBooleanField(false);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(matchingTestEntity, nonMatchingTestEntity),
                    condition("booleanField", Operator.EQUAL, true));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualBooleanPrimitiveField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setBooleanPrimitiveField(true);

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setBooleanPrimitiveField(false);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(nonMatchingTestEntity, matchingTestEntity),
                    condition("booleanPrimitiveField", Operator.EQUAL, true));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualFloatField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setFloatField(1.3F);

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setFloatField(-9.1F);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(matchingTestEntity, nonMatchingTestEntity),
                    condition("floatField", Operator.EQUAL, 1.3F));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualFloatPrimitiveField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setFloatPrimitiveField(1.3F);

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setFloatPrimitiveField(-9.1F);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(nonMatchingTestEntity, matchingTestEntity),
                    condition("floatPrimitiveField", Operator.EQUAL, 1.3F));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualDoubleField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setDoubleField(1.3);

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setDoubleField(-9.1);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(matchingTestEntity, nonMatchingTestEntity),
                    condition("doubleField", Operator.EQUAL, 1.3));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualDoublePrimitiveField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setDoublePrimitiveField(1.3);

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setDoublePrimitiveField(-9.1);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(nonMatchingTestEntity, matchingTestEntity),
                    condition("doublePrimitiveField", Operator.EQUAL, 1.3));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualCharacterField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setCharField('a');

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setCharField('z');

            List<TestEntity> result = sut.perform(
                    Arrays.asList(matchingTestEntity, nonMatchingTestEntity),
                    condition("charField", Operator.EQUAL, 'a'));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterWithEqualCharacterPrimitiveField() {
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setCharPrimitiveField('a');

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setCharPrimitiveField('z');

            List<TestEntity> result = sut.perform(
                    Arrays.asList(nonMatchingTestEntity, matchingTestEntity),
                    condition("charPrimitiveField", Operator.EQUAL, 'a'));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterFromNestedObject() {
            NestedEntity matchingNestedEntity = new NestedEntity();
            matchingNestedEntity.setStringField("anything");

            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setNestedEntity(matchingNestedEntity);

            NestedEntity nonMatchingNestedEntity = new NestedEntity();
            nonMatchingNestedEntity.setStringField("notAnything");

            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setNestedEntity(nonMatchingNestedEntity);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(matchingTestEntity, nonMatchingTestEntity),
                    condition("nestedEntity.stringField", Operator.EQUAL, "anything"));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }

        @Test
        void filterFromNestedObject_twoLevels() {
            NestedNestedEntity nestedNestedEntity = new NestedNestedEntity();
            nestedNestedEntity.setStringField("anything");
            NestedEntity matchingNestedEntity = new NestedEntity();
            matchingNestedEntity.setNestedNestedEntity(nestedNestedEntity);
            TestEntity matchingTestEntity = new TestEntity();
            matchingTestEntity.setNestedEntity(matchingNestedEntity);

            NestedNestedEntity nonMatchingNestedNestedEntity = new NestedNestedEntity();
            nonMatchingNestedNestedEntity.setStringField("notAnything");
            NestedEntity nonMatchingNestedEntity = new NestedEntity();
            nonMatchingNestedEntity.setNestedNestedEntity(nonMatchingNestedNestedEntity);
            TestEntity nonMatchingTestEntity = new TestEntity();
            nonMatchingTestEntity.setNestedEntity(nonMatchingNestedEntity);

            List<TestEntity> result = sut.perform(
                    Arrays.asList(matchingTestEntity, nonMatchingTestEntity),
                    condition("nestedEntity.nestedNestedEntity.stringField", Operator.EQUAL, "anything"));

            assertThat(result).isEqualTo(singletonList(matchingTestEntity));
        }
    }

    @Nested
    class MultipleConditions {
        @Test
        void performCompoundCondition_or() {
            TestEntity firstConditionMatchingTestEntity = new TestEntity();
            firstConditionMatchingTestEntity.setStringField("hello");

            TestEntity secondConditionMatchingTestEntity = new TestEntity();
            secondConditionMatchingTestEntity.setStringField("bye");

            TestEntity notMatchingTestEntity = new TestEntity();
            notMatchingTestEntity.setStringField("sup");

            CompoundCondition compoundCondition = new CompoundCondition();
            compoundCondition.booleanOperator = BooleanOperator.OR;
            compoundCondition.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("stringField", Operator.EQUAL, "bye"));

            List<TestEntity> result = sut.perform(
                    Arrays.asList(
                            firstConditionMatchingTestEntity,
                            secondConditionMatchingTestEntity,
                            notMatchingTestEntity),
                    compoundCondition);

            assertThat(result).containsExactlyInAnyOrder(firstConditionMatchingTestEntity, secondConditionMatchingTestEntity);
        }

        @Test
        void performCompoundCondition_and() {
            TestEntity conditionMatchingTestEntity = new TestEntity();
            conditionMatchingTestEntity.setStringField("hello");
            conditionMatchingTestEntity.setIntegerField(13);

            TestEntity notMatchingTestEntity0 = new TestEntity();
            notMatchingTestEntity0.setStringField("hello");

            TestEntity notMatchingTestEntity1 = new TestEntity();
            notMatchingTestEntity1.setIntegerField(13);

            CompoundCondition compoundCondition = new CompoundCondition();
            compoundCondition.booleanOperator = BooleanOperator.AND;
            compoundCondition.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("integerField", Operator.EQUAL, 13));

            List<TestEntity> result = sut.perform(
                    Arrays.asList(
                            conditionMatchingTestEntity,
                            notMatchingTestEntity0,
                            notMatchingTestEntity1),
                    compoundCondition);

            assertThat(result).containsExactlyInAnyOrder(conditionMatchingTestEntity);
        }

        @Test
        void performCompoundCondition_not() {
            TestEntity conditionMatchingTestEntity = new TestEntity();

            TestEntity notMatchingTestEntity0 = new TestEntity();
            notMatchingTestEntity0.setStringField("hello");

            TestEntity notMatchingTestEntity1 = new TestEntity();
            notMatchingTestEntity1.setIntegerField(13);

            TestEntity notMatchingTestEntity2 = new TestEntity();
            notMatchingTestEntity2.setStringField("hello");
            notMatchingTestEntity2.setIntegerField(13);

            CompoundCondition compoundCondition = new CompoundCondition();
            compoundCondition.booleanOperator = BooleanOperator.NOT;
            compoundCondition.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("integerField", Operator.EQUAL, 13));

            List<TestEntity> result = sut.perform(
                    Arrays.asList(
                            conditionMatchingTestEntity,
                            notMatchingTestEntity0,
                            notMatchingTestEntity1,
                            notMatchingTestEntity2),
                    compoundCondition);

            assertThat(result).containsExactlyInAnyOrder(conditionMatchingTestEntity);
        }
    }

    @Nested
    class NestedConditions {
        @Test
        void performCompoundConditionNested_or() {
            CompoundCondition nestedCondition0 = new CompoundCondition();
            nestedCondition0.booleanOperator = BooleanOperator.OR;
            nestedCondition0.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("stringField", Operator.EQUAL, "bye"));

            CompoundCondition nestedCondition1 = new CompoundCondition();
            nestedCondition1.booleanOperator = BooleanOperator.OR;
            nestedCondition1.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "good morning"),
                    condition("stringField", Operator.EQUAL, "good night"));

            CompoundCondition outerCondition = new CompoundCondition();
            outerCondition.booleanOperator = BooleanOperator.OR;
            outerCondition.nestedConditions = new ArrayList<>();
            outerCondition.nestedConditions.add(nestedCondition0);
            outerCondition.nestedConditions.add(nestedCondition1);

            TestEntity testEntity0 = new TestEntity();
            testEntity0.setStringField("hello");

            TestEntity testEntity1 = new TestEntity();
            testEntity1.setStringField("bye");

            TestEntity testEntity2 = new TestEntity();
            testEntity2.setStringField("good morning");

            TestEntity testEntity3 = new TestEntity();
            testEntity3.setStringField("good night");

            TestEntity testEntity4 = new TestEntity();
            testEntity4.setStringField("batman");

            List<TestEntity> result = sut.perform(Arrays.asList(testEntity0, testEntity1, testEntity2, testEntity3, testEntity4), outerCondition);

            assertThat(result).containsExactlyInAnyOrder(testEntity0, testEntity1, testEntity2, testEntity3);
        }

        @Test
        void performCompoundConditionNested_and() {
            CompoundCondition nestedCondition0 = new CompoundCondition();
            nestedCondition0.booleanOperator = BooleanOperator.AND;
            nestedCondition0.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("integerField", Operator.EQUAL, 13));

            CompoundCondition nestedCondition1 = new CompoundCondition();
            nestedCondition1.booleanOperator = BooleanOperator.AND;
            nestedCondition1.conditions = Arrays.asList(
                    condition("booleanField", Operator.EQUAL, true),
                    condition("charField", Operator.EQUAL, 'a'));

            CompoundCondition outerCondition = new CompoundCondition();
            outerCondition.booleanOperator = BooleanOperator.AND;
            outerCondition.nestedConditions = new ArrayList<>();
            outerCondition.nestedConditions.add(nestedCondition0);
            outerCondition.nestedConditions.add(nestedCondition1);

            TestEntity testEntity0 = new TestEntity();
            testEntity0.setStringField("hello");
            testEntity0.setIntegerField(13);
            testEntity0.setBooleanField(true);
            testEntity0.setCharField('a');

            TestEntity testEntity1 = new TestEntity();
            testEntity1.setStringField("bye");
            testEntity1.setIntegerField(13);
            testEntity1.setBooleanField(true);
            testEntity1.setCharField('a');

            TestEntity testEntity2 = new TestEntity();
            testEntity2.setStringField("hello");
            testEntity2.setIntegerField(17);
            testEntity2.setBooleanField(true);
            testEntity2.setCharField('a');

            TestEntity testEntity3 = new TestEntity();
            testEntity3.setStringField("hello");
            testEntity3.setIntegerField(13);
            testEntity3.setBooleanField(false);
            testEntity3.setCharField('a');

            TestEntity testEntity4 = new TestEntity();
            testEntity4.setStringField("hello");
            testEntity4.setIntegerField(13);
            testEntity4.setBooleanField(true);
            testEntity4.setCharField('z');

            List<TestEntity> result = sut.perform(Arrays.asList(testEntity0, testEntity1, testEntity2, testEntity3, testEntity4), outerCondition);

            assertThat(result).containsExactly(testEntity0);
        }

        @Test
        void performCompoundConditionNested_not() {
            CompoundCondition nestedCondition0 = new CompoundCondition();
            nestedCondition0.booleanOperator = BooleanOperator.OR;
            nestedCondition0.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("stringField", Operator.EQUAL, "bye"));

            CompoundCondition nestedCondition1 = new CompoundCondition();
            nestedCondition1.booleanOperator = BooleanOperator.OR;
            nestedCondition1.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "good morning"),
                    condition("stringField", Operator.EQUAL, "good night"));

            CompoundCondition outerCondition = new CompoundCondition();
            outerCondition.booleanOperator = BooleanOperator.NOT;
            outerCondition.nestedConditions = new ArrayList<>();
            outerCondition.nestedConditions.add(nestedCondition0);
            outerCondition.nestedConditions.add(nestedCondition1);

            TestEntity testEntity0 = new TestEntity();
            testEntity0.setStringField("hello");

            TestEntity testEntity1 = new TestEntity();
            testEntity1.setStringField("bye");

            TestEntity testEntity2 = new TestEntity();
            testEntity2.setStringField("good morning");

            TestEntity testEntity3 = new TestEntity();
            testEntity3.setStringField("good night");

            TestEntity testEntity4 = new TestEntity();
            testEntity4.setStringField("batman");

            List<TestEntity> result = sut.perform(Arrays.asList(testEntity0, testEntity1, testEntity2, testEntity3, testEntity4), outerCondition);

            assertThat(result).containsExactlyInAnyOrder(testEntity4);

//            write tests for nested conditions, perhaps with new test entities
        }
    }

    Condition condition(String fieldName, Operator operator, Object value) {
        Condition result = new Condition();
        result.fieldName = fieldName;
        result.operator = operator;
        result.value = value;
        return result;
    }
}