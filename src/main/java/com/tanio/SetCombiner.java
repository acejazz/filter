package com.tanio;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.List.copyOf;

class SetCombiner {
    <T> Set<T> or(List<Set<T>> resultLists) {
        return resultLists.stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    <T> Set<T> and(List<Set<T>> resultLists) {
        Iterator<Set<T>> iterator = resultLists.iterator();
        Set<T> result = new HashSet<>(iterator.next());
        while (iterator.hasNext()) {
            result.retainAll(iterator.next());
        }
        return result;
    }

    <T> Set<T> not(List<T> target, List<Set<T>> resultLists) {
        // !A and !B and !C = !(A or B or C)
        return negate(target, or(resultLists));
    }

    private <T> Set<T> negate(List<T> universe, Set<T> set) {
        Set<T> result = new HashSet<>(copyOf(universe));
        result.removeAll(set);
        return result;
    }
}
