package com.tanio;

class Condition {
    Object value;
    Operator operator;
    String fieldName;

    enum Operator {
        EQUAL, NOT_EQUAL
    }
}
