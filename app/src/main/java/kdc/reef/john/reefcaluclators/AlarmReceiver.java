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
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by John on 10/7/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    List<Alert> lsAlerts;
    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyApp",  Integer.toString(intent.getIntExtra("RequestCode", -1)));
        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(TAG, "AlarmReceiver (onReceive) BOOT_COMPLETED");
                lsAlerts = loadAlerts(context);
                Log.d(TAG, "AlarmReceiver (onReceive) Number of Alarms to set = " + lsAlerts.size());
                for (int i = 0; i < lsAlerts.size(); i++) {
                    Log.d(TAG, "AlarmReceiver (onReceive) Setting Alarm #" + i);
                    AlertsActivity.setReminder(context, AlarmReceiver.class, i, null, lsAlerts);
                }
            }
        } else {
            AlertsActivity.showNotification(context, MainActivity.class, intent.getStringExtra("Name"), intent.getStringExtra("Message"), intent.getIntExtra("RequestCode", -1));
            AlertsActivity.checkAlarms(context);
        }
    }

    public List<Alert> loadAlerts(Context context){
        List<Alert> lsToReturn = new ArrayList<>();
        Gson gson = new Gson();
        try{
            FileInputStream fis = context.openFileInput("alertData.txt");
            InputStreamReader isr = new InputStreamReader (fis);
            BufferedReader buffreader = new BufferedReader(isr) ;
            String readString = buffreader.readLine() ;
            isr.close();
            buffreader.close();
            fis.close();
            if(!readString.isEmpty()){
                Log.d(TAG, "AlarmReceiver (loadAlerts) Loaded alerts successfully.");
                lsToReturn = gson.fromJson(readString,  new TypeToken<Collection<Alert>>(){}.getType());
            }
            else{
                Log.d(TAG, "AlarmReceiver (loadAlerts) Could not load alerts.");
            }
        }catch (Exception e){
            Log.d(TAG, "AlarmReceiver (loadAlerts) Exception: Could not load alerts.");
            e.printStackTrace();
        }
        return lsToReturn;
    }
}
