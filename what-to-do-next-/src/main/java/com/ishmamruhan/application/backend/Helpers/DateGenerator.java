package com.ishmamruhan.application.backend.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateGenerator {

    public static String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
        return dateFormat.format(new Date());
    }
}
