package com.tanio;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetterMethodNameBuilderFromCamelCaseTest {
    GetterMethodNameBuilderFromCamelCase sut = new GetterMethodNameBuilderFromCamelCase();

    @Test
    void buildGetterName() {
        String result = sut.buildGetterName("anything");
        assertThat(result).isEqualTo("getAnything");
    }

    @Test
    void buildGetterCompoundName() {
        String result = sut.buildGetterName("anythingCompound");
        assertThat(result).isEqualTo("getAnythingCompound");
    }
}