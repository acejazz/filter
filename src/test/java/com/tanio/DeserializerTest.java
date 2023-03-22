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

        Deserializer deserializer = new Deserializer();
        CompoundCondition result = deserializer.deserializeCompoundCondition(json);
        assertThat(result.getOperator()).isEqualTo(CompoundCondition.BooleanOperator.AND);
        assertThat(result.getConditions()).containsExactlyInAnyOrder(
                new SimpleCondition("anyFieldName0", SimpleCondition.Operator.EQUAL, "anyValue0"),
                new SimpleCondition("anyFieldName1", SimpleCondition.Operator.NOT_EQUAL, "anyValue1"));
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

        Deserializer deserializer = new Deserializer();
        SimpleCondition result = deserializer.deserializeSimpleCondition(json);
        assertThat(result.getFieldName()).isEqualTo("anyFieldName");
        assertThat(result.getOperator()).isEqualTo(SimpleCondition.Operator.EQUAL);
        assertThat(result.getValue()).isEqualTo("anyValue");
    }
}

class Deserializer {
    CompoundCondition deserializeCompoundCondition(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        OperatorMapper operatorMapper = new OperatorMapper();

        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode and = jsonNode.get("and");
        Set<Condition> simpleConditions = new HashSet<>();
        if (and.getNodeType().equals(JsonNodeType.ARRAY)) {
            Iterator<JsonNode> elements = and.elements();
            while (elements.hasNext()) {
                JsonNode next = elements.next();
                String fieldName = next.get("fieldName").asText();
                String operator = next.get("operator").asText();
                String value = next.get("value").asText();
                SimpleCondition simpleCondition =
                        new SimpleCondition(fieldName, operatorMapper.map(operator), value);
                simpleConditions.add(simpleCondition);
            }
        }

        return new CompoundCondition(CompoundCondition.BooleanOperator.AND, simpleConditions);
    }

    public SimpleCondition deserializeSimpleCondition(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        OperatorMapper operatorMapper = new OperatorMapper();

        JsonNode jsonNode = objectMapper.readTree(json);
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
            default -> throw new RuntimeException();
        };
    }
}