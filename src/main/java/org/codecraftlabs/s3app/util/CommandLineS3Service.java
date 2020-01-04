package org.codecraftlabs.s3app.util;

public enum CommandLineS3Service {
    CREATE_BUCKET("createBucket"),
    LIST_BUCKET("listBucket"),
    DELETE_BUCKET("deleteBucket");

    private String code;

    CommandLineS3Service(String code) {
        this.code = code;
    }

    protected String code() {
        return code;
    }

    public static CommandLineS3Service findByCode(String code) throws IllegalArgumentException {
        for (CommandLineS3Service operation : values()) {
            if (operation.code().equals(code)) {
                return operation;
            }
        }
        return null;
    }
}
