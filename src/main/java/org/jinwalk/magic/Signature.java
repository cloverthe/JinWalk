package org.jinwalk.magic;

import java.util.Arrays;
import java.util.StringJoiner;

public class Signature {

    private byte[] signature;
    private String description;

    public Signature() {
    }

    public Signature(byte[] signature, String description) {
        this.signature = signature;
        this.description = description;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override public String toString() {
        return new StringJoiner(", ", Signature.class.getSimpleName() + "[", "]").add(
                "signature=" + Arrays.toString(signature)).add("description='" + description + "'").toString();
    }
}
