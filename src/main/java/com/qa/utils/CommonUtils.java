package com.qa.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    public static final long WAIT = 10;
    public static final long IMPLICIT_WAIT=10;
    public static final long EXPLICIT_WAIT=15;
    public static final long LongWAIT = 40;

    public String dateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public Logger log() {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }

}
