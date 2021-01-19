package com.woo.base.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Log4j2Configur {
    public static void main(String[] args) throws IOException {
        File file = new File("D:/log4j2.xml");
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        final ConfigurationSource source = new ConfigurationSource(in);
        Configurator.initialize(null, source);

        Logger logger = LogManager.getLogger("mylog");
    }
}
