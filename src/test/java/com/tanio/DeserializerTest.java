package com.tanio;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class DeserializerTest {
    @Test
    void deserialize() throws JsonProcessingException {
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
}

class Deserializer {
    SimpleCondition deserialize(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(SimpleCondition.class, new SimpleConditionDeserializer());
        objectMapper.registerModule(module);
        return objectMapper.readValue(json, SimpleCondition.class);
    }
}

class SimpleConditionDeserializer extends StdDeserializer<SimpleCondition> {

    SimpleConditionDeserializer() {
        super((Class<?>) null);
    }

    @Override
    public SimpleCondition deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String fieldName = node.get("fieldName").asText();
        String operator = node.get("operator").asText();
        String value = node.get("value").asText();
        return new SimpleCondition(fieldName, map(operator), value);
    }

    SimpleCondition.Operator map(String operator) {
        return switch (operator) {
            case "EQUAL" -> SimpleCondition.Operator.EQUAL;
            default -> throw new RuntimeException();
        };
    }
}