package com.tanio;

import java.util.List;

interface Evaluable {
    <T> List<T> evaluate(List<T> target);
}
