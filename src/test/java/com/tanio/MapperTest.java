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
        SimpleConditionDto simpleConditionDto = new SimpleConditionDto("anyFieldName", SimpleConditionDto.Operator.EQUAL, "anyValue");
        SimpleCondition result = (SimpleCondition) mapper.map(simpleConditionDto);
        assertThat(result.getFieldName()).isEqualTo("anyFieldName");
        assertThat(result.getOperator()).isEqualTo(SimpleCondition.Operator.EQUAL);
        assertThat(result.getValue()).isEqualTo("anyValue");
    }

    @Test
    void decorateCompoundConditionDto() {
        SimpleConditionDto simpleConditionDto0 = new SimpleConditionDto("anyFieldName0", SimpleConditionDto.Operator.EQUAL, "anyValue0");
        SimpleConditionDto simpleConditionDto1 = new SimpleConditionDto("anyFieldName1", SimpleConditionDto.Operator.NOT_EQUAL, "anyValue1");
        CompoundConditionDto condition = new CompoundConditionDto(
                CompoundConditionDto.BooleanOperator.AND,
                Arrays.asList(simpleConditionDto0, simpleConditionDto1)
        );
        CompoundCondition result = (CompoundCondition) mapper.map(condition);
        assertThat(result.getOperator()).isEqualTo(CompoundCondition.BooleanOperator.AND);
        Map<String, SimpleCondition> map = result.getConditions().stream()
                .collect(Collectors.toMap(
                        it -> ((SimpleCondition) it).getFieldName(),
                        it -> (SimpleCondition) it
                ));

        assertThat(map.get("anyFieldName0").getOperator()).isEqualTo(SimpleCondition.Operator.EQUAL);
        assertThat(map.get("anyFieldName0").getValue()).isEqualTo("anyValue0");
        assertThat(map.get("anyFieldName1").getOperator()).isEqualTo(SimpleCondition.Operator.NOT_EQUAL);
        assertThat(map.get("anyFieldName1").getValue()).isEqualTo("anyValue1");
    }
}