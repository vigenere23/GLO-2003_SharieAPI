/**
 * Copyright (c) 2013 - 2016, Coveo Solutions Inc.
 */
package com.github.glo2003.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Project
{
    @JsonProperty("id")
    public String ID;
    @JsonProperty("name")
    public String name;
    @JsonProperty("html_url")
    public String htmlUrl;
    //@JsonProperty("contributors")
    //    public List<Contributor> contributors;
    @JsonProperty("language")
    public String language;
    //@JsonProperty("branches")
    //public List<Branch> branches;
    @JsonProperty("badges")
    public List<String> badges;
    @JsonProperty("open_issues_count")
    public Integer openIssuesCount;
}
