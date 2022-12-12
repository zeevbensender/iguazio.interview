package com.interview.iguazio.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

@Service
public class InputFileReader {
    private static final Logger LOG = LoggerFactory.getLogger(InputFileReader.class);

    public Stream<String> readInputFile(String path) {
        LOG.info("<<< Reading file: {} >>>", path);
        try {
            return Files.lines(new File(path)
                            .toPath())
                    .map(s -> s.trim())
                    .filter(s -> !s.isEmpty());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
