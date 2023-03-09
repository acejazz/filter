package com.tanio;

class Condition {
    String fieldName;
    Operator operator;
    Object value;

    enum Operator {
        EQUAL, LOWER_THAN, NOT_EQUAL
    }
}