package org.codecraftlabs.s3app.util;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public enum CommandLineS3Service {
    UPLOAD_OBJECTS("uploadObjects"),
    LIST_OBJECTS("listObjects"),
    CREATE_BUCKET("createBucket"),
    LIST_BUCKETS("listBuckets"),
    DELETE_BUCKET("deleteBucket");

    private String code;

    CommandLineS3Service(String code) {
        this.code = code;
    }

    protected String code() {
        return code;
    }

    public static Optional<CommandLineS3Service> findByCode(String code) throws IllegalArgumentException {
        for (var operation : values()) {
            if (operation.code().equals(code)) {
                return of(operation);
            }
        }
        return empty();
    }
}
