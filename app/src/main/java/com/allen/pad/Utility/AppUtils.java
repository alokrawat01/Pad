package com.allen.pad.Utility;

import java.util.Calendar;
import java.util.Date;

public class AppUtils {
    public static Date getCurrentDateTime(){
        Date currentDate =  Calendar.getInstance().getTime();
        return currentDate;
    }

}
