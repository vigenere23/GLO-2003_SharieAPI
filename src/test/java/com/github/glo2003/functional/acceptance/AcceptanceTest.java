package com.github.glo2003.functional.acceptance;

import com.github.glo2003.functional.FunctionnalTest;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.github.glo2003.functional.acceptance"
)
public class AcceptanceTest extends FunctionnalTest {}
