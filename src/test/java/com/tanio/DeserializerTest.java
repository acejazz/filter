package com.tanio;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DeserializerTest {
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
        SimpleCondition result = deserializer.deserialize(json);
        assertThat(result.getFieldName()).isEqualTo("anyFieldName");
        assertThat(result.getOperator()).isEqualTo(SimpleCondition.Operator.EQUAL);
        assertThat(result.getValue()).isEqualTo("anyValue");
    }

    @Test
    void deserializeSimpleConditionArray() throws JsonProcessingException {
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
}

class Deserializer {
    SimpleCondition deserialize(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(SimpleCondition.class, new SimpleConditionDeserializer());
        objectMapper.registerModule(module);
        return objectMapper.readValue(json, SimpleCondition.class);
    }

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
}

class SimpleConditionDeserializer extends StdDeserializer<SimpleCondition> {

    OperatorMapper operatorMapper = new OperatorMapper();

    SimpleConditionDeserializer() {
        super((Class<?>) null);
    }

    @Override
    public SimpleCondition deserialize(JsonParser jsonParser,
                                       DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String fieldName = node.get("fieldName").asText();
        String operator = node.get("operator").asText();
        String value = node.get("value").asText();
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