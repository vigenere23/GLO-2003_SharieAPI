/**
 * Copyright (c) 2013 - 2016, Coveo Solutions Inc.
 */
package com.github.glo2003.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.glo2003.response.Project;

import javaslang.collection.List;
import spark.Request;
import spark.Response;
import spark.Route;

public class ProjectHandler implements Route
{
    @Override
    public Object handle(Request request,
                         Response response) throws Exception
    {
        Map<String, Long> s = new HashMap<String, Long>();

        s.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().intValue()));

        //map(entry -> new Map.Entry<String,Integer>()).collect(Collectors.toMap())
        return List.of(new Project()).toJavaList();
    }
}
