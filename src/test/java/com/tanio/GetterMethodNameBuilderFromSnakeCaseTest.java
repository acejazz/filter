package com.tanio;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetterMethodNameBuilderFromSnakeCaseTest {
    @Test
    void buildGetterName() {
        GetterMethodNameBuilderFromSnakeCase sut = new GetterMethodNameBuilderFromSnakeCase();
        String result = sut.buildGetterName("anything");
        assertThat(result).isEqualTo("getAnything");
    }

    @Test
    void buildGetterCompoundName() {
        GetterMethodNameBuilderFromSnakeCase sut = new GetterMethodNameBuilderFromSnakeCase();
        String result = sut.buildGetterName("anything_compound");
        assertThat(result).isEqualTo("getAnythingCompound");
    }
}