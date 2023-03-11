package org.jinwalk.readers;

import com.google.common.primitives.Bytes;
import com.opencsv.CSVReader;
import org.apache.commons.codec.binary.Hex;
import org.jinwalk.magic.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JinReader {
    private static final Logger logger = LoggerFactory.getLogger(JinReader.class);
    private FileInputStream file;
    private DataInputStream data;
    private List<Signature> signatures;

    public JinReader(String path) throws Exception {

        file = new FileInputStream(path);
        data = new DataInputStream(file);
        signatures = initializeSignatures();
        byte[] fileRaw = data.readAllBytes();
        for (Signature signature : signatures) {
            int index = Bytes.indexOf(fileRaw, signature.getSignature());
            if (index >= 0) {
                logger.info("Found {} at index {}", signature.getDescription(), index);

            }
        }

    }

    private List<Signature> initializeSignatures() throws IOException {
        String path = "D:\\Workspace\\JinWalk\\JinWalk\\resources\\signatures.csv";
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(path));
            reader.skip(1);
            List<Signature> signatures = new ArrayList<>();

            // read line by line
            String[] record = null;

            while ((record = reader.readNext()) != null) {
                Signature signature = new Signature();
                signature.setSignature(Hex.decodeHex(record[0]));
                signature.setDescription(record[1]);
                signatures.add(signature);
            }
            return signatures;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            reader.close();
        }
        return signatures;
    }
}
