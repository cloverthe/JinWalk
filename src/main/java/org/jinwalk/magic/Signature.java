package org.jinwalk.magic;

import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * Entity containing the signature itself and its description
 */
public class Signature {

    private byte[] signatureInHex;
    private String description;

    public Signature() {
        // constructor
    }

    public byte[] getSignature() {
        return signatureInHex;
    }

    public void setSignature(byte[] signatureInHex) {
        this.signatureInHex = signatureInHex;
    }

    public String getEncodedSignature() {
        return Hex.encodeHexString(this.getSignature()).toUpperCase();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override public String toString() {
        return new StringJoiner(", ", Signature.class.getSimpleName() + "[", "]").add(
                "signature=" + Arrays.toString(signatureInHex)).add("description='" + description + "'").toString();
    }
}
