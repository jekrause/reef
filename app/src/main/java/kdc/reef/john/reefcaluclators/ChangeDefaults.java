package kdc.reef.john.reefcaluclators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by John on 5/30/2017.
 */

public class ChangeDefaults extends AppCompatActivity {
    public static String currency = "$";
    private static int curPos =0;
    public static String measurement = "\"";
    private  static int measPos =0;
    public static boolean purchasedUpgrade = false;


    ArrayAdapter<CharSequence> measurementAdapter;
    ArrayAdapter<CharSequence> currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            }
        });
    }
}
