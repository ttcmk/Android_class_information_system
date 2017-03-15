package com.example.popupmenutest;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@SuppressLint("SimpleDateFormat")
public class CalendarUtil {
        public static void main(String[] args) {

	    }

	   	    
	    public static String getTwoDay(String sj1, String sj2) {
	        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
	        SimpleDateFormat myFormatter2 = new SimpleDateFormat("yyyy-MM-dd");
	        long day = 0;
	        try {
	            java.util.Date date = myFormatter.parse(sj1);
	            java.util.Date mydate = myFormatter2.parse(sj2);
	            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
	        } catch (Exception e) {
	            return "";
	        }
	        return day + "";
	    }
	    	    
	    
	    public String getNowTime(String dateformat) {
	        Date now = new Date();
	        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// ø…“‘∑Ω±„µÿ–ﬁ∏ƒ»’∆⁄∏Ò Ω
	        String hehe = dateFormat.format(now);
	        return hehe;
	    }

	    
	    private int getMondayPlus() {
	        Calendar cd = Calendar.getInstance();
	        // ªÒµ√ΩÒÃÏ «“ª÷‹µƒµ⁄º∏ÃÏ£¨–«∆⁄»’ «µ⁄“ªÃÏ£¨–«∆⁄∂˛ «µ⁄∂˛ÃÏ......
	        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // “ÚŒ™∞¥÷–π˙¿Ò∞›“ª◊˜Œ™µ⁄“ªÃÏÀ˘“‘’‚¿Ôºı1
	        if (dayOfWeek == 1) {
	            return 0;
	        } else {
	            return 1 - dayOfWeek;
	        }
	    }

	    
	    public String getMondayOFWeek(String dateformat) {
	        int mondayPlus = this.getMondayPlus();
	        GregorianCalendar currentDate = new GregorianCalendar();
	        currentDate.add(GregorianCalendar.DATE, mondayPlus);
	        Date monday = currentDate.getTime();

	        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat) ;
	        String preMonday = dateFormat.format(monday);
	        return preMonday;
	    }

}
