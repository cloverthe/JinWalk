package org.jinwalk.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jinwalk.readers.JinReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CliHelper.class);
    CommandLine cmd = null;
    CommandLineParser parser = new DefaultParser();

    public CliHelper(String[] args) {
        Options options = new Options();
        Option file = Option.builder("f").longOpt("file").argName("file").hasArg().desc("file to read").build();
        options.addOption(file);
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("f")) {
                handleReadFile(cmd.getOptionValue("f"));
            }
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
        }

    }

    private void handleReadFile(String path) {
        JinReader reader = new JinReader(path);
        reader.analyze();
    }

}
