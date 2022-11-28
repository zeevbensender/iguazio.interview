package com.lightricks.homework.crawler.service;

import org.springframework.stereotype.Service;

@Service
public class AppContext {
    protected int maxLevel = -1;
    protected String outputFileName = null;

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
}
