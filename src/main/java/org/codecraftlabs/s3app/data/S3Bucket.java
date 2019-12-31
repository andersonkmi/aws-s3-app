package org.codecraftlabs.s3app.data;

import java.util.Objects;

public class S3Bucket {
    private AwsRegion region = AwsRegion.US_EAST_1;
    private String name;

    private S3Bucket() {}

    public S3Bucket(String name) {
        this.name = name;
    }

    public S3Bucket(String name, AwsRegion region) {
        this.name = name;
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public AwsRegion getRegion() {
        return region;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + region.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!this.getClass().equals(other.getClass())) {
            return false;
        }

        S3Bucket instance = (S3Bucket) other;
        return Objects.equals(name, instance.name);
    }
}
