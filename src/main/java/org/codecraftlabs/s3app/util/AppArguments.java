package org.codecraftlabs.s3app.util;

import java.util.Map;

public class AppArguments {
    public static final String SERVICE_OPTION = "service";
    public static final String BUCKET_OPTION = "bucket";
    public static final String REGION_OPTION = "region";

    private Map<String, String> arguments;

    public AppArguments(Map<String, String> arguments) {
        this.arguments = arguments;
    }

    public String option(String key) {
        return arguments.getOrDefault(key, "");
    }
}