package org.jinwalk.readers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class JinReader {
    private static final Logger logger = LoggerFactory.getLogger(JinReader.class);
    FileInputStream file;
    DataInputStream data;
    public JinReader(String path) {
        try {
            file = new FileInputStream(path);
            data = new DataInputStream(file);

            try {
                System.out.println(Arrays.toString(data.readAllBytes()));
            } catch (IOException e) {
                logger.error(e.toString());
            }
        } catch (FileNotFoundException e) {
            logger.error(e.toString());
        }
    }
}
