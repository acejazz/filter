package com.tanio;

class Condition {
    String fieldName;
    Operator operator;
    Object value;

    enum Operator {
        EQUAL, NOT_EQUAL
    }
}