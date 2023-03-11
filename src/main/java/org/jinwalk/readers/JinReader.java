package org.jinwalk.readers;

import com.google.common.primitives.Bytes;
import com.opencsv.CSVReader;
import org.apache.commons.codec.binary.Hex;
import org.jinwalk.magic.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JinReader {
    private static final Logger logger = LoggerFactory.getLogger(JinReader.class);
    private static final String RESOURCES_SIGNATURES_CSV = "resources/signatures.csv";

    public JinReader(String path) {

        FileInputStream file = null;
        try {
            file = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
        List<Signature> signatures = null;
        byte[] fileRaw = new byte[0];
        try {
            assert file != null;
            try (DataInputStream data = new DataInputStream(file)) {
                signatures = initializeSignatures();
                fileRaw = data.readAllBytes();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        assert signatures != null;
        for (Signature signature : signatures) {
            int index = Bytes.indexOf(fileRaw, signature.getSignature());
            if (index >= 0) {
                logger.info("Found {} at index {}", signature.getDescription(), index);

            }
        }

    }

    private List<Signature> initializeSignatures() throws IOException {
        File signaturesFile = new File(RESOURCES_SIGNATURES_CSV);

        try (CSVReader reader = new CSVReader(new FileReader(signaturesFile))) {
            reader.skip(1);
            List<Signature> signatures = new ArrayList<>();

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
}
