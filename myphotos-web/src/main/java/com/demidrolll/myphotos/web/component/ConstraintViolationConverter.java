package com.demidrolll.myphotos.web.component;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class ConstraintViolationConverter {

    public <T> Map<String, List<String>> convert(Set<ConstraintViolation<T>> violations) {
        Map<String, List<String>> list = new HashMap<>();
        for (ConstraintViolation<T> violation : violations) {
            for (Path.Node node : violation.getPropertyPath()) {
                List<String> messages = list.computeIfAbsent(node.getName(), k -> new ArrayList<>());
                messages.add(violation.getMessage());
            }
        }
        return list;
    }
}
