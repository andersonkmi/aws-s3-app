package org.codecraftlabs.s3app.data;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class S3Bucket {
    private AwsRegion region = AwsRegion.US_EAST_1;
    private String name;
    private Instant creationDate;
    private Set<S3Object> s3Objects = new HashSet<>();

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

    public void add(S3Object item) {
        s3Objects.add(item);
    }

    public Set<S3Object> objects() {
        return Collections.unmodifiableSet(s3Objects);
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
