/**
 * Copyright (c) 2013 - 2016, Coveo Solutions Inc.
 */
package com.github.glo2003.handlers;

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
        return List.of(new Project()).toJavaList();
    }
}
