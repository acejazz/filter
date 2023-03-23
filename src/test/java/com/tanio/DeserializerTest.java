package com.tanio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanio.CompoundCondition.BooleanOperator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
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
                                "fieldName":"anyFieldName0",
                                "operator":"EQUAL",
                                "value":"anyValue0"
                            },
                            {
                                "fieldName":"anyFieldName1",
                                "operator":"NOT_EQUAL",
                                "value":"anyValue1"
                            }
                        ]
                }
                """;

        CompoundCondition result = (CompoundCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(
                new CompoundCondition(
                        AND,
                        Set.of(
                                new SimpleCondition("anyFieldName0", EQUAL, "anyValue0"),
                                new SimpleCondition("anyFieldName1", NOT_EQUAL, "anyValue1"))));
    }

    @Test
    void deserializeCompoundOrCondition() throws JsonProcessingException {
        String json = """
                {
                    "or":
                        [
                            {
                                "fieldName":"anyFieldName0",
                                "operator":"EQUAL",
                                "value":"anyValue0"
                            },
                            {
                                "fieldName":"anyFieldName1",
                                "operator":"NOT_EQUAL",
                                "value":"anyValue1"
                            }
                        ]
                }
                """;

        CompoundCondition result = (CompoundCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(
                new CompoundCondition(
                        OR,
                        Set.of(
                                new SimpleCondition("anyFieldName0", EQUAL, "anyValue0"),
                                new SimpleCondition("anyFieldName1", NOT_EQUAL, "anyValue1"))));
    }

    @Test
    void deserializeCompoundNotCondition() throws JsonProcessingException {
        String json = """
                {
                    "not":
                        [
                            {
                                "fieldName":"anyFieldName0",
                                "operator":"EQUAL",
                                "value":"anyValue0"
                            },
                            {
                                "fieldName":"anyFieldName1",
                                "operator":"NOT_EQUAL",
                                "value":"anyValue1"
                            }
                        ]
                }
                """;

        CompoundCondition result = (CompoundCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(
                new CompoundCondition(
                        NOT,
                        Set.of(
                                new SimpleCondition("anyFieldName0", EQUAL, "anyValue0"),
                                new SimpleCondition("anyFieldName1", NOT_EQUAL, "anyValue1"))));
    }

    @Test
    void deserializeNestedLevelCondition() throws JsonProcessingException {
        String json = """
                {
                   "and": [
                     {
                       "fieldName": "anyFieldName0",
                       "operator": "EQUAL",
                       "value": "anyValue0"
                     },
                     {
                       "and": [
                         {
                           "fieldName": "anyFieldName1",
                           "operator": "NOT_EQUAL",
                           "value": "anyValue1"
                         },
                         {
                           "fieldName": "anyFieldName2",
                           "operator": "GREATER_THAN",
                           "value": "anyValue2"
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
                                new SimpleCondition("anyFieldName0", EQUAL, "anyValue0"),
                                new CompoundCondition(
                                        AND,
                                        Set.of(
                                                new SimpleCondition("anyFieldName1", NOT_EQUAL, "anyValue1"),
                                                new SimpleCondition("anyFieldName2", GREATER_THAN, "anyValue2"))))));
    }

    @Test
    void deserializeMixedNestedLevelCondition() throws JsonProcessingException {
        String json = """
                [
                    {
                        "fieldName": "anyFieldName0",
                        "operator": "EQUAL",
                        "value": "anyValue0"
                    },
                    {
                        "and": [
                        {
                            "fieldName": "anyFieldName1",
                            "operator": "NOT_EQUAL",
                            "value": "anyValue1"
                        },
                        {
                            "fieldName": "anyFieldName2",
                            "operator": "GREATER_THAN",
                            "value": "anyValue2"
                         }
                       ]
                    }
                ]
                """;

        CompoundCondition result = (CompoundCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(
                new CompoundCondition(
                        AND,
                        Set.of(new SimpleCondition("anyFieldName0", EQUAL, "anyValue0"),
                                new CompoundCondition(
                                        AND,
                                        Set.of(
                                                new SimpleCondition("anyFieldName1", NOT_EQUAL, "anyValue1"),
                                                new SimpleCondition("anyFieldName2", GREATER_THAN, "anyValue2"))))));
    }

    @Test
    void deserializeTwoNestedLevelsCondition() throws JsonProcessingException {
        String json = """
                {
                    "and": [
                      {
                        "fieldName": "anyFieldName0",
                        "operator": "EQUAL",
                        "value": "anyValue0"
                      },
                      {
                        "and": [
                          {
                            "and": [
                              {
                                "fieldName": "anyFieldName1",
                                "operator": "NOT_EQUAL",
                                "value": "anyValue1"
                              },
                              {
                                "fieldName": "anyFieldName2",
                                "operator": "GREATER_THAN",
                                "value": "anyValue2"
                              }
                            ]
                          },
                          {
                            "fieldName": "anyFieldName3",
                            "operator": "EQUAL",
                            "value": "anyValue3"
                          },
                          {
                            "fieldName": "anyFieldName4",
                            "operator": "NOT_EQUAL",
                            "value": "anyValue4"
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
                        new SimpleCondition("anyFieldName0", EQUAL, "anyValue0"),
                        new CompoundCondition(
                                AND,
                                Set.of(
                                        new CompoundCondition(
                                                AND,
                                                Set.of(
                                                        new SimpleCondition("anyFieldName1", NOT_EQUAL, "anyValue1"),
                                                        new SimpleCondition("anyFieldName2", GREATER_THAN, "anyValue2")
                                                )),
                                        new SimpleCondition("anyFieldName3", EQUAL, "anyValue3"),
                                        new SimpleCondition("anyFieldName4", NOT_EQUAL, "anyValue4"))))));
    }

    @Test
    void deserializeSimpleCondition() throws JsonProcessingException {
        String json = """
                {
                    "fieldName":"anyFieldName",
                    "operator":"EQUAL",
                    "value":"anyValue"
                }
                """;

        SimpleCondition result = (SimpleCondition) sut.deserialize(json);
        assertThat(result).isEqualTo(new SimpleCondition("anyFieldName", EQUAL, "anyValue"));
    }
}

class Deserializer {
    ObjectMapper objectMapper = new ObjectMapper();
    OperatorMapper operatorMapper = new OperatorMapper();
    BooleanOperatorMapper booleanOperatorMapper = new BooleanOperatorMapper();

    public static final BooleanOperator DEFAULT_BOOLEAN_OPERATOR = AND;

    Condition deserialize(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);

        if (jsonNode.isArray()) {
            return toCompoundConditionWithDefaultOperator(jsonNode);
        }

        if (isCompoundCondition(jsonNode)) {
            return toCompoundCondition(jsonNode);
        }

        return toSimpleCondition(jsonNode);
    }

    private CompoundCondition toCompoundConditionWithDefaultOperator(JsonNode jsonNode) {
        Set<Condition> conditions = toConditionSet(jsonNode);
        return new CompoundCondition(DEFAULT_BOOLEAN_OPERATOR, conditions);
    }

    private CompoundCondition toCompoundCondition(JsonNode jsonNode) {
        String fieldName = jsonNode.fieldNames().next();
        JsonNode operatorJsonNode = jsonNode.get(fieldName);

        Set<Condition> conditions = toConditionSet(operatorJsonNode);
        return new CompoundCondition(booleanOperatorMapper.mapToOperator(fieldName), conditions);
    }

    private Set<Condition> toConditionSet(JsonNode node) {
        Set<Condition> conditions = new HashSet<>();
        Iterator<JsonNode> children = node.elements();
        while (children.hasNext()) {
            JsonNode child = children.next();
            if (isCompoundCondition(child)) {
                conditions.add(toCompoundCondition(child));
            } else {
                conditions.add(toSimpleCondition(child));
            }
        }
        return conditions;
    }

    private boolean isCompoundCondition(JsonNode node) {
        return node.has("and") || node.has("or") || node.has("not");
    }

    private SimpleCondition toSimpleCondition(JsonNode jsonNode) {
        String fieldName = jsonNode.get("fieldName").asText();
        String operator = jsonNode.get("operator").asText();
        String value = jsonNode.get("value").asText();
        return new SimpleCondition(fieldName, operatorMapper.map(operator), value);
    }
}

class BooleanOperatorMapper {
    BooleanOperator mapToOperator(String fieldName) {
        return switch (fieldName) {
            case "and" -> AND;
            case "or" -> OR;
            case "not" -> NOT;
            default -> throw new RuntimeException();
        };
    }
}

class OperatorMapper {
    SimpleCondition.Operator map(String operator) {
        return switch (operator) {
            case "EQUAL" -> EQUAL;
            case "NOT_EQUAL" -> NOT_EQUAL;
            case "GREATER_THAN" -> GREATER_THAN;
            default -> throw new RuntimeException();
        };
    }
}