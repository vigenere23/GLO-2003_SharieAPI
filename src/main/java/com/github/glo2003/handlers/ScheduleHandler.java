package com.github.glo2003.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.glo2003.response.Schedule;

import spark.Request;
import spark.Response;
import spark.Route;

public class ScheduleHandler implements Route
{
    @Override
    public Object handle(Request request, Response response) throws Exception
    {
        return new Schedule();
    }
}
