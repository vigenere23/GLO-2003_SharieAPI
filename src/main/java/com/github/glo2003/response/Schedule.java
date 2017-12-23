package com.github.glo2003.response;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Schedule 
{
    @JsonProperty("days")
    public List days = new ArrayList();
}
