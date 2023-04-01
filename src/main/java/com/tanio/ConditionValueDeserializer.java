package com.tanio;

import com.fasterxml.jackson.databind.JsonNode;

class ConditionValueDeserializer {
    Object deserializeConditionValue(JsonNode jsonNode) {
        if (jsonNode.isInt()) {
            return jsonNode.asInt();
        } else if (jsonNode.isDouble()) {
            return jsonNode.asDouble();
        } else if (jsonNode.isBoolean()) {
            return jsonNode.asBoolean();
        } else {
            String value = jsonNode.asText();
            if (value.length() == 1) {
                return value.charAt(0);
            }
            return value;
        }
    }
}
