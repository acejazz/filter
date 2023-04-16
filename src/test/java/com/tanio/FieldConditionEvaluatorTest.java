package com.tanio;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.tanio.SimpleCondition.ComparisonOperator.*;
import static com.tanio.TestEnum.ENUM_VALUE0;
import static com.tanio.TestEnum.ENUM_VALUE1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldConditionEvaluatorTest {
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
        void evaluateContains() {
            assertThatThrownBy(() -> sut.evaluateCondition(CONTAINS, 7, 7))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNotContains() {
            assertThatThrownBy(() -> sut.evaluateCondition(NOT_CONTAINS, 7, 7))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'not contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, null, 7));
            assertFalse(sut.evaluateCondition(EQUAL, null, 7));
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
        void evaluateContains() {
            assertThatThrownBy(() -> sut.evaluateCondition(CONTAINS, 7L, 7L))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNotContains() {
            assertThatThrownBy(() -> sut.evaluateCondition(NOT_CONTAINS, 7L, 7L))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'not contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, null, 7L));
            assertFalse(sut.evaluateCondition(EQUAL, null, 7L));
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
            assertTrue(sut.evaluateCondition(NOT_EQUAL, null, "anyString"));
            assertFalse(sut.evaluateCondition(EQUAL, null, "anyString"));
            assertFalse(sut.evaluateCondition(GREATER_THAN, null, "anyString"));
            assertFalse(sut.evaluateCondition(LESS_THAN, null, "anyString"));
        }

        @Test
        void evaluateContains() {
            assertTrue(sut.evaluateCondition(CONTAINS, "Xanything", "anything"));
            assertTrue(sut.evaluateCondition(CONTAINS, "anythingX", "anything"));
            assertTrue(sut.evaluateCondition(CONTAINS, "XanythingX", "anything"));
            assertFalse(sut.evaluateCondition(CONTAINS, "anything", "Xanything"));
            assertFalse(sut.evaluateCondition(CONTAINS, "anything", "ANYTHING"));
            assertFalse(sut.evaluateCondition(CONTAINS, "anything", "something"));
        }

        @Test
        void evaluateNotContains() {
            assertTrue(sut.evaluateCondition(NOT_CONTAINS, "anything", "something"));
            assertTrue(sut.evaluateCondition(NOT_CONTAINS, "anything", "Xanything"));
            assertTrue(sut.evaluateCondition(NOT_CONTAINS, "anything", "ANYTHING"));
            assertFalse(sut.evaluateCondition(NOT_CONTAINS, "Xanything", "anything"));
            assertFalse(sut.evaluateCondition(NOT_CONTAINS, "anythingX", "anything"));
            assertFalse(sut.evaluateCondition(NOT_CONTAINS, "XanythingX", "anything"));
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
        void evaluateContains() {
            assertThatThrownBy(() -> sut.evaluateCondition(CONTAINS, true, true))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'contains' operator cannot be applied to booleans");
        }

        @Test
        void evaluateNotContains() {
            assertThatThrownBy(() -> sut.evaluateCondition(NOT_CONTAINS, true, true))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'not contains' operator cannot be applied to booleans");
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, null, false));
            assertFalse(sut.evaluateCondition(EQUAL, null, true));
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
        void evaluateContains() {
            assertThatThrownBy(() -> sut.evaluateCondition(CONTAINS, 11.3F, 7.1F))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNotContains() {
            assertThatThrownBy(() -> sut.evaluateCondition(NOT_CONTAINS, 11.3F, 7.1F))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'not contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, null, 7F));
            assertFalse(sut.evaluateCondition(EQUAL, null, 7F));
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
        void evaluateContains() {
            assertThatThrownBy(() -> sut.evaluateCondition(CONTAINS, 11.3, 7.1))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNotContains() {
            assertThatThrownBy(() -> sut.evaluateCondition(NOT_CONTAINS, 11.3, 7.1))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'not contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, null, 7.1));
            assertFalse(sut.evaluateCondition(EQUAL, null, 7.1));
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
        void evaluateContains() {
            assertTrue(sut.evaluateCondition(CONTAINS, 'x', 'x'));
            assertFalse(sut.evaluateCondition(CONTAINS, 'x', 'a'));
            assertFalse(sut.evaluateCondition(CONTAINS, 'x', 'X'));
        }

        @Test
        void evaluateNotContains() {
            assertTrue(sut.evaluateCondition(NOT_CONTAINS, 'x', 'a'));
            assertTrue(sut.evaluateCondition(NOT_CONTAINS, 'x', 'X'));
            assertFalse(sut.evaluateCondition(NOT_CONTAINS, 'x', 'x'));
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, null, 'a'));
            assertFalse(sut.evaluateCondition(EQUAL, null, 'a'));
            assertFalse(sut.evaluateCondition(GREATER_THAN, null, 'a'));
            assertFalse(sut.evaluateCondition(LESS_THAN, null, 'a'));
        }
    }

    @Nested
    class EnumTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.evaluateCondition(EQUAL, "ENUM_VALUE0", ENUM_VALUE0));
            assertFalse(sut.evaluateCondition(EQUAL, "ENUM_VALUE1", ENUM_VALUE0));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, "ENUM_VALUE0", ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(NOT_EQUAL, "ENUM_VALUE1", ENUM_VALUE1));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.evaluateCondition(LESS_THAN, "ENUM_VALUE0", ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(LESS_THAN, "ENUM_VALUE1", ENUM_VALUE0));
            assertFalse(sut.evaluateCondition(LESS_THAN, "ENUM_VALUE1", ENUM_VALUE1));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.evaluateCondition(GREATER_THAN, "ENUM_VALUE1", ENUM_VALUE0));
            assertFalse(sut.evaluateCondition(GREATER_THAN, "ENUM_VALUE0", ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(GREATER_THAN, "ENUM_VALUE1", ENUM_VALUE1));
        }

        @Test
        void evaluateContains() {
            assertTrue(sut.evaluateCondition(CONTAINS, "ENUM_VALUE1", ENUM_VALUE1));
            assertTrue(sut.evaluateCondition(CONTAINS, "xENUM_VALUE1", ENUM_VALUE1));
            assertTrue(sut.evaluateCondition(CONTAINS, "ENUM_VALUE1x", ENUM_VALUE1));
            assertTrue(sut.evaluateCondition(CONTAINS, "xENUM_VALUE1x", ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(CONTAINS, "ENUM_VALUE0", ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(CONTAINS, "enum_value1", ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(CONTAINS, "Xenum_value1", ENUM_VALUE1));
        }

        @Test
        void evaluateNotContains() {
            assertTrue(sut.evaluateCondition(NOT_CONTAINS, "ENUM_VALUE0", ENUM_VALUE1));
            assertTrue(sut.evaluateCondition(NOT_CONTAINS, "enum_value1", ENUM_VALUE1));
            assertTrue(sut.evaluateCondition(NOT_CONTAINS, "Xenum_value1", ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(NOT_CONTAINS, "ENUM_VALUE1", ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(NOT_CONTAINS, "xENUM_VALUE1", ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(NOT_CONTAINS, "ENUM_VALUE1x", ENUM_VALUE1));
            assertFalse(sut.evaluateCondition(NOT_CONTAINS, "xENUM_VALUE1x", ENUM_VALUE1));
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.evaluateCondition(NOT_EQUAL, null, ENUM_VALUE0));
            assertFalse(sut.evaluateCondition(EQUAL, null, ENUM_VALUE0));
            assertFalse(sut.evaluateCondition(GREATER_THAN, null, ENUM_VALUE0));
            assertFalse(sut.evaluateCondition(LESS_THAN, null, ENUM_VALUE0));
        }
    }

    @Test
    void handleValidDifferentTypeForBoolean() {
        assertTrue(sut.evaluateCondition(EQUAL, true, "true"));
        assertTrue(sut.evaluateCondition(EQUAL, false, "false"));
        assertFalse(sut.evaluateCondition(EQUAL, true, "false"));
        assertFalse(sut.evaluateCondition(EQUAL, false, "true"));
    }

    @Test
    void handleInvalidDifferentTypeForBoolean() {
        assertThatThrownBy(() -> sut.evaluateCondition(EQUAL, true, "anything"))
                .isInstanceOf(FilterException.class)
                .hasMessage("[anything] is not a valid boolean value");
    }

    @Test
    void handleValidDifferentTypeForNumbers() {
        assertTrue(sut.evaluateCondition(EQUAL, 1.3, "1.3"));
        assertTrue(sut.evaluateCondition(EQUAL, -5, "-5"));
        assertFalse(sut.evaluateCondition(EQUAL, 1.3, "-1.5"));
        assertFalse(sut.evaluateCondition(EQUAL, 7, "9"));
    }

    @Test
    void handleInvalidDifferentTypeForNumbers() {
        assertThatThrownBy(() -> sut.evaluateCondition(EQUAL, 1.3, "anything"))
                .isInstanceOf(FilterException.class)
                .hasMessage("[anything] is not a valid numeric value");
    }

    @Test
    void handleObject() {
        assertThatThrownBy(() -> sut.evaluateCondition(EQUAL, "anything", new Object()))
                .isInstanceOf(FilterException.class)
                .hasMessage("Filter applicable only to primitives, primitive wrappers and strings");
    }

    @Test
    void handleNull() {
        assertTrue(sut.evaluateCondition(EQUAL, null, null));
        assertFalse(sut.evaluateCondition(NOT_EQUAL, null, null));
        assertFalse(sut.evaluateCondition(LESS_THAN, null, null));
        assertFalse(sut.evaluateCondition(GREATER_THAN, null, null));
        assertFalse(sut.evaluateCondition(CONTAINS, null, null));
        assertFalse(sut.evaluateCondition(NOT_CONTAINS, null, null));
    }
}