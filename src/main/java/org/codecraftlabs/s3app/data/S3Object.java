package org.codecraftlabs.s3app.data;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.util.Objects;

public class S3Object {
    private String key;
    private String eTag;
    private Instant lastModified;

    public S3Object(@Nonnull String key, @Nonnull String eTag, @Nonnull Instant lastModified) {
        this.key = key;
        this.eTag = eTag;
        this.lastModified = lastModified;
    }

    public String key() {
        return key;
    }

    public String eTag() {
        return eTag;
    }

    public Instant lastModified() {
        return lastModified;
    }

    @Override
    public int hashCode() {
        return key.hashCode() + eTag.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }

        S3Object instance = (S3Object) obj;
        return  Objects.equals(key, instance.key) &&
                Objects.equals(lastModified, instance.lastModified) &&
                Objects.equals(eTag, instance.eTag);
    }

    @Override
    public String toString() {
        return "{" +
                "\"key\":\"" + key + "\", " +
                "\"eTag\":\"" + eTag + "\", " +
                "\"lastModified\":\"" + lastModified.toString() +
                "\"}";
    }
}
