package org.jinwalk.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jinwalk.readers.JinReader;

public class CliHelper {
    CommandLine cmd = null;
    CommandLineParser parser = new DefaultParser();
    private Options options;

    public CliHelper(String[] args) throws Exception {
        options = new Options();
        Option file = Option.builder("f").longOpt("file").argName("file").hasArg().desc("file to read").build();
        options.addOption(file);
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("f")) {
                handleReadFile(cmd.getOptionValue("f"));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleReadFile(String path) throws Exception {
        JinReader reader = new JinReader(path);
    }

}
