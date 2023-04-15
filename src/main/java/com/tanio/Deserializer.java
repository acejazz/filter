package com.tanio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanio.CompoundCondition.BooleanOperator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.tanio.ArgumentChecks.checkNotNull;
import static com.tanio.CompoundCondition.BooleanOperator.AND;
import static com.tanio.StringToOperatorMapper.*;

public class Deserializer {
    private final StringToOperatorMapper stringToOperatorMapper;
    private final ConditionValueDeserializer conditionValueDeserializer;
    private final ObjectMapper objectMapper;
    private final BooleanOperator firstLevelMixedConditionsBooleanOperator;

    public Deserializer() {
        this(new ObjectMapper(), AND);
    }

    public Deserializer(ObjectMapper objectMapper,
                        BooleanOperator firstLevelMixedConditionsBooleanOperator) {
        checkNotNull(objectMapper, "objectMapper");
        checkNotNull(firstLevelMixedConditionsBooleanOperator, "firstLevelMixedConditionsBooleanOperator");

        this.objectMapper = objectMapper;
        this.firstLevelMixedConditionsBooleanOperator = firstLevelMixedConditionsBooleanOperator;
        this.stringToOperatorMapper = new StringToOperatorMapper();
        this.conditionValueDeserializer = new ConditionValueDeserializer();
    }

    public Condition deserialize(String json) throws JsonProcessingException {
        checkNotNull(json, "json");

        JsonNode jsonNode = objectMapper.readTree(json);

        if (areConditionsMixedOnFirstLevel(jsonNode)) {
            return toCompoundConditionWithDefaultOperator(jsonNode);
        }

        if (isCompoundCondition(jsonNode)) {
            return toCompoundCondition(jsonNode);
        }

        return toSimpleCondition(jsonNode);
    }

    private boolean areConditionsMixedOnFirstLevel(JsonNode jsonNode) {
        return jsonNode.isArray();
    }

    private CompoundCondition toCompoundConditionWithDefaultOperator(JsonNode jsonNode) {
        Set<Condition> conditions = toConditionSet(jsonNode);
        return new CompoundCondition(firstLevelMixedConditionsBooleanOperator, conditions);
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
}