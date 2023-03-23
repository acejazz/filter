package com.tanio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DeserializerTest {
    Deserializer sut = new Deserializer();

    @Test
    void deserializeAndCompoundCondition() throws JsonProcessingException {
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
        assertThat(result.getOperator()).isEqualTo(CompoundCondition.BooleanOperator.AND);
        assertThat(result.getConditions()).containsExactlyInAnyOrder(
                new SimpleCondition("anyFieldName0", SimpleCondition.Operator.EQUAL, "anyValue0"),
                new SimpleCondition("anyFieldName1", SimpleCondition.Operator.NOT_EQUAL, "anyValue1"));
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
        assertThat(result.getOperator()).isEqualTo(CompoundCondition.BooleanOperator.AND);
        assertThat(result.getConditions()).containsExactlyInAnyOrder(
                new SimpleCondition("anyFieldName0", SimpleCondition.Operator.EQUAL, "anyValue0"),
                new CompoundCondition(
                        CompoundCondition.BooleanOperator.AND,
                        Set.of(
                                new SimpleCondition("anyFieldName1", SimpleCondition.Operator.NOT_EQUAL, "anyValue1"),
                                new SimpleCondition("anyFieldName2", SimpleCondition.Operator.GREATER_THAN, "anyValue2"))));
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
        assertThat(result.getOperator()).isEqualTo(CompoundCondition.BooleanOperator.AND);
        assertThat(result.getConditions()).containsExactlyInAnyOrder(
                new SimpleCondition("anyFieldName0", SimpleCondition.Operator.EQUAL, "anyValue0"),
                new CompoundCondition(
                        CompoundCondition.BooleanOperator.AND,
                        Set.of(
                                new SimpleCondition("anyFieldName1", SimpleCondition.Operator.NOT_EQUAL, "anyValue1"),
                                new SimpleCondition("anyFieldName2", SimpleCondition.Operator.GREATER_THAN, "anyValue2"))));
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
        assertThat(result.getFieldName()).isEqualTo("anyFieldName");
        assertThat(result.getOperator()).isEqualTo(SimpleCondition.Operator.EQUAL);
        assertThat(result.getValue()).isEqualTo("anyValue");
    }
}

class Deserializer {
    ObjectMapper objectMapper = new ObjectMapper();
    OperatorMapper operatorMapper = new OperatorMapper();

    Condition deserialize(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);

        if (jsonNode.isArray()) {
            Set<Condition> conditions = new HashSet<>();
            Iterator<JsonNode> elements = jsonNode.elements();
            while(elements.hasNext()) {
                JsonNode next = elements.next();
                if (next.has("and")) {
                    conditions.add(toCompoundCondition(next));
                }
                else {
                    conditions.add(toSimpleCondition(next));
                }
            }
            return new CompoundCondition(CompoundCondition.BooleanOperator.AND, conditions);
        }

        if (jsonNode.has("and")) {
            return toCompoundCondition(jsonNode);
        }

        return deserializeSimpleCondition(jsonNode);
    }

    private CompoundCondition toCompoundCondition(JsonNode jsonNode) {
        JsonNode and = jsonNode.get("and");
        Set<Condition> conditions = new HashSet<>();
        if (and.getNodeType().equals(JsonNodeType.ARRAY)) {
            Iterator<JsonNode> elements = and.elements();

            while (elements.hasNext()) {
                JsonNode next = elements.next();
                if (next.has("and")) {
                    conditions.add(toCompoundCondition(next));
                } else {
                    conditions.add(toSimpleCondition(next));
                }
            }
        }

        return new CompoundCondition(CompoundCondition.BooleanOperator.AND, conditions);
    }

    private SimpleCondition deserializeSimpleCondition(JsonNode jsonNode) {
        return toSimpleCondition(jsonNode);
    }

    private SimpleCondition toSimpleCondition(JsonNode jsonNode) {
        String fieldName = jsonNode.get("fieldName").asText();
        String operator = jsonNode.get("operator").asText();
        String value = jsonNode.get("value").asText();
        return new SimpleCondition(fieldName, operatorMapper.map(operator), value);
    }
}

class OperatorMapper {
    SimpleCondition.Operator map(String operator) {
        return switch (operator) {
            case "EQUAL" -> SimpleCondition.Operator.EQUAL;
            case "NOT_EQUAL" -> SimpleCondition.Operator.NOT_EQUAL;
            case "GREATER_THAN" -> SimpleCondition.Operator.GREATER_THAN;
            default -> throw new RuntimeException();
        };
    }
}