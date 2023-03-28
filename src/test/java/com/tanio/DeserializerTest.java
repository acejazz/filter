package com.tanio;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static com.tanio.CompoundCondition.*;
import static com.tanio.SimpleCondition.*;
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
                                "operator": "less_than",
                                "value": 5
                            }
                        ]
                }
                """;

        CompoundCondition result = (CompoundCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(
                and(
                        equal("anyFieldName0", true),
                        lessThan("anyFieldName1", 5)));
    }

    @Test
    void deserializeCompoundOrCondition() throws JsonProcessingException {
        String json = """
                {
                    "or":
                        [
                            {
                                "fieldName": "anyFieldName0",
                                "operator": "contains",
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
                or(
                        contains("anyFieldName0", "anyString"),
                        notEqual("anyFieldName1", 'z')));
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
                not(
                        equal("anyFieldName0", false),
                        notEqual("anyFieldName1", 11.3)));
    }

    @Test
    void deserializeNestedLevelCondition() throws JsonProcessingException {
        String json = """
                {
                   "and": [
                     {
                       "fieldName": "anyFieldName0",
                       "operator": "less_than",
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
                and(
                        lessThan("anyFieldName0", 12),
                        or(
                                notEqual("anyFieldName1", true),
                                greaterThan("anyFieldName2", 12.3))));
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
                and(
                        equal("anyFieldName0", "anyString"),
                        not(
                                notEqual("anyFieldName1", 'f'),
                                greaterThan("anyFieldName2", true))));
    }

    @Test
    void deserializeTwoNestedLevelsCondition() throws JsonProcessingException {
        String json = """
                {
                    "and": [
                      {
                        "fieldName": "anyFieldName0",
                        "operator": "contains",
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
                            "operator": "less_than",
                            "value": -3
                          }
                        ]
                      }
                    ]
                  }
                """;

        CompoundCondition result = (CompoundCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(
                and(
                        contains("anyFieldName0", "anyString0"),
                        or(
                                not(
                                        notEqual("anyFieldName1", 11),
                                        greaterThan("anyFieldName2", -3)),
                                equal("anyFieldName3", false),
                                lessThan("anyFieldName4", -3))));
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
        assertThat(result).isEqualTo(equal("anyFieldName", "anyValue"));
    }
}