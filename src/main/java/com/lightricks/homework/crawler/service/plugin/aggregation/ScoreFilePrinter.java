package com.lightricks.homework.crawler.service.plugin.aggregation;

import com.lightricks.homework.crawler.service.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Prints data to the configured TSV file
 */
@Service
@Profile("filePrinter")
@Primary
public class ScoreFilePrinter extends AbstractScorePrinter {
    private static final Logger LOG = LoggerFactory.getLogger(ScoreFilePrinter.class);


    public ScoreFilePrinter(@Autowired AppContext context) {
        super(context);
    }

    @Override
    public void print() {
        try (FileWriter fw = new FileWriter(context.getOutputFileName())) {
            fw.write(output.toString());
        } catch (IOException e) {
            LOG.error("Failed to write to output file: {}", context.getOutputFileName(), e);
        }
    }
}
