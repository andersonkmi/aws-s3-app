package org.codecraftlabs.s3app.util;

public enum S3Operation {
    CREATE_BUCKET("createBucket"),
    LIST_BUCKET("listBucket"),
    DELETE_BUCKET("deleteBucket");

    private String code;

    S3Operation(String code) {
        this.code = code;
    }
}
