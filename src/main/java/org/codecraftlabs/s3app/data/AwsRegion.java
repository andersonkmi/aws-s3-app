package org.codecraftlabs.s3app.data;

public enum AwsRegion {
    AP_EAST_1("ap-east-1", "Asia Pacific (Hong Kong)"),
    AP_SOUTH_1("ap-south-1", "Asia Pacific (Mumbai)"),
    AP_NORTHEAST_1("ap-northeast-1", "Asia Pacific (Tokyo)"),
    AP_NORTHEAST_2("ap-northeast-2", "Asia Pacific (Seoul)"),
    AP_NORTHEAST_3("ap-northeast-3", "Asia Pacific (Osaka-Local)"),
    AP_SOUTHEAST_1("ap-southeast-1", "Asia Pacific (Singapore)"),
    AP_SOUTHEAST_2("ap-southeast-2", "Asia Pacific (Sydney)"),
    EU_WEST_1("eu-west-1", "Europe (Ireland)"),
    EU_WEST_2("eu-west-2", "Europe (London)"),
    EU_WEST_3("eu-west-3", "Europe (Paris)"),
    EU_NORTH_1("eu-north-1", "Europe (Stockholm)"),
    EU_CENTRAL_1("eu-central-1", "Europe (Frankfurt)"),
    ME_SOUTH_1("ap-south-1", "Middle East (Bahrain)"),
    CA_CENTRAL_1("ca-central-1", "Canada (Central)"),
    SA_EAST_1("sa-east-1", "South America (São Paulo)"),
    US_WEST_1(" us-west-1", "US West (N. California)"),
    US_WEST_2(" us-west-2", "US West (Oregon)"),
    US_EAST_2("us-east-2", "US East (Ohio)"),
    US_EAST_1("us-east-1", "US East (N. Virginia)");

    private String regionCode;
    private String description;

    AwsRegion(String regionCode, String description) {
        this.regionCode = regionCode;
        this.description = description;
    }

    public String regionCode() {
        return regionCode;
    }

    public String description() {
        return description;
    }
}
