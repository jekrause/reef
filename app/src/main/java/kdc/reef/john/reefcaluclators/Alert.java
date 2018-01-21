package kdc.reef.john.reefcaluclators;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by John on 9/7/2017.
 */

public class Alert {
    boolean bRepeats;
    boolean bActive;
    boolean bBiweekly;
    boolean bMonthly;
    boolean bYearly;
    int iIcon; //Set icon that will show up for alert (water bucket, test tube, etc...)
    private int iRequestCode;
    private String sDate = "00-00-00";
    private String sMessage;
    private String sName;
    private String sTime;
    private boolean[] lsDaysOfWeek = new boolean[7];

    public Alert(Time poAlertTime, Calendar poRepeatSchedule, Boolean pbRepeats, int piRequestCode) {
        //// TODO: 9/7/2017
        iIcon = R.drawable.alarm_clock_black;
        bRepeats = pbRepeats == null ? false : pbRepeats;
        sName = "Alert (Tap to edit)";
        bActive = false;
        iRequestCode = piRequestCode;
    }

    public void checkRepeats(){
        if(bBiweekly||bMonthly||bYearly){
            bRepeats = true;
            return;
        }

        for(int i=0; i<lsDaysOfWeek.length; i++){
            if(lsDaysOfWeek[i]){
                bRepeats = true;
                Log.d("MyApp","Alert (checkRepeats) setting bRepeats = true");
                return;
            }
        }
        Log.d("MyApp","Alert (checkRepeats) setting bRepeats = true");
        bRepeats = false;
    }

    public void computeNextAlarm(){
        Log.d("MyApp", "Alert (computeNextAlarm) start of method");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        //Math for getting next time.
        if(bBiweekly){
            calendar.add(Calendar.WEEK_OF_MONTH, 2);
            sDate = sdf.format(calendar.getTime());
        }
        else if(bMonthly){
            calendar.add(Calendar.MONTH,1);
            sDate = sdf.format(calendar.getTime());
        }
        else if(bYearly){
            calendar.add(Calendar.YEAR, 1);
            sDate = sdf.format(calendar.getTime());
        }
        else{
            //First find next day.
            int todayDay = calendar.get(Calendar.DAY_OF_WEEK)-1;
            int index;
            for(index = 1; index < 7; index++){
                if(lsDaysOfWeek[(index + todayDay)% 6]) {
                    Log.d("MyApp", "Alert (computeNextAlarm) next day of week is " + index);
                    break;
                }
            }
            calendar.add(Calendar.DATE, index+1);
            sDate = sdf.format(calendar.getTime());
        }
        Log.d("MyApp", "Alert (computeNextAlarm) next date is " + sDate);
    }

    public String getMessage(){
        return sMessage;
    }
    public void setMessage(String psMessage){
        sMessage = psMessage;
    }
    public String getName() {
        return sName;
    }
    public void setName(String psName) {
        sName = psName;
    }
    public String getTime() {
        return sTime;
    }
    public void setTime(String psTime) {
        sTime = psTime;
    }
    public String getDate() {
        return sDate;
    }
    public void setDate(String psDate) {
        sDate = psDate;
    }
    public boolean[] getDaysOfWeek() {
        return lsDaysOfWeek;
    }
    public void setDaysOfWeek(boolean pbSunday, boolean pbMonday, boolean pbTuesday, boolean pbWednesday, boolean pbThursday, boolean pbFriday, boolean pbSaturday) {
        lsDaysOfWeek[0] = pbSunday;
        lsDaysOfWeek[1] = pbMonday;
        lsDaysOfWeek[2] = pbTuesday;
        lsDaysOfWeek[3] = pbWednesday;
        lsDaysOfWeek[4] = pbThursday;
        lsDaysOfWeek[5] = pbFriday;
        lsDaysOfWeek[6] = pbSaturday;
    }
    public boolean getDay(int piDay) {
        return lsDaysOfWeek[piDay];
    }
    public void setDay(int piDay){
        lsDaysOfWeek[piDay] = !lsDaysOfWeek[piDay];
        bBiweekly = false;
        bMonthly = false;
        bYearly = false;
        checkRepeats();
    }
    public void setBiweekly(){
        bBiweekly = !bBiweekly;
        bMonthly = false;
        bYearly = false;
        for(int i = 0; i <7; i++)
            lsDaysOfWeek[i] = false;
    }
    public void setMonthly(){
        bMonthly = !bMonthly;
        bBiweekly = false;
        bYearly = false;
        for(int i = 0; i <7; i++)
            lsDaysOfWeek[i] = false;
    }
    public void setYearly(){
        bYearly = !bYearly;
        bBiweekly = false;
        bMonthly = false;
        for(int i = 0; i <7; i++)
            lsDaysOfWeek[i] = false;
    }

    public int getRequestCode(){
        return iRequestCode;
    }
}