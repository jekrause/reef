package kdc.reef.john.reefcaluclators;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by John on 10/7/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    List<Alert> lsAlerts;
    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent){
        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED");
                lsAlerts = AlertsActivity.lsAlerts;
                for(int i = 0; i<lsAlerts.size(); i++){
                    AlertsActivity.setReminder(context, AlarmReceiver.class, i);
                }
            }
        }
        AlertsActivity.showNotification(context, MainActivity.class, "Alarm test", "test now");
    }


}
