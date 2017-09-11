package kdc.reef.john.reefcaluclators;

import android.graphics.drawable.Drawable;
import android.widget.ArrayAdapter;
import android.widget.Switch;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by John on 9/7/2017.
 */

public class Alert {
    Boolean bRepeats;
    Drawable oIcon; //Set icon that will show up for alert (water bucket, test tube, etc...)
    Time oCreationTime;
    Time oAlertTime;
    Calendar oRepeatSchedule;
    Switch oSwitch;

    public Alert(Time poAlertTime, Calendar poRepeatSchedule, Boolean pbRepeats, Drawable poIcon){
        //// TODO: 9/7/2017
        bRepeats = pbRepeats == null ? false : pbRepeats;
    }
}