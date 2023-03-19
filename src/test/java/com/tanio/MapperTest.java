package com.tanio;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class MapperTest {
    Mapper mapper = new Mapper();

    @Test
    void decorateSimpleConditionDto() {
        SimpleCondition simpleConditionDto = new SimpleCondition("anyFieldName", SimpleCondition.Operator.EQUAL, "anyValue");
        EvaluableSimpleCondition result = (EvaluableSimpleCondition) mapper.map(simpleConditionDto);
        assertThat(result.getFieldName()).isEqualTo("anyFieldName");
        assertThat(result.getOperator()).isEqualTo(EvaluableSimpleCondition.Operator.EQUAL);
        assertThat(result.getValue()).isEqualTo("anyValue");
    }

    @Test
    void decorateCompoundConditionDto() {
        SimpleCondition simpleConditionDto0 = new SimpleCondition("anyFieldName0", SimpleCondition.Operator.EQUAL, "anyValue0");
        SimpleCondition simpleConditionDto1 = new SimpleCondition("anyFieldName1", SimpleCondition.Operator.NOT_EQUAL, "anyValue1");
        CompoundCondition condition = new CompoundCondition(
                CompoundCondition.BooleanOperator.AND,
                Arrays.asList(simpleConditionDto0, simpleConditionDto1)
        );
        EvaluableCompoundCondition result = (EvaluableCompoundCondition) mapper.map(condition);
        assertThat(result.getOperator()).isEqualTo(EvaluableCompoundCondition.BooleanOperator.AND);
        Map<String, EvaluableSimpleCondition> map = result.getConditions().stream()
                .collect(Collectors.toMap(
                        it -> ((EvaluableSimpleCondition) it).getFieldName(),
                        it -> (EvaluableSimpleCondition) it
                ));

        assertThat(map.get("anyFieldName0").getOperator()).isEqualTo(EvaluableSimpleCondition.Operator.EQUAL);
        assertThat(map.get("anyFieldName0").getValue()).isEqualTo("anyValue0");
        assertThat(map.get("anyFieldName1").getOperator()).isEqualTo(EvaluableSimpleCondition.Operator.NOT_EQUAL);
        assertThat(map.get("anyFieldName1").getValue()).isEqualTo("anyValue1");
    }
}