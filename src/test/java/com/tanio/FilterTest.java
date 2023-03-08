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
            List<Entity> result = sut.perform(emptyList(), anyCondition);
            assertThat(result).isEqualTo(emptyList());
        }

        @Test
        void filterWithEqualIntegerField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setIntegerField(7);

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setIntegerField(11);

            List<Entity> result = sut.perform(
                    Arrays.asList(matchingEntity, nonMatchingEntity),
                    condition("integerField", Operator.EQUAL, 7));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterWithEqualIntegerPrimitiveField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setIntegerPrimitiveField(7);

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setIntegerPrimitiveField(13);

            List<Entity> result = sut.perform(
                    Arrays.asList(nonMatchingEntity, matchingEntity),
                    condition("integerPrimitiveField", Operator.EQUAL, 7));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterWithEqualLongField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setLongField(7L);

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setLongField(13L);

            List<Entity> result = sut.perform(
                    Arrays.asList(matchingEntity, nonMatchingEntity),
                    condition("longField", Operator.EQUAL, 7L));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterWithEqualLongPrimitiveField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setLongPrimitiveField(7);

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setLongPrimitiveField(13);

            List<Entity> result = sut.perform(
                    Arrays.asList(nonMatchingEntity, matchingEntity),
                    condition("longPrimitiveField", Operator.EQUAL, 7));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterWithEqualShortField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setShortField((short) 7);

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setShortField((short) 13);

            List<Entity> result = sut.perform(
                    Arrays.asList(matchingEntity, nonMatchingEntity),
                    condition("shortField", Operator.EQUAL, (short) 7));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterWithEqualShortPrimitiveField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setShortPrimitiveField((short) 7);

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setShortPrimitiveField((short) 13);

            List<Entity> result = sut.perform(
                    Arrays.asList(nonMatchingEntity, matchingEntity),
                    condition("shortPrimitiveField", Operator.EQUAL, (short) 7));

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

        @Test
        void filterWithEqualBooleanField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setBooleanField(true);

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setBooleanField(false);

            List<Entity> result = sut.perform(
                    Arrays.asList(matchingEntity, nonMatchingEntity),
                    condition("booleanField", Operator.EQUAL, true));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterWithEqualBooleanPrimitiveField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setBooleanPrimitiveField(true);

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setBooleanPrimitiveField(false);

            List<Entity> result = sut.perform(
                    Arrays.asList(nonMatchingEntity, matchingEntity),
                    condition("booleanPrimitiveField", Operator.EQUAL, true));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterWithEqualFloatField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setFloatField(1.3F);

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setFloatField(-9.1F);

            List<Entity> result = sut.perform(
                    Arrays.asList(matchingEntity, nonMatchingEntity),
                    condition("floatField", Operator.EQUAL, 1.3F));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterWithEqualFloatPrimitiveField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setFloatPrimitiveField(1.3F);

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setFloatPrimitiveField(-9.1F);

            List<Entity> result = sut.perform(
                    Arrays.asList(nonMatchingEntity, matchingEntity),
                    condition("floatPrimitiveField", Operator.EQUAL, 1.3F));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterWithEqualDoubleField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setDoubleField(1.3);

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setDoubleField(-9.1);

            List<Entity> result = sut.perform(
                    Arrays.asList(matchingEntity, nonMatchingEntity),
                    condition("doubleField", Operator.EQUAL, 1.3));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterWithEqualDoublePrimitiveField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setDoublePrimitiveField(1.3);

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setDoublePrimitiveField(-9.1);

            List<Entity> result = sut.perform(
                    Arrays.asList(nonMatchingEntity, matchingEntity),
                    condition("doublePrimitiveField", Operator.EQUAL, 1.3));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterWithEqualCharacterField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setCharField('a');

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setCharField('z');

            List<Entity> result = sut.perform(
                    Arrays.asList(matchingEntity, nonMatchingEntity),
                    condition("charField", Operator.EQUAL, 'a'));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterWithEqualCharacterPrimitiveField() {
            Entity matchingEntity = new Entity();
            matchingEntity.setCharPrimitiveField('a');

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setCharPrimitiveField('z');

            List<Entity> result = sut.perform(
                    Arrays.asList(nonMatchingEntity, matchingEntity),
                    condition("charPrimitiveField", Operator.EQUAL, 'a'));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterFromNestedObject() {
            NestedEntity matchingNestedEntity = new NestedEntity();
            matchingNestedEntity.setStringField("anything");

            Entity matchingEntity = new Entity();
            matchingEntity.setNestedEntity(matchingNestedEntity);

            NestedEntity nonMatchingNestedEntity = new NestedEntity();
            nonMatchingNestedEntity.setStringField("notAnything");

            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setNestedEntity(nonMatchingNestedEntity);

            List<Entity> result = sut.perform(
                    Arrays.asList(matchingEntity, nonMatchingEntity),
                    condition("nestedEntity.stringField", Operator.EQUAL, "anything"));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }

        @Test
        void filterFromNestedObject_twoLevels() {
            NestedNestedEntity nestedNestedEntity = new NestedNestedEntity();
            nestedNestedEntity.setStringField("anything");
            NestedEntity matchingNestedEntity = new NestedEntity();
            matchingNestedEntity.setNestedNestedEntity(nestedNestedEntity);
            Entity matchingEntity = new Entity();
            matchingEntity.setNestedEntity(matchingNestedEntity);

            NestedNestedEntity nonMatchingNestedNestedEntity = new NestedNestedEntity();
            nonMatchingNestedNestedEntity.setStringField("notAnything");
            NestedEntity nonMatchingNestedEntity = new NestedEntity();
            nonMatchingNestedEntity.setNestedNestedEntity(nonMatchingNestedNestedEntity);
            Entity nonMatchingEntity = new Entity();
            nonMatchingEntity.setNestedEntity(nonMatchingNestedEntity);

            List<Entity> result = sut.perform(
                    Arrays.asList(matchingEntity, nonMatchingEntity),
                    condition("nestedEntity.nestedNestedEntity.stringField", Operator.EQUAL, "anything"));

            assertThat(result).isEqualTo(singletonList(matchingEntity));
        }
    }

    @Nested
    class MultipleConditions {
        @Test
        void performCompoundCondition_or() {
            Entity firstConditionMatchingEntity = new Entity();
            firstConditionMatchingEntity.setStringField("hello");

            Entity secondConditionMatchingEntity = new Entity();
            secondConditionMatchingEntity.setStringField("bye");

            Entity notMatchingEntity = new Entity();
            notMatchingEntity.setStringField("sup");

            CompoundCondition compoundCondition = new CompoundCondition();
            compoundCondition.booleanOperator = BooleanOperator.OR;
            compoundCondition.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("stringField", Operator.EQUAL, "bye"));

            List<Entity> result = sut.perform(
                    Arrays.asList(
                            firstConditionMatchingEntity,
                            secondConditionMatchingEntity,
                            notMatchingEntity),
                    compoundCondition);

            assertThat(result).containsExactlyInAnyOrder(firstConditionMatchingEntity, secondConditionMatchingEntity);
        }

        @Test
        void performCompoundCondition_and() {
            Entity conditionMatchingEntity = new Entity();
            conditionMatchingEntity.setStringField("hello");
            conditionMatchingEntity.setIntegerField(13);

            Entity notMatchingEntity0 = new Entity();
            notMatchingEntity0.setStringField("hello");

            Entity notMatchingEntity1 = new Entity();
            notMatchingEntity1.setIntegerField(13);

            CompoundCondition compoundCondition = new CompoundCondition();
            compoundCondition.booleanOperator = BooleanOperator.AND;
            compoundCondition.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("integerField", Operator.EQUAL, 13));

            List<Entity> result = sut.perform(
                    Arrays.asList(
                            conditionMatchingEntity,
                            notMatchingEntity0,
                            notMatchingEntity1),
                    compoundCondition);

            assertThat(result).containsExactlyInAnyOrder(conditionMatchingEntity);
        }

        @Test
        void performCompoundCondition_not() {
            Entity conditionMatchingEntity = new Entity();

            Entity notMatchingEntity0 = new Entity();
            notMatchingEntity0.setStringField("hello");

            Entity notMatchingEntity1 = new Entity();
            notMatchingEntity1.setIntegerField(13);

            Entity notMatchingEntity2 = new Entity();
            notMatchingEntity2.setStringField("hello");
            notMatchingEntity2.setIntegerField(13);

            CompoundCondition compoundCondition = new CompoundCondition();
            compoundCondition.booleanOperator = BooleanOperator.NOT;
            compoundCondition.conditions = Arrays.asList(
                    condition("stringField", Operator.EQUAL, "hello"),
                    condition("integerField", Operator.EQUAL, 13));

            List<Entity> result = sut.perform(
                    Arrays.asList(
                            conditionMatchingEntity,
                            notMatchingEntity0,
                            notMatchingEntity1,
                            notMatchingEntity2),
                    compoundCondition);

            assertThat(result).containsExactlyInAnyOrder(conditionMatchingEntity);
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

            Entity entity0 = new Entity();
            entity0.setStringField("hello");

            Entity entity1 = new Entity();
            entity1.setStringField("bye");

            Entity entity2 = new Entity();
            entity2.setStringField("good morning");

            Entity entity3 = new Entity();
            entity3.setStringField("good night");

            Entity entity4 = new Entity();
            entity4.setStringField("batman");

            List<Entity> result = sut.perform(Arrays.asList(entity0, entity1, entity2, entity3, entity4), outerCondition);

            assertThat(result).containsExactlyInAnyOrder(entity0, entity1, entity2, entity3);
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

            Entity entity0 = new Entity();
            entity0.setStringField("hello");
            entity0.setIntegerField(13);
            entity0.setBooleanField(true);
            entity0.setCharField('a');

            Entity entity1 = new Entity();
            entity1.setStringField("bye");
            entity1.setIntegerField(13);
            entity1.setBooleanField(true);
            entity1.setCharField('a');

            Entity entity2 = new Entity();
            entity2.setStringField("hello");
            entity2.setIntegerField(17);
            entity2.setBooleanField(true);
            entity2.setCharField('a');

            Entity entity3 = new Entity();
            entity3.setStringField("hello");
            entity3.setIntegerField(13);
            entity3.setBooleanField(false);
            entity3.setCharField('a');

            Entity entity4 = new Entity();
            entity4.setStringField("hello");
            entity4.setIntegerField(13);
            entity4.setBooleanField(true);
            entity4.setCharField('z');

            List<Entity> result = sut.perform(Arrays.asList(entity0, entity1, entity2, entity3, entity4), outerCondition);

            assertThat(result).containsExactly(entity0);
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

            Entity entity0 = new Entity();
            entity0.setStringField("hello");

            Entity entity1 = new Entity();
            entity1.setStringField("bye");

            Entity entity2 = new Entity();
            entity2.setStringField("good morning");

            Entity entity3 = new Entity();
            entity3.setStringField("good night");

            Entity entity4 = new Entity();
            entity4.setStringField("batman");

            List<Entity> result = sut.perform(Arrays.asList(entity0, entity1, entity2, entity3, entity4), outerCondition);

            assertThat(result).containsExactlyInAnyOrder(entity4);
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