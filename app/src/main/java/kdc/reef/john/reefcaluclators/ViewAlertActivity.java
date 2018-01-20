package kdc.reef.john.reefcaluclators;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.button;
import static android.R.attr.data;
import static android.R.attr.fingerprintAuthDrawable;

public class ViewAlertActivity extends AppCompatActivity implements View.OnClickListener {
    Alert alert;

    EditText edtMessage;
    TextView name, date, time;
    ToggleButton btnSUN, btnMON, btnTUES, btnWED, btnTHUR, btnFRI, btnSAT, btnBiweekly, btnMonthly, btnYearly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alert);
        edtMessage = (EditText) findViewById(R.id.editTxtMessage);
        name = (TextView) findViewById(R.id.txtAlertSelectedTitle);
        time = (TextView) findViewById(R.id.txtAlertSelectedTime);
        date = (TextView) findViewById(R.id.txtAlertSelectedDate);
        btnSUN = (ToggleButton) findViewById(R.id.toggleBtnSUN);
        btnMON = (ToggleButton) findViewById(R.id.toggleButtonMON);
        btnTUES = (ToggleButton) findViewById(R.id.toggleBtnTUES);
        btnWED = (ToggleButton) findViewById(R.id.toggleBtnWED);
        btnTHUR = (ToggleButton) findViewById(R.id.toggleBtnTHUR);
        btnFRI = (ToggleButton) findViewById(R.id.toggleBtnFRI);
        btnSAT = (ToggleButton) findViewById(R.id.toggleBtnSAT);
        btnBiweekly = (ToggleButton) findViewById(R.id.toggleBtnBiweekly);
        btnMonthly = (ToggleButton) findViewById(R.id.toggleBtnMonthly);
        btnYearly = (ToggleButton) findViewById(R.id.toggleBtnYearly);
        btnSUN.setOnClickListener(this);
        btnMON.setOnClickListener(this);
        btnTUES.setOnClickListener(this);
        btnWED.setOnClickListener(this);
        btnTHUR.setOnClickListener(this);
        btnFRI.setOnClickListener(this);
        btnSAT.setOnClickListener(this);
        btnBiweekly.setOnClickListener(this);
        btnMonthly.setOnClickListener(this);
        btnYearly.setOnClickListener(this);
        alert = AlertsActivity.lsAlerts.get(getIntent().getIntExtra("iPosition", 0));
        dataChanged();
    }

    public void changeAlertTitle(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewAlertActivity.this);
        builder.setTitle("Set Name");
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(input.getText().toString().length()!=0){
                    alert.setName(input.getText().toString());
                    dataChanged();
                }
                else{
                    Toast.makeText(ViewAlertActivity.this, "Please type a name for the alert", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do nothing
            }
        });
        builder.show();
    }

    public void changeAlertTime(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Time");

        final TimePicker picker = new TimePicker(this);
        builder.setView(picker);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alert.setTime(picker.getHour() + ":" +String.format("%02d",picker.getMinute()));
                if(alert.bActive){
                    //alarm is already set for a different time so we must cancel that alarm and set a new one
                    AlertsActivity.cancelReminder(getApplicationContext(), AlarmReceiver.class, -1, alert);
                    AlertsActivity.setReminder(getApplicationContext(), AlarmReceiver.class, -1, alert, null);
                }
                dataChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do nothing
            }
        });
        builder.show();
    }

    public void changeAlertDate(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Date");

        final DatePicker picker = new DatePicker(this);
        picker.setCalendarViewShown(false);
        picker.setMinDate(System.currentTimeMillis() - 1000);
        builder.setView(picker);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int   day  = picker.getDayOfMonth();
                int   month= picker.getMonth();
                int   year = picker.getYear()-1900;

                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                alert.setDate(sdf.format(new Date(year, month, day)));
                if(alert.bActive){
                    //alarm is already set for a different time so we must cancel that alarm and set a new one
                    AlertsActivity.cancelReminder(getApplicationContext(), AlarmReceiver.class, -1, alert);
                    AlertsActivity.setReminder(getApplicationContext(), AlarmReceiver.class, -1, alert, null);
                }
                dataChanged();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do nothing
            }
        });

        builder.show();
    }

    @Override
    public void onBackPressed(){
        Gson gson = new Gson();
        //save edit text for custom message
        alert.setMessage(edtMessage.getText().toString());
        String str = gson.toJson(AlertsActivity.lsAlerts);
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

    private void dataChanged(){
        time.setText(alert.getTime());
        name.setText(alert.getName());
        date.setText(alert.getDate());
        edtMessage.setText(alert.getMessage());
        btnSUN.setChecked(alert.getDay(0));
        btnMON.setChecked(alert.getDay(1));
        btnTUES.setChecked(alert.getDay(2));
        btnWED.setChecked(alert.getDay(3));
        btnTHUR.setChecked(alert.getDay(4));
        btnFRI.setChecked(alert.getDay(5));
        btnSAT.setChecked(alert.getDay(6));
        btnBiweekly.setChecked(alert.bBiweekly);
        btnMonthly.setChecked(alert.bMonthly);
        btnYearly.setChecked(alert.bYearly);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.toggleBtnSUN:
                alert.setDay(0);
                break;
            case R.id.toggleButtonMON:
                alert.setDay(1);
                break;
            case R.id.toggleBtnTUES:
                alert.setDay(2);
                break;
            case R.id.toggleBtnWED:
                alert.setDay(3);
                break;
            case R.id.toggleBtnTHUR:
                alert.setDay(4);
                break;
            case R.id.toggleBtnFRI:
                alert.setDay(5);
                break;
            case R.id.toggleBtnSAT:
                alert.setDay(6);
                break;
            case R.id.toggleBtnBiweekly:
                //all days of week will now be false
                alert.setBiweekly();
                break;
            case R.id.toggleBtnMonthly:
                //all days of week will now be false
                alert.setMonthly();
                break;
            case R.id.toggleBtnYearly:
                //all days of week will now be false
                alert.setYearly();
                break;
        }
        dataChanged();
    }
}