package com.tanio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.tanio.CompoundCondition.BooleanOperator.AND;
// TODO: merge the two mappers
// TODO: pass the object mapper and the default operator from the constructor
class Deserializer {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ComparisonOperatorMapper comparisonOperatorMapper = new ComparisonOperatorMapper();
    private final BooleanOperatorMapper booleanOperatorMapper = new BooleanOperatorMapper();
    private final ConditionValueDeserializer conditionValueDeserializer = new ConditionValueDeserializer();

    public static final CompoundCondition.BooleanOperator DEFAULT_BOOLEAN_OPERATOR = AND;

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
        return node.has("and") || node.has("or") || node.has("not");
    }

    private SimpleCondition toSimpleCondition(JsonNode jsonNode) {
        String fieldName = jsonNode.get("fieldName").asText();
        String operator = jsonNode.get("operator").asText();
        Object value = conditionValueDeserializer.deserializeConditionValue(jsonNode.get("value"));
        return new SimpleCondition(fieldName, comparisonOperatorMapper.map(operator), value);
    }
}
