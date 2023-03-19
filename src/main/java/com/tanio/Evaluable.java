package com.tanio;

import java.util.List;
import java.util.Set;

interface Evaluable {
    <T> Set<T> evaluate(List<T> target);
}
