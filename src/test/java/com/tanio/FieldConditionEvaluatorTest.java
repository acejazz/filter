package com.tanio;

import com.tanio.Condition.Operator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldConditionEvaluatorTest {
    FieldConditionEvaluator sut = new FieldConditionEvaluator();

    @Nested
    class IntegerTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(Operator.EQUAL, 5, 5));
            assertFalse(sut.evaluateCondition(Operator.EQUAL, 7, 5));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(Operator.NOT_EQUAL, 7, 5));
            assertFalse(sut.evaluateCondition(Operator.NOT_EQUAL, 5, 5));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(Operator.LOWER_THAN, 7, 11));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, 7, 5));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, 7, 7));
        }
    }

    @Nested
    class LongTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(Operator.EQUAL, 5L, 5L));
            assertFalse(sut.evaluateCondition(Operator.EQUAL, 7L, 5L));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(Operator.NOT_EQUAL, 7L, 5L));
            assertFalse(sut.evaluateCondition(Operator.NOT_EQUAL, 5L, 5L));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(Operator.LOWER_THAN, 7L, 11L));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, 7L, 5L));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, 7L, 7L));
        }
    }

    @Nested
    class StringTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(Operator.EQUAL, "Hello", "Hello"));
            assertFalse(sut.evaluateCondition(Operator.EQUAL, "Bye", "Hello"));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(Operator.NOT_EQUAL, "Bye", "Hello"));
            assertFalse(sut.evaluateCondition(Operator.NOT_EQUAL, "Hello", "Hello"));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(Operator.LOWER_THAN, "bye", "hello"));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, "hello", "bye"));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, "hello", "hello"));
        }
    }

    @Nested
    class BooleanTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(Operator.EQUAL, true, true));
            assertFalse(sut.evaluateCondition(Operator.EQUAL, false, true));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(Operator.NOT_EQUAL, false, true));
            assertFalse(sut.evaluateCondition(Operator.NOT_EQUAL, true, true));
        }

        @Test
        void handleLowerThan() {
            assertThatThrownBy(() -> sut.evaluateCondition(Operator.LOWER_THAN, true, true))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'lower than' operator cannot be applied to booleans");
        }
    }

    @Nested
    class FloatTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(Operator.EQUAL, 1.5F, 1.5F));
            assertFalse(sut.evaluateCondition(Operator.EQUAL, -2.1F, 1.5F));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(Operator.NOT_EQUAL, -2.1F, 1.5F));
            assertFalse(sut.evaluateCondition(Operator.NOT_EQUAL, 1.5F, 1.5F));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(Operator.LOWER_THAN, 7.1F, 11.3F));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, 7.1F, 5.3F));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, 7.1F, 7.1F));
        }
    }

    @Nested
    class DoubleTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(Operator.EQUAL, 1.5, 1.5));
            assertFalse(sut.evaluateCondition(Operator.EQUAL, -2.1, 1.5));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(Operator.NOT_EQUAL, -2.1, 1.5));
            assertFalse(sut.evaluateCondition(Operator.NOT_EQUAL, 1.5, 1.5));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(Operator.LOWER_THAN, 7.1, 11.3));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, 7.1, 5.3));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, 7.1, 7.1));
        }
    }

    @Nested
    class CharacterTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(Operator.EQUAL, 'a', 'a'));
            assertFalse(sut.evaluateCondition(Operator.EQUAL, 'z', 'a'));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(Operator.NOT_EQUAL, 'z', 'a'));
            assertFalse(sut.evaluateCondition(Operator.NOT_EQUAL, 'a', 'a'));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(Operator.LOWER_THAN, 'a', 'b'));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, 'b', 'a'));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, 'a', 'a'));
        }
    }

    @Nested
    class EnumTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(Operator.EQUAL, "ENUM_VALUE0", TestEnum.ENUM_VALUE0));
            assertFalse(sut.evaluateCondition(Operator.EQUAL, "ENUM_VALUE1", TestEnum.ENUM_VALUE0));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(Operator.NOT_EQUAL, "ENUM_VALUE0", TestEnum.ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(Operator.NOT_EQUAL, "ENUM_VALUE1", TestEnum.ENUM_VALUE1));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(Operator.LOWER_THAN, "ENUM_VALUE0", TestEnum.ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, "ENUM_VALUE1", TestEnum.ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(Operator.LOWER_THAN, "ENUM_VALUE1", TestEnum.ENUM_VALUE0));
        }
    }

    @Test
    void handleObject() {
        assertThatThrownBy(() -> sut.evaluateCondition(Operator.EQUAL, new Object(), "anything"))
                .isInstanceOf(FilterException.class)
                .hasMessage("Filter applicable only to primitives, primitive wrappers and strings");
    }
}