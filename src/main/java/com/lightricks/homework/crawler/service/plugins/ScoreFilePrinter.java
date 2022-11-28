package com.lightricks.homework.crawler.service.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;


@Service
@Profile("filePrinter")
@Primary
public class ScoreFilePrinter extends AbstractScorePrinter {
    private static final Logger LOG = LoggerFactory.getLogger(ScoreFilePrinter.class);

    @Value("${f}")
    private String outputFileName;

    @Override
    public void close() {
        try (FileWriter fw = new FileWriter(outputFileName)) {
            fw.write(output.toString());
        } catch (IOException e) {
            LOG.error("Failed to write to output file: {}", outputFileName, e);
        }
    }
}
