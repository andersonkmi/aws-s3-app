package org.codecraftlabs.s3app.util;

public enum S3Operation {
    CREATE_BUCKET("createBucket"),
    LIST_BUCKET("listBucket"),
    DELETE_BUCKET("deleteBucket");

    private String code;

    S3Operation(String code) {
        this.code = code;
    }

    protected String code() {
        return code;
    }

    public static S3Operation findByCode(String code) throws IllegalArgumentException {
        for (S3Operation operation : values()) {
            if (operation.code().equals(code)) {
                return operation;
            }
        }
        return null;
    }
}
