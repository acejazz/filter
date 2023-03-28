package com.tanio;

import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConditionValueSerializerTest {
    ConditionValueSerializer sut = new ConditionValueSerializer();

    @Test
    void serializeInteger() {
        Object result = sut.serializeConditionValue(new IntNode(3));
        assertThat(result).isEqualTo(3);
    }

    @Test
    void serializeDouble() {
        Object result = sut.serializeConditionValue(new DoubleNode(11.3));
        assertThat(result).isEqualTo(11.3);
    }

    @Test
    void serializeString() {
        Object result = sut.serializeConditionValue(new TextNode("anyString"));
        assertThat(result).isEqualTo("anyString");
    }

    @Test
    void serializeChar() {
        Object result = sut.serializeConditionValue(new TextNode("a"));
        assertThat(result).isEqualTo('a');
    }

    @Test
    void serializeBoolean() {
        Object result = sut.serializeConditionValue(BooleanNode.TRUE);
        assertThat(result).isEqualTo(true);
    }
}