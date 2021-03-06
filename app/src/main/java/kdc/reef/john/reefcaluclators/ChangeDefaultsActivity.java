package kdc.reef.john.reefcaluclators;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by John on 5/30/2017.
 */

public class ChangeDefaultsActivity extends AppCompatActivity {
    public static String currency = "$";
    private static int curPos =0;
    public static String measurement = "\"";
    private  static int measPos =0;
    public static boolean purchasedUpgrade = false;

    Defaults d;


    transient ArrayAdapter<CharSequence> measurementAdapter;
    transient ArrayAdapter<CharSequence> currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Gson gson = new Gson();
        try{
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

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

            FileInputStream filein = openFileInput("defaultData.txt");
            InputStreamReader isr = new InputStreamReader ( filein ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;
            String readString = buffreader.readLine ( ) ;

            isr.close ( ) ;
            buffreader.close();
            filein.close();


            if(!readString.isEmpty()){
                d  = gson.fromJson(readString, Defaults.class);
            }
            else{
                d = new Defaults();
            }

        }catch(Exception ex){
            d = new Defaults();
        }
        super.onCreate(savedInstanceState);
        currency = d.getCurrency();
        curPos = d.getCurPos();
        measurement = d.getMeasurement();
        measPos = d.getMeasPos();
        purchasedUpgrade = d.isPurchasedUpgrade();

        setContentView(R.layout.activity_change_defaults);

        Spinner currencySpinner = (Spinner) findViewById(R.id.spinner_currency);
        Spinner measurementSpinner = (Spinner) findViewById(R.id.spinner_measurement);
        String temp;

        currencySpinner.setSelection(curPos);
        measurementSpinner.setSelection(measPos);

        measurementAdapter = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);
        measurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measurementSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0) {measurement = "\""; measPos = position;}
                else if(position ==1) {measurement = "cm"; measPos = position;}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });

        currencyAdapter = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0) {currency = "$"; curPos = position;}
                else if(position ==1) {currency = "€"; curPos = position;}
                else if(position ==2) {currency = "£"; curPos = position;}
                else if(position ==3) {currency = "¥"; curPos = position;}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });
    }

    @Override
    public void onBackPressed(){
        d.setPurchasedUpgrade(purchasedUpgrade);
        d.setCurrency(currency);
        d.setCurPos(curPos);
        d.setMeasPos(measPos);
        d.setMeasurement(measurement);

        Gson gson = new Gson();
        String temp = gson.toJson(d);
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
            FileOutputStream fileout = openFileOutput("defaultData.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(temp);
            outputWriter.close();
        }catch(Exception ex){
            Log.d("MyApp", "Could not save data");
        }
        super.onBackPressed();
    }
}
