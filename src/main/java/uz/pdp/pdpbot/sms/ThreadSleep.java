package uz.pdp.pdpbot.sms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThreadSleep {

    public static void main(String[] args) throws InterruptedException {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
        String strDate = dateFormat.format(date);
        String strTime = dateFormat1.format(date);
        int day = Integer.parseInt(strDate.substring(0, 2));

        int hour = Integer.parseInt(strTime.substring(0, 2));
        int min = Integer.parseInt(strTime.substring(3, 4));

    }

}



