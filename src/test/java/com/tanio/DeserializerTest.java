package com.tanio;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.tanio.CompoundCondition.BooleanOperator.*;
import static com.tanio.SimpleCondition.Operator.*;
import static org.assertj.core.api.Assertions.assertThat;

class DeserializerTest {
    Deserializer sut = new Deserializer();

    @Test
    void deserializeCompoundAndCondition() throws JsonProcessingException {
        String json = """
                {
                    "and":
                        [
                            {
                                "fieldName": "anyFieldName0",
                                "operator": "equal",
                                "value": true
                            },
                            {
                                "fieldName": "anyFieldName1",
                                "operator": "not_equal",
                                "value": 5
                            }
                        ]
                }
                """;

        CompoundCondition result = (CompoundCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(
                new CompoundCondition(
                        AND,
                        Set.of(
                                new SimpleCondition("anyFieldName0", EQUAL, true),
                                new SimpleCondition("anyFieldName1", NOT_EQUAL, 5))));
    }

    @Test
    void deserializeCompoundOrCondition() throws JsonProcessingException {
        String json = """
                {
                    "or":
                        [
                            {
                                "fieldName": "anyFieldName0",
                                "operator": "equal",
                                "value": "anyString"
                            },
                            {
                                "fieldName": "anyFieldName1",
                                "operator": "not_equal",
                                "value": "z"
                            }
                        ]
                }
                """;

        CompoundCondition result = (CompoundCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(
                new CompoundCondition(
                        OR,
                        Set.of(
                                new SimpleCondition("anyFieldName0", EQUAL, "anyString"),
                                new SimpleCondition("anyFieldName1", NOT_EQUAL, 'z'))));
    }

    @Test
    void deserializeCompoundNotCondition() throws JsonProcessingException {
        String json = """
                {
                    "not":
                        [
                            {
                                "fieldName": "anyFieldName0",
                                "operator": "equal",
                                "value": false
                            },
                            {
                                "fieldName": "anyFieldName1",
                                "operator": "not_equal",
                                "value": 11.3
                            }
                        ]
                }
                """;

        CompoundCondition result = (CompoundCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(
                new CompoundCondition(
                        NOT,
                        Set.of(
                                new SimpleCondition("anyFieldName0", EQUAL, false),
                                new SimpleCondition("anyFieldName1", NOT_EQUAL, 11.3))));
    }

    @Test
    void deserializeNestedLevelCondition() throws JsonProcessingException {
        String json = """
                {
                   "and": [
                     {
                       "fieldName": "anyFieldName0",
                       "operator": "equal",
                       "value": 12
                     },
                     {
                       "or": [
                         {
                           "fieldName": "anyFieldName1",
                           "operator": "not_equal",
                           "value": true
                         },
                         {
                           "fieldName": "anyFieldName2",
                           "operator": "greater_than",
                           "value": 12.3
                         }
                       ]
                     }
                   ]
                 }
                """;

        CompoundCondition result = (CompoundCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(
                new CompoundCondition(
                        AND,
                        Set.of(
                                new SimpleCondition("anyFieldName0", EQUAL, 12),
                                new CompoundCondition(
                                        OR,
                                        Set.of(
                                                new SimpleCondition("anyFieldName1", NOT_EQUAL, true),
                                                new SimpleCondition("anyFieldName2", GREATER_THAN, 12.3))))));
    }

    @Test
    void deserializeMixedNestedLevelCondition() throws JsonProcessingException {
        String json = """
                [
                    {
                        "fieldName": "anyFieldName0",
                        "operator": "equal",
                        "value": "anyString"
                    },
                    {
                        "not": [
                        {
                            "fieldName": "anyFieldName1",
                            "operator": "not_equal",
                            "value": "f"
                        },
                        {
                            "fieldName": "anyFieldName2",
                            "operator": "greater_than",
                            "value": true
                         }
                       ]
                    }
                ]
                """;

        CompoundCondition result = (CompoundCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(
                new CompoundCondition(
                        AND,
                        Set.of(new SimpleCondition("anyFieldName0", EQUAL, "anyString"),
                                new CompoundCondition(
                                        NOT,
                                        Set.of(
                                                new SimpleCondition("anyFieldName1", NOT_EQUAL, 'f'),
                                                new SimpleCondition("anyFieldName2", GREATER_THAN, true))))));
    }

    @Test
    void deserializeTwoNestedLevelsCondition() throws JsonProcessingException {
        String json = """
                {
                    "and": [
                      {
                        "fieldName": "anyFieldName0",
                        "operator": "equal",
                        "value": "anyString0"
                      },
                      {
                        "or": [
                          {
                            "not": [
                              {
                                "fieldName": "anyFieldName1",
                                "operator": "not_equal",
                                "value": 11
                              },
                              {
                                "fieldName": "anyFieldName2",
                                "operator": "greater_than",
                                "value": -3
                              }
                            ]
                          },
                          {
                            "fieldName": "anyFieldName3",
                            "operator": "equal",
                            "value": false
                          },
                          {
                            "fieldName": "anyFieldName4",
                            "operator": "not_equal",
                            "value": true
                          }
                        ]
                      }
                    ]
                  }
                """;

        CompoundCondition result = (CompoundCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(new CompoundCondition(
                AND,
                Set.of(
                        new SimpleCondition("anyFieldName0", EQUAL, "anyString0"),
                        new CompoundCondition(
                                OR,
                                Set.of(
                                        new CompoundCondition(
                                                NOT,
                                                Set.of(
                                                        new SimpleCondition("anyFieldName1", NOT_EQUAL, 11),
                                                        new SimpleCondition("anyFieldName2", GREATER_THAN, -3)
                                                )),
                                        new SimpleCondition("anyFieldName3", EQUAL, false),
                                        new SimpleCondition("anyFieldName4", NOT_EQUAL, true))))));
    }

    @Test
    void deserializeSimpleCondition() throws JsonProcessingException {
        String json = """
                {
                    "fieldName": "anyFieldName",
                    "operator": "equal",
                    "value": "anyValue"
                }
                """;

        SimpleCondition result = (SimpleCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(new SimpleCondition("anyFieldName", EQUAL, "anyValue"));
    }
}