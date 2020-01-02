package org.codecraftlabs.s3app.data;

import javax.annotation.Nonnull;

public class S3Object {
    private String key;

    public S3Object(@Nonnull String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
