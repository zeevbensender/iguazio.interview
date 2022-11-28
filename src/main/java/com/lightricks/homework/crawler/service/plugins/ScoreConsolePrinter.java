package com.lightricks.homework.crawler.service.plugins;

import com.lightricks.homework.crawler.service.AppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Service
@Profile("consolePrinter")
@Primary
public class ScoreConsolePrinter extends AbstractScorePrinter {

    public ScoreConsolePrinter(@Autowired AppContext context) {
        super(context);
    }

    @Override
    public void print() {
        System.out.println(output);
    }

}
