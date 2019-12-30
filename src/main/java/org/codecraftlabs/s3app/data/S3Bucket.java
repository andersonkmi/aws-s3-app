package org.codecraftlabs.s3app.data;

public class S3Bucket {
    private String name;

    private S3Bucket() {}

    public S3Bucket(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {

    }
}
