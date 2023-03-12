package org.jinwalk.readers;

import com.google.common.primitives.Bytes;
import com.opencsv.CSVReader;
import org.apache.commons.codec.binary.Hex;
import org.jinwalk.magic.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class JinReader {
    private static final Logger logger = LoggerFactory.getLogger(JinReader.class);
    private long offsetFile = 0;
    private List<Signature> signatures = null;
    private byte[] fileRaw = new byte[0];

    public JinReader(String path) {

        FileInputStream file = null;
        try {
            file = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }

        try {
            assert file != null;
            try (DataInputStream data = new DataInputStream(file)) {
                signatures = initializeSignatures();
                if (signatures.isEmpty()) {
                    logger.error("Couldn't parse signatures file, or it's empty");
                }
                fileRaw = data.readAllBytes();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    private void handleSignatureCheck(byte[] fileRaw, Signature signature) {
        int newIndex = Bytes.indexOf(fileRaw, signature.getSignature());
        if (newIndex != -1) {
            offsetFile += newIndex;
            if (newIndex != 0) {
                offsetFile += signature.getSignature().length;
            }
            String offsetHex = Long.toHexString(offsetFile).toUpperCase();
            newIndex += signature.getSignature().length;
            logger.info("Found: {}, offset: DEC: {}, HEX: {}", signature.getDescription(), offsetFile, offsetHex);
            fileRaw = Arrays.copyOfRange(fileRaw, newIndex, fileRaw.length);
            handleSignatureCheck(fileRaw, signature);
        }
    }

    private void reset() {
        offsetFile = 0;
    }

    private List<Signature> initializeSignatures() throws IOException {
        InputStream in = getClass().getResourceAsStream("/signatures.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)));
        try (CSVReader reader = new CSVReader(br)) {
            reader.skip(1);
            signatures = new ArrayList<>();

            // read line by line
            String[] csvRecord;

            while ((csvRecord = reader.readNext()) != null) {
                Signature signature = new Signature();
                signature.setSignature(Hex.decodeHex(csvRecord[0]));
                signature.setDescription(csvRecord[1]);
                signatures.add(signature);
            }
            return signatures;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    public void analyze() {
        assert signatures != null;
        for (Signature signature : signatures) {
            reset();
            handleSignatureCheck(fileRaw, signature);
        }
    }
}
