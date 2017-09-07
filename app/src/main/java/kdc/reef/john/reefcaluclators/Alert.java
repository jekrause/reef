package kdc.reef.john.reefcaluclators;

import android.graphics.drawable.Drawable;
import java.sql.Time;
import java.util.Calendar;

/**
 * Created by John on 9/7/2017.
 */

public class Alert {
    Boolean bRepeats;
    Drawable oIcon; //Set icon that will show up for alert (water bucket, test tube, etc...)
    Time oCreationTime;
    Time oAlertTime;
    Calendar oRepeatSchedule;

    public Alert(){
        //// TODO: 9/7/2017
    }
}