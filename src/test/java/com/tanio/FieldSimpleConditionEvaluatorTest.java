package com.tanio;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.tanio.SimpleCondition.Operator.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldSimpleConditionEvaluatorTest {
    FieldConditionEvaluator sut = new FieldConditionEvaluator();

    @Nested
    class IntegerTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(EQUAL, 5, 5));
            assertFalse(sut.evaluateCondition(EQUAL, 7, 5));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, 7, 5));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, 5, 5));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(LESS_THAN, 7, 11));
            assertFalse(sut.evaluateCondition(LESS_THAN, 7, 5));
            assertFalse(sut.evaluateCondition(LESS_THAN, 7, 7));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.evaluateCondition(GREATER_THAN, 7, 5));
            assertFalse(sut.evaluateCondition(GREATER_THAN, 7, 11));
            assertFalse(sut.evaluateCondition(GREATER_THAN, 7, 7));
        }

        @Test
        void evaluateNull() {
            assertFalse(sut.evaluateCondition(EQUAL, null, 7));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, null, 7));
            assertFalse(sut.evaluateCondition(GREATER_THAN, null, 7));
            assertFalse(sut.evaluateCondition(LESS_THAN, null, 7));
        }
    }

    @Nested
    class LongTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(EQUAL, 5L, 5L));
            assertFalse(sut.evaluateCondition(EQUAL, 7L, 5L));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, 7L, 5L));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, 5L, 5L));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(LESS_THAN, 7L, 11L));
            assertFalse(sut.evaluateCondition(LESS_THAN, 7L, 5L));
            assertFalse(sut.evaluateCondition(LESS_THAN, 7L, 7L));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.evaluateCondition(GREATER_THAN, 7L, 5L));
            assertFalse(sut.evaluateCondition(GREATER_THAN, 7L, 11L));
            assertFalse(sut.evaluateCondition(GREATER_THAN, 7L, 7L));
        }

        @Test
        void evaluateNull() {
            assertFalse(sut.evaluateCondition(EQUAL, null, 7L));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, null, 7L));
            assertFalse(sut.evaluateCondition(GREATER_THAN, null, 7L));
            assertFalse(sut.evaluateCondition(LESS_THAN, null, 7L));
        }
    }

    @Nested
    class StringTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(EQUAL, "Hello", "Hello"));
            assertFalse(sut.evaluateCondition(EQUAL, "Bye", "Hello"));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, "Bye", "Hello"));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, "Hello", "Hello"));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(LESS_THAN, "bye", "hello"));
            assertFalse(sut.evaluateCondition(LESS_THAN, "hello", "bye"));
            assertFalse(sut.evaluateCondition(LESS_THAN, "hello", "hello"));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.evaluateCondition(GREATER_THAN, "hello", "bye"));
            assertFalse(sut.evaluateCondition(GREATER_THAN, "bye", "hello"));
            assertFalse(sut.evaluateCondition(GREATER_THAN, "hello", "hello"));
        }

        @Test
        void evaluateNull() {
            assertFalse(sut.evaluateCondition(EQUAL, null, "anyString"));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, null, "anyString"));
            assertFalse(sut.evaluateCondition(GREATER_THAN, null, "anyString"));
            assertFalse(sut.evaluateCondition(LESS_THAN, null, "anyString"));
        }
    }

    @Nested
    class BooleanTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(EQUAL, true, true));
            assertFalse(sut.evaluateCondition(EQUAL, false, true));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, false, true));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, true, true));
        }

        @Test
        void handleLowerThan() {
            assertThatThrownBy(() -> sut.evaluateCondition(LESS_THAN, true, true))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'lower than' operator cannot be applied to booleans");
        }

        @Test
        void handleGreaterThan() {
            assertThatThrownBy(() -> sut.evaluateCondition(GREATER_THAN, true, true))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'greater than' operator cannot be applied to booleans");
        }

        @Test
        void evaluateNull() {
            assertFalse(sut.evaluateCondition(EQUAL, null, true));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, null, false));
        }
    }

    @Nested
    class FloatTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(EQUAL, 1.5F, 1.5F));
            assertFalse(sut.evaluateCondition(EQUAL, -2.1F, 1.5F));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, -2.1F, 1.5F));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, 1.5F, 1.5F));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(LESS_THAN, 7.1F, 11.3F));
            assertFalse(sut.evaluateCondition(LESS_THAN, 7.1F, 5.3F));
            assertFalse(sut.evaluateCondition(LESS_THAN, 7.1F, 7.1F));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.evaluateCondition(GREATER_THAN, 11.3F, 7.1F));
            assertFalse(sut.evaluateCondition(GREATER_THAN, 5.3F, 7.1F));
            assertFalse(sut.evaluateCondition(GREATER_THAN, 7.1F, 7.1F));
        }

        @Test
        void evaluateNull() {
            assertFalse(sut.evaluateCondition(EQUAL, null, 7F));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, null, 7F));
            assertFalse(sut.evaluateCondition(GREATER_THAN, null, 7F));
            assertFalse(sut.evaluateCondition(LESS_THAN, null, 7F));
        }
    }

    @Nested
    class DoubleTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(EQUAL, 1.5, 1.5));
            assertFalse(sut.evaluateCondition(EQUAL, -2.1, 1.5));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, -2.1, 1.5));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, 1.5, 1.5));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(LESS_THAN, 7.1, 11.3));
            assertFalse(sut.evaluateCondition(LESS_THAN, 7.1, 5.3));
            assertFalse(sut.evaluateCondition(LESS_THAN, 7.1, 7.1));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.evaluateCondition(GREATER_THAN, 11.3, 7.1));
            assertFalse(sut.evaluateCondition(GREATER_THAN, 5.3, 7.1));
            assertFalse(sut.evaluateCondition(GREATER_THAN, 7.1, 7.1));
        }

        @Test
        void evaluateNull() {
            assertFalse(sut.evaluateCondition(EQUAL, null, 7.1));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, null, 7.1));
            assertFalse(sut.evaluateCondition(GREATER_THAN, null, 7.1));
            assertFalse(sut.evaluateCondition(LESS_THAN, null, 7.1));
        }
    }

    @Nested
    class CharacterTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(EQUAL, 'a', 'a'));
            assertFalse(sut.evaluateCondition(EQUAL, 'z', 'a'));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, 'z', 'a'));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, 'a', 'a'));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(LESS_THAN, 'a', 'b'));
            assertFalse(sut.evaluateCondition(LESS_THAN, 'b', 'a'));
            assertFalse(sut.evaluateCondition(LESS_THAN, 'a', 'a'));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.evaluateCondition(GREATER_THAN, 'b', 'a'));
            assertFalse(sut.evaluateCondition(GREATER_THAN, 'a', 'b'));
            assertFalse(sut.evaluateCondition(GREATER_THAN, 'a', 'a'));
        }

        @Test
        void evaluateNull() {
            assertFalse(sut.evaluateCondition(EQUAL, null, 'a'));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, null, 'a'));
            assertFalse(sut.evaluateCondition(GREATER_THAN, null, 'a'));
            assertFalse(sut.evaluateCondition(LESS_THAN, null, 'a'));
        }
    }

    @Nested
    class EnumTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(EQUAL, "ENUM_VALUE0", TestEnum.ENUM_VALUE0));
            assertFalse(sut.evaluateCondition(EQUAL, "ENUM_VALUE1", TestEnum.ENUM_VALUE0));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, "ENUM_VALUE0", TestEnum.ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, "ENUM_VALUE1", TestEnum.ENUM_VALUE1));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(LESS_THAN, "ENUM_VALUE0", TestEnum.ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(LESS_THAN, "ENUM_VALUE1", TestEnum.ENUM_VALUE0));
            assertFalse(sut.evaluateCondition(LESS_THAN, "ENUM_VALUE1", TestEnum.ENUM_VALUE1));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.evaluateCondition(GREATER_THAN, "ENUM_VALUE1", TestEnum.ENUM_VALUE0));
            assertFalse(sut.evaluateCondition(GREATER_THAN, "ENUM_VALUE0", TestEnum.ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(GREATER_THAN, "ENUM_VALUE1", TestEnum.ENUM_VALUE1));
        }

        @Test
        void evaluateNull() {
            assertFalse(sut.evaluateCondition(EQUAL, null, TestEnum.ENUM_VALUE0));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, null, TestEnum.ENUM_VALUE0));
            assertFalse(sut.evaluateCondition(GREATER_THAN, null, TestEnum.ENUM_VALUE0));
            assertFalse(sut.evaluateCondition(LESS_THAN, null, TestEnum.ENUM_VALUE0));
        }
    }

    @Test
    void handleObject() {
        assertThatThrownBy(() -> sut.evaluateCondition(EQUAL, "anything", new Object()))
                .isInstanceOf(FilterException.class)
                .hasMessage("Filter applicable only to primitives, primitive wrappers and strings");
    }
}