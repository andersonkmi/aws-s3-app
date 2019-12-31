package org.codecraftlabs.s3app.data;

public enum AwsRegion {
    US_EAST_1("us-east-1");

    private String region;
    AwsRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
}
