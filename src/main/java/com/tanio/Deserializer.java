package com.tanio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.tanio.CompoundCondition.BooleanOperator.AND;
import static com.tanio.StringToOperatorMapper.*;

public class Deserializer {
    private final StringToOperatorMapper stringToOperatorMapper = new StringToOperatorMapper();
    private final ConditionValueDeserializer conditionValueDeserializer = new ConditionValueDeserializer();
    private final ObjectMapper objectMapper;
    private final CompoundCondition.BooleanOperator booleanOperator;

    public Deserializer() {
        this(new Settings());
    }

    public Deserializer(Settings settings) {
        this.objectMapper = settings.objectMapper;
        this.booleanOperator = settings.defaultBooleanOperator;
    }

    public Condition deserialize(String json) throws JsonProcessingException {
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
        return new CompoundCondition(booleanOperator, conditions);
    }

    private CompoundCondition toCompoundCondition(JsonNode jsonNode) {
        String fieldName = jsonNode.fieldNames().next();
        JsonNode operatorJsonNode = jsonNode.get(fieldName);
        Set<Condition> conditions = toConditionSet(operatorJsonNode);
        return new CompoundCondition(stringToOperatorMapper.mapToBooleanOperator(fieldName), conditions);
    }

    private SimpleCondition toSimpleCondition(JsonNode jsonNode) {
        String fieldName = jsonNode.get(STRING_FIELD_NAME).asText();
        String operator = jsonNode.get(STRING_OPERATOR).asText();
        Object value = conditionValueDeserializer.deserializeConditionValue(jsonNode.get(STRING_VALUE));
        return new SimpleCondition(fieldName, stringToOperatorMapper.mapToComparisonOperator(operator), value);
    }

    private Set<Condition> toConditionSet(JsonNode node) {
        Iterator<JsonNode> children = node.elements();

        Set<Condition> conditions = new HashSet<>();
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
        return node.has(STRING_AND) || node.has(STRING_OR) || node.has(STRING_NOT);
    }

    public static class Settings {
        ObjectMapper objectMapper = new ObjectMapper();
        CompoundCondition.BooleanOperator defaultBooleanOperator = AND;
    }
}
