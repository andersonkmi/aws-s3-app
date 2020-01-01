package org.codecraftlabs.s3app.data;

public class S3Object {
    private String key;

    public S3Object(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
