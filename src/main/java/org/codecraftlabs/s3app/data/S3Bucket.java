package org.codecraftlabs.s3app.data;

import java.time.Instant;
import java.util.Objects;

public class S3Bucket {
    private AwsRegion region = AwsRegion.US_EAST_1;
    private String name;
    private Instant creationDate;

    public S3Bucket(String name) {
        this.name = name;
    }

    public S3Bucket(String name, AwsRegion region) {
        this.name = name;
        this.region = region;
    }

    public S3Bucket(String name, Instant creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public S3Bucket(String name, AwsRegion region, Instant creationDate) {
        this.name = name;
        this.region = region;
        this.creationDate = creationDate;
    }

    public String name() {
        return name;
    }

    public Instant creationDate() {
        return creationDate;
    }

    public AwsRegion region() {
        return region;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + region.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        buffer.append("\"name\":\"").append(name).append("\", ");
        buffer.append("\"region\":\"").append(region.regionCode()).append("\", ");
        buffer.append("\"creationDate\":\"").append(creationDate.toString());
        buffer.append("\"}");
        return buffer.toString();
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
