package com.lightricks.homework.crawler.service.plugin.processing;

import com.lightricks.homework.crawler.model.PageMessage;

public interface PageProcessingPlugin extends Comparable<PageProcessingPlugin>{
    void process(PageMessage pageMessage);
    int getPriority();

    @Override
    default int compareTo(PageProcessingPlugin o) {
        return Integer.compare(this.getPriority(), o.getPriority());
    }
}
