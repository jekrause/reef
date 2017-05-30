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
    public static String currency;
    public static String measurement;


    ArrayAdapter<CharSequence> measurementAdapter;
    ArrayAdapter<CharSequence> currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_defaults);

        Spinner currencySpinner = (Spinner) findViewById(R.id.spinner_currency);
        Spinner measurementSpinner = (Spinner) findViewById(R.id.spinner2);
        String temp;

        measurementAdapter = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);
        measurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measurementSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0) {measurement = "\"";}
                else if(position ==1) {measurement = "cm";}
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
                if(position ==0) {currency = "$";}
                else if(position ==1) {currency = "€";}
                else if(position ==2) {currency = "£";}
                else if(position ==3) {currency = "¥";}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(this, currency + "    " + measurement, Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
