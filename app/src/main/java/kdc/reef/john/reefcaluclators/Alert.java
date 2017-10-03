package kdc.reef.john.reefcaluclators;

import android.graphics.drawable.Drawable;
import android.widget.ArrayAdapter;
import android.widget.Switch;

import java.sql.Time;
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
    int iIcon; //Set icon that will show up for alert (water bucket, test tube, etc...)
    private String sDate = "00-00-00";
    private String sName;
    private String sTime;
    private boolean[] lsDaysOfWeek = new boolean[7];
    //Time oCreationTime;
    //Time oAlertTime;
    //Calendar oRepeatSchedule;
    //Switch oSwitch;

    public Alert(Time poAlertTime, Calendar poRepeatSchedule, Boolean pbRepeats) {
        //// TODO: 9/7/2017
        iIcon = R.drawable.alarm_clock_black;
        bRepeats = pbRepeats == null ? false : pbRepeats;
        sName = "Alert (Tap to edit)";
        bActive = false;
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
    public void setDay(int piDay, boolean pbVal){
        lsDaysOfWeek[piDay] = pbVal;
    }
}