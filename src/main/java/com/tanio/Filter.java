package com.tanio;

import java.util.List;
import java.util.stream.Collectors;

class Filter {
    List<Entity> perform(List<Entity> target, Condition condition) {
        return target.stream().filter(it -> it.integerField == condition.value)
                .collect(Collectors.toList());
    }
}
