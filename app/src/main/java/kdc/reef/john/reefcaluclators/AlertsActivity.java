package kdc.reef.john.reefcaluclators;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class AlertsActivity extends AppCompatActivity {

    NotificationCompat.Builder oNotification;
    public static List<Alert> lsAlerts = new ArrayList<>();
    ArrayAdapter<Alert> oAlertsArrayAdapter;
    int iMaxNumber = 2;
    private static final int iUniqueID = 493940594;
    public static final int DAILY_REMINDER_REQUEST_CODE=100;

    ListView viewListView;
    TextView txtCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addAlert()){
                    Snackbar.make(view, "Alarm Added", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
                else{
                    Snackbar.make(view, "Please consider upgrading.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
        Log.d("MyApp","new Arraylist");
        txtCounter = (TextView) findViewById(R.id.txtCounter);
        Log.d("MyApp","Refresh Listings");
        refreshListings();
        Log.d("MyApp","RegisterClickCallBack");
        registerClickCallBack();
        Log.d("MyApp","End of onCreate");

    }

    public class MyListAdapter extends ArrayAdapter<Alert>{
        public MyListAdapter(){
            super(AlertsActivity.this, R.layout.alert_list_item, lsAlerts);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //make sure we have a view to work with
            View alertView = convertView;
            if(alertView == null) {
                alertView = getLayoutInflater().inflate(R.layout.alert_list_item, parent, false);
            }
            final ImageView imageViewIcon = (ImageView) alertView.findViewById(R.id.itemIcon);
            imageViewIcon.setBackgroundResource(lsAlerts.get(position).iIcon);
            //set name
            TextView txtName = (TextView) alertView.findViewById(R.id.txtAlertItemTitle);
            txtName.setText(lsAlerts.get(position).getName());
            //set date
            TextView txtDate = (TextView) alertView.findViewById(R.id.txtAlertItemDate);
            txtDate.setText(lsAlerts.get(position).getDate());
            //set time
            TextView txtTime = (TextView) alertView.findViewById(R.id.txtAlertItemTime);
            txtTime.setText(lsAlerts.get(position).getTime());
            //set repeats text
            TextView txtRepeats = (TextView) alertView.findViewById(R.id.txtAlertItemRepeats);
            String tempText = "";
            if(lsAlerts.get(position).getDay(0))
                tempText += "Su ";
            if(lsAlerts.get(position).getDay(1))
                tempText += "M ";
            if(lsAlerts.get(position).getDay(2))
                tempText += "Tu ";
            if(lsAlerts.get(position).getDay(3))
                tempText += "W ";
            if(lsAlerts.get(position).getDay(4))
                tempText += "Th ";
            if(lsAlerts.get(position).getDay(5))
                tempText += "F ";
            if(lsAlerts.get(position).getDay(6))
                tempText += "Sa ";
            if(tempText.length()==0)
                tempText = "Does not repeat";
            txtRepeats.setText(tempText);
            final Switch switch1 = (Switch) alertView.findViewById(R.id.switch1);
            if(lsAlerts.get(position).getTime()==null || (convertToMillis(lsAlerts.get(position).getDate(), lsAlerts.get(position).getTime()) < System.currentTimeMillis() && (!lsAlerts.get(position).bRepeats && lsAlerts.get(position).bActive))){
                lsAlerts.get(position).iIcon = R.drawable.alarm_clock_black;
                lsAlerts.get(position).bActive = false;
                switch1.setChecked(false);
            }
            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        //TODO check if time has passed before doing anything
                        if((lsAlerts.get(position).getTime() == null || lsAlerts.get(position).getDate()=="00-00-0000") || convertToMillis(lsAlerts.get(position).getDate(), lsAlerts.get(position).getTime()) > System.currentTimeMillis()){
                            switch1.setText("ON ");
                            lsAlerts.get(position).iIcon = R.drawable.alarm_clock_red;
                            lsAlerts.get(position).bActive = true;
                            if(lsAlerts.get(position).getTime() == null){
                                //there is no date so we should have the user set one
                                Intent intent = new Intent(AlertsActivity.this, ViewAlertActivity.class);
                                intent.putExtra("iPosition", position);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(AlertsActivity.this, "Alarm set for " + lsAlerts.get(position).getDate() +" at "+lsAlerts.get(position).getTime(), Toast.LENGTH_SHORT).show();
                                setReminder(AlertsActivity.this, AlarmReceiver.class, position);
                            }
                        }
                        else{
                            Toast.makeText(AlertsActivity.this,"Time has passed.", Toast.LENGTH_SHORT).show();
                            switch1.setChecked(false);
                        }
                    }
                    else{
                        switch1.setText("OFF");
                        lsAlerts.get(position).iIcon = R.drawable.alarm_clock_black;
                        lsAlerts.get(position).bActive = false;
                        cancelReminder(AlertsActivity.this, AlarmReceiver.class);
                    }
                }
            });
            //switch1.setChecked(lsAlerts.get(position).bActive);
            oAlertsArrayAdapter.notifyDataSetChanged();
            registerClickCallBack();
            return alertView;
        }
    }

    private void refreshListings(){
        Log.d("MyApp","RefreshListingsStart");
        if(oAlertsArrayAdapter!=null){
            oAlertsArrayAdapter.notifyDataSetChanged();
        }
        else{
            Gson gson = new Gson();
            try{
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Explain to the user why we need to read the contacts
                    }

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 80085);

                    // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                    // app-defined int constant that should be quite unique

                    return;
                }

                FileInputStream filein = openFileInput("alertData.txt");
                InputStreamReader isr = new InputStreamReader ( filein ) ;
                BufferedReader buffreader = new BufferedReader ( isr ) ;

                String readString = buffreader.readLine ( ) ;

                isr.close ( ) ;
                buffreader.close();
                filein.close();

                if(!readString.isEmpty()){
                    Log.d("MyApp","Read string not empty");
                    lsAlerts = gson.fromJson(readString,  new TypeToken<Collection<Alert>>(){}.getType());
                }
                else{
                    Log.d("MyApp","Read String empty");
                    lsAlerts = new ArrayList<>();
                }
                if(oAlertsArrayAdapter==null){
                    Log.d("MyApp","Populate ListView");
                    populateListView();
                    updateCounter();
                }

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        Log.d("MyApp","RefreshListings End");
    }

    public void populateListView(){
        Log.d("MyApp","PopulateListView Start");
        oAlertsArrayAdapter = new MyListAdapter();
        viewListView = (ListView) findViewById(R.id.listViewAlarms);
        viewListView.setCacheColorHint(Color.TRANSPARENT); // not sure if this is required for you.
        viewListView.setFastScrollEnabled(true);
        viewListView.setScrollingCacheEnabled(false);
        viewListView.setAdapter(oAlertsArrayAdapter);
        Log.d("MyApp","PopulateListView End");
    }

    public boolean addAlert(){
        if(lsAlerts.size()<iMaxNumber){
            lsAlerts.add(new Alert(null,null,true));
            if(oAlertsArrayAdapter != null){
                oAlertsArrayAdapter.notifyDataSetChanged();
            }
            populateListView();
            updateCounter();
            return true;
        }
        return false;
    }

    private void updateCounter(){
        txtCounter.setText(lsAlerts.size() + " of " + iMaxNumber);
        if(lsAlerts.size() == iMaxNumber){
            txtCounter.setTextColor(Color.RED);
        }
        else{
            txtCounter.setTextColor(Color.WHITE);
        }
    }

    //sets on click listeners
    private void registerClickCallBack(){
        viewListView = (ListView) findViewById(R.id.listViewAlarms);
        Log.d("MyApp","RegisterClickCallBack Start");
        viewListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("MyApp", "Setting LongClick Listener");
                AlertDialog.Builder builder = new AlertDialog.Builder(AlertsActivity.this);
                builder.setTitle("Confirm Deletion");
                builder.setMessage("Are you sure you want to delete this profile?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        lsAlerts.remove(position);
                        updateCounter();
                        oAlertsArrayAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });
        viewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MyApp", "Setting ClickListener");
                Intent intent = new Intent(AlertsActivity.this, ViewAlertActivity.class);
                intent.putExtra("iPosition", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Gson gson = new Gson();
        String str = gson.toJson(lsAlerts);
        try{
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    // Explain to the user why we need to read the contacts
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 80085);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique
                return;
            }
            FileOutputStream fileout = openFileOutput("alertData.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(str);
            outputWriter.close();
            fileout.close();
            Log.d("MyApp", "Data Saved: " + str);
        }catch (Exception ex){
            Log.d("MyApp", ex.toString());
        }
        super.onBackPressed();
    }

    public static void setReminder(Context context, Class<?> cls, int piposition)
    {
        // Enable a receiver
        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        intent1.putExtra("Name", lsAlerts.get(piposition).getName());
        intent1.putExtra("Position", piposition);
        //intent1.putExtra("Message", lsAlerts.get(piposition).getMessage());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                DAILY_REMINDER_REQUEST_CODE, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        //not using repeat reminder anymore because that should be handled when the alarm wakes the device.
        am.set(AlarmManager.RTC_WAKEUP, convertToMillis(lsAlerts.get(piposition).getDate(), lsAlerts.get(piposition).getTime()),pendingIntent);
    }

    public static void cancelReminder(Context context,Class<?> cls)
    {
        // Disable a receiver
        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                DAILY_REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    public static void showNotification(Context context,Class<?> cls,String title,String content)
    {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notificationIntent = new Intent(context, cls);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(
                DAILY_REMINDER_REQUEST_CODE,PendingIntent.FLAG_UPDATE_CURRENT);

        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(context);
        Notification notification = builder.setContentTitle(title)
                .setContentText(content).setAutoCancel(true)
                .setSound(alarmSound).setSmallIcon(R.drawable.alarm_clock_black)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(DAILY_REMINDER_REQUEST_CODE, notification);
    }

    private static long convertToMillis(String poDate, String poTime) {
        long i = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        Log.d("MyApp", "x "+poDate + " " + poTime);
        try{
            Date tempDate = sdf.parse(poDate + " " + poTime);
            Log.d("MyApp", "xx "+tempDate + "");
            return tempDate.getTime();
        }catch (Exception ex){
            Log.d("MyApp", "Failed...");
            ex.printStackTrace();
        }
        return i;
    }

    public static void checkAlarms(){
        //TODO
        Log.d("MyApp", "(checkAlarms) start of method");
        for(Alert alert:lsAlerts){
            alert.checkRepeats();
            //Check if the alarm is repeating and the time has passed.
            if(alert.bRepeats && convertToMillis(alert.getDate(), alert.getTime()) < System.currentTimeMillis()){
                //set new date and time for it.
                alert.computeNextAlarm();
                //TODO set next alarm

            }
        }
    }

    @Override
    protected void onResume(){
        refreshListings();
        registerClickCallBack();
        super.onResume();
    }
}
