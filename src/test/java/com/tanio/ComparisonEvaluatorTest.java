package com.tanio;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.tanio.SimpleCondition.ComparisonOperator.*;
import static com.tanio.TestEnum.ENUM_VALUE0;
import static com.tanio.TestEnum.ENUM_VALUE1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComparisonEvaluatorTest {
    ComparisonEvaluator sut = new ComparisonEvaluator();

    @Nested
    class IntegerTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.compare(EQUAL, 5, 5));
            assertFalse(sut.compare(EQUAL, 7, 5));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.compare(NOT_EQUAL, 7, 5));
            assertFalse(sut.compare(NOT_EQUAL, 5, 5));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.compare(LESS_THAN, 7, 11));
            assertFalse(sut.compare(LESS_THAN, 7, 5));
            assertFalse(sut.compare(LESS_THAN, 7, 7));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.compare(GREATER_THAN, 7, 5));
            assertFalse(sut.compare(GREATER_THAN, 7, 11));
            assertFalse(sut.compare(GREATER_THAN, 7, 7));
        }

        @Test
        void evaluateContains() {
            assertThatThrownBy(() -> sut.compare(CONTAINS, 7, 7))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNotContains() {
            assertThatThrownBy(() -> sut.compare(NOT_CONTAINS, 7, 7))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'not contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.compare(NOT_EQUAL, null, 7));
            assertFalse(sut.compare(EQUAL, null, 7));
            assertFalse(sut.compare(GREATER_THAN, null, 7));
            assertFalse(sut.compare(LESS_THAN, null, 7));
        }
    }

    @Nested
    class LongTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.compare(EQUAL, 5L, 5L));
            assertFalse(sut.compare(EQUAL, 7L, 5L));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.compare(NOT_EQUAL, 7L, 5L));
            assertFalse(sut.compare(NOT_EQUAL, 5L, 5L));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.compare(LESS_THAN, 7L, 11L));
            assertFalse(sut.compare(LESS_THAN, 7L, 5L));
            assertFalse(sut.compare(LESS_THAN, 7L, 7L));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.compare(GREATER_THAN, 7L, 5L));
            assertFalse(sut.compare(GREATER_THAN, 7L, 11L));
            assertFalse(sut.compare(GREATER_THAN, 7L, 7L));
        }

        @Test
        void evaluateContains() {
            assertThatThrownBy(() -> sut.compare(CONTAINS, 7L, 7L))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNotContains() {
            assertThatThrownBy(() -> sut.compare(NOT_CONTAINS, 7L, 7L))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'not contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.compare(NOT_EQUAL, null, 7L));
            assertFalse(sut.compare(EQUAL, null, 7L));
            assertFalse(sut.compare(GREATER_THAN, null, 7L));
            assertFalse(sut.compare(LESS_THAN, null, 7L));
        }
    }

    @Nested
    class StringTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.compare(EQUAL, "Hello", "Hello"));
            assertFalse(sut.compare(EQUAL, "Bye", "Hello"));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.compare(NOT_EQUAL, "Bye", "Hello"));
            assertFalse(sut.compare(NOT_EQUAL, "Hello", "Hello"));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.compare(LESS_THAN, "bye", "hello"));
            assertFalse(sut.compare(LESS_THAN, "hello", "bye"));
            assertFalse(sut.compare(LESS_THAN, "hello", "hello"));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.compare(GREATER_THAN, "hello", "bye"));
            assertFalse(sut.compare(GREATER_THAN, "bye", "hello"));
            assertFalse(sut.compare(GREATER_THAN, "hello", "hello"));
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.compare(NOT_EQUAL, null, "anyString"));
            assertFalse(sut.compare(EQUAL, null, "anyString"));
            assertFalse(sut.compare(GREATER_THAN, null, "anyString"));
            assertFalse(sut.compare(LESS_THAN, null, "anyString"));
        }

        @Test
        void evaluateContains() {
            assertTrue(sut.compare(CONTAINS, "Xanything", "anything"));
            assertTrue(sut.compare(CONTAINS, "anythingX", "anything"));
            assertTrue(sut.compare(CONTAINS, "XanythingX", "anything"));
            assertFalse(sut.compare(CONTAINS, "anything", "Xanything"));
            assertFalse(sut.compare(CONTAINS, "anything", "ANYTHING"));
            assertFalse(sut.compare(CONTAINS, "anything", "something"));
        }

        @Test
        void evaluateNotContains() {
            assertTrue(sut.compare(NOT_CONTAINS, "anything", "something"));
            assertTrue(sut.compare(NOT_CONTAINS, "anything", "Xanything"));
            assertTrue(sut.compare(NOT_CONTAINS, "anything", "ANYTHING"));
            assertFalse(sut.compare(NOT_CONTAINS, "Xanything", "anything"));
            assertFalse(sut.compare(NOT_CONTAINS, "anythingX", "anything"));
            assertFalse(sut.compare(NOT_CONTAINS, "XanythingX", "anything"));
        }
    }

    @Nested
    class BooleanTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.compare(EQUAL, true, true));
            assertFalse(sut.compare(EQUAL, false, true));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.compare(NOT_EQUAL, false, true));
            assertFalse(sut.compare(NOT_EQUAL, true, true));
        }

        @Test
        void handleLowerThan() {
            assertThatThrownBy(() -> sut.compare(LESS_THAN, true, true))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'lower than' operator cannot be applied to booleans");
        }

        @Test
        void handleGreaterThan() {
            assertThatThrownBy(() -> sut.compare(GREATER_THAN, true, true))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'greater than' operator cannot be applied to booleans");
        }

        @Test
        void evaluateContains() {
            assertThatThrownBy(() -> sut.compare(CONTAINS, true, true))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'contains' operator cannot be applied to booleans");
        }

        @Test
        void evaluateNotContains() {
            assertThatThrownBy(() -> sut.compare(NOT_CONTAINS, true, true))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'not contains' operator cannot be applied to booleans");
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.compare(NOT_EQUAL, null, false));
            assertFalse(sut.compare(EQUAL, null, true));
        }
    }

    @Nested
    class FloatTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.compare(EQUAL, 1.5F, 1.5F));
            assertFalse(sut.compare(EQUAL, -2.1F, 1.5F));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.compare(NOT_EQUAL, -2.1F, 1.5F));
            assertFalse(sut.compare(NOT_EQUAL, 1.5F, 1.5F));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.compare(LESS_THAN, 7.1F, 11.3F));
            assertFalse(sut.compare(LESS_THAN, 7.1F, 5.3F));
            assertFalse(sut.compare(LESS_THAN, 7.1F, 7.1F));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.compare(GREATER_THAN, 11.3F, 7.1F));
            assertFalse(sut.compare(GREATER_THAN, 5.3F, 7.1F));
            assertFalse(sut.compare(GREATER_THAN, 7.1F, 7.1F));
        }

        @Test
        void evaluateContains() {
            assertThatThrownBy(() -> sut.compare(CONTAINS, 11.3F, 7.1F))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNotContains() {
            assertThatThrownBy(() -> sut.compare(NOT_CONTAINS, 11.3F, 7.1F))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'not contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.compare(NOT_EQUAL, null, 7F));
            assertFalse(sut.compare(EQUAL, null, 7F));
            assertFalse(sut.compare(GREATER_THAN, null, 7F));
            assertFalse(sut.compare(LESS_THAN, null, 7F));
        }
    }

    @Nested
    class DoubleTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.compare(EQUAL, 1.5, 1.5));
            assertFalse(sut.compare(EQUAL, -2.1, 1.5));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.compare(NOT_EQUAL, -2.1, 1.5));
            assertFalse(sut.compare(NOT_EQUAL, 1.5, 1.5));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.compare(LESS_THAN, 7.1, 11.3));
            assertFalse(sut.compare(LESS_THAN, 7.1, 5.3));
            assertFalse(sut.compare(LESS_THAN, 7.1, 7.1));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.compare(GREATER_THAN, 11.3, 7.1));
            assertFalse(sut.compare(GREATER_THAN, 5.3, 7.1));
            assertFalse(sut.compare(GREATER_THAN, 7.1, 7.1));
        }

        @Test
        void evaluateContains() {
            assertThatThrownBy(() -> sut.compare(CONTAINS, 11.3, 7.1))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNotContains() {
            assertThatThrownBy(() -> sut.compare(NOT_CONTAINS, 11.3, 7.1))
                    .isInstanceOf(FilterException.class)
                    .hasMessage("'not contains' operator cannot be applied to numbers");
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.compare(NOT_EQUAL, null, 7.1));
            assertFalse(sut.compare(EQUAL, null, 7.1));
            assertFalse(sut.compare(GREATER_THAN, null, 7.1));
            assertFalse(sut.compare(LESS_THAN, null, 7.1));
        }
    }

    @Nested
    class CharacterTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.compare(EQUAL, 'a', 'a'));
            assertFalse(sut.compare(EQUAL, 'z', 'a'));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.compare(NOT_EQUAL, 'z', 'a'));
            assertFalse(sut.compare(NOT_EQUAL, 'a', 'a'));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.compare(LESS_THAN, 'a', 'b'));
            assertFalse(sut.compare(LESS_THAN, 'b', 'a'));
            assertFalse(sut.compare(LESS_THAN, 'a', 'a'));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.compare(GREATER_THAN, 'b', 'a'));
            assertFalse(sut.compare(GREATER_THAN, 'a', 'b'));
            assertFalse(sut.compare(GREATER_THAN, 'a', 'a'));
        }

        @Test
        void evaluateContains() {
            assertTrue(sut.compare(CONTAINS, 'x', 'x'));
            assertFalse(sut.compare(CONTAINS, 'x', 'a'));
            assertFalse(sut.compare(CONTAINS, 'x', 'X'));
        }

        @Test
        void evaluateNotContains() {
            assertTrue(sut.compare(NOT_CONTAINS, 'x', 'a'));
            assertTrue(sut.compare(NOT_CONTAINS, 'x', 'X'));
            assertFalse(sut.compare(NOT_CONTAINS, 'x', 'x'));
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.compare(NOT_EQUAL, null, 'a'));
            assertFalse(sut.compare(EQUAL, null, 'a'));
            assertFalse(sut.compare(GREATER_THAN, null, 'a'));
            assertFalse(sut.compare(LESS_THAN, null, 'a'));
        }
    }

    @Nested
    class EnumTests {
        @Test
        void evaluateEqual() {
            assertTrue(sut.compare(EQUAL, "ENUM_VALUE0", ENUM_VALUE0));
            assertFalse(sut.compare(EQUAL, "ENUM_VALUE1", ENUM_VALUE0));
        }

        @Test
        void evaluateNotEqual() {
            assertTrue(sut.compare(NOT_EQUAL, "ENUM_VALUE0", ENUM_VALUE1));
            assertFalse(sut.compare(NOT_EQUAL, "ENUM_VALUE1", ENUM_VALUE1));
        }

        @Test
        void evaluateLowerThan() {
            assertTrue(sut.compare(LESS_THAN, "ENUM_VALUE0", ENUM_VALUE1));
            assertFalse(sut.compare(LESS_THAN, "ENUM_VALUE1", ENUM_VALUE0));
            assertFalse(sut.compare(LESS_THAN, "ENUM_VALUE1", ENUM_VALUE1));
        }

        @Test
        void evaluateGreaterThan() {
            assertTrue(sut.compare(GREATER_THAN, "ENUM_VALUE1", ENUM_VALUE0));
            assertFalse(sut.compare(GREATER_THAN, "ENUM_VALUE0", ENUM_VALUE1));
            assertFalse(sut.compare(GREATER_THAN, "ENUM_VALUE1", ENUM_VALUE1));
        }

        @Test
        void evaluateContains() {
            assertTrue(sut.compare(CONTAINS, "ENUM_VALUE1", ENUM_VALUE1));
            assertTrue(sut.compare(CONTAINS, "xENUM_VALUE1", ENUM_VALUE1));
            assertTrue(sut.compare(CONTAINS, "ENUM_VALUE1x", ENUM_VALUE1));
            assertTrue(sut.compare(CONTAINS, "xENUM_VALUE1x", ENUM_VALUE1));
            assertFalse(sut.compare(CONTAINS, "ENUM_VALUE0", ENUM_VALUE1));
            assertFalse(sut.compare(CONTAINS, "enum_value1", ENUM_VALUE1));
            assertFalse(sut.compare(CONTAINS, "Xenum_value1", ENUM_VALUE1));
        }

        @Test
        void evaluateNotContains() {
            assertTrue(sut.compare(NOT_CONTAINS, "ENUM_VALUE0", ENUM_VALUE1));
            assertTrue(sut.compare(NOT_CONTAINS, "enum_value1", ENUM_VALUE1));
            assertTrue(sut.compare(NOT_CONTAINS, "Xenum_value1", ENUM_VALUE1));
            assertFalse(sut.compare(NOT_CONTAINS, "ENUM_VALUE1", ENUM_VALUE1));
            assertFalse(sut.compare(NOT_CONTAINS, "xENUM_VALUE1", ENUM_VALUE1));
            assertFalse(sut.compare(NOT_CONTAINS, "ENUM_VALUE1x", ENUM_VALUE1));
            assertFalse(sut.compare(NOT_CONTAINS, "xENUM_VALUE1x", ENUM_VALUE1));
        }

        @Test
        void evaluateNull() {
            assertTrue(sut.compare(NOT_EQUAL, null, ENUM_VALUE0));
            assertFalse(sut.compare(EQUAL, null, ENUM_VALUE0));
            assertFalse(sut.compare(GREATER_THAN, null, ENUM_VALUE0));
            assertFalse(sut.compare(LESS_THAN, null, ENUM_VALUE0));
        }
    }

    @Test
    void handleValidDifferentTypeForBoolean() {
        assertTrue(sut.compare(EQUAL, true, "true"));
        assertTrue(sut.compare(EQUAL, false, "false"));
        assertFalse(sut.compare(EQUAL, true, "false"));
        assertFalse(sut.compare(EQUAL, false, "true"));
    }

    @Test
    void handleInvalidDifferentTypeForBoolean() {
        assertThatThrownBy(() -> sut.compare(EQUAL, true, "anything"))
                .isInstanceOf(FilterException.class)
                .hasMessage("[anything] is not a valid boolean value");
    }

    @Test
    void handleValidDifferentTypeForNumbers() {
        assertTrue(sut.compare(EQUAL, 1.3, "1.3"));
        assertTrue(sut.compare(EQUAL, -5, "-5"));
        assertFalse(sut.compare(EQUAL, 1.3, "-1.5"));
        assertFalse(sut.compare(EQUAL, 7, "9"));
    }

    @Test
    void handleInvalidDifferentTypeForNumbers() {
        assertThatThrownBy(() -> sut.compare(EQUAL, 1.3, "anything"))
                .isInstanceOf(FilterException.class)
                .hasMessage("[anything] is not a valid numeric value");
    }

    @Test
    void handleObject() {
        assertThatThrownBy(() -> sut.compare(EQUAL, "anything", new Object()))
                .isInstanceOf(FilterException.class)
                .hasMessage("Filter applicable only to primitives, primitive wrappers and strings");
    }

    @Test
    void handleNull() {
        assertTrue(sut.compare(EQUAL, null, null));
        assertFalse(sut.compare(NOT_EQUAL, null, null));
        assertFalse(sut.compare(LESS_THAN, null, null));
        assertFalse(sut.compare(GREATER_THAN, null, null));
        assertFalse(sut.compare(CONTAINS, null, null));
        assertFalse(sut.compare(NOT_CONTAINS, null, null));
    }
}