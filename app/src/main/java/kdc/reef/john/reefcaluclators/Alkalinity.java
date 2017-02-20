package kdc.reef.john.reefcaluclators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Alkalinity extends AppCompatActivity {

    Spinner spin;
    ArrayAdapter<CharSequence> adapterSpinner;
    int currentlySelected = 1;
    double tankSize,currentD,desiredD;

    TextView ml,floz,tsp;
    EditText gallons,current,desired;
    Button resetAlk, calculateAlk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alkalinity);

        resetAlk = (Button) findViewById(R.id.resetAlk);
        calculateAlk = (Button) findViewById(R.id.calculateCalc);

        ml = (TextView) findViewById(R.id.textViewml);
        floz = (TextView) findViewById(R.id.textViewfloz);
        tsp = (TextView) findViewById(R.id.textViewtsp);
        gallons = (EditText) findViewById(R.id.alkalinityGallons);
        current = (EditText) findViewById(R.id.currentLvl);
        desired = (EditText) findViewById(R.id.desiredLvl);

        resetAlk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ml.setText("Amount (ml)");
                floz.setText("Amount (fl oz)");
                tsp.setText("Amount (tsp)");
                gallons.setText("");
                desired.setText("");
                current.setText("");
            }
        });

        calculateAlk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    tankSize = Double.parseDouble(gallons.getText().toString());
                    currentD = Double.parseDouble(current.getText().toString());
                    desiredD = Double.parseDouble(desired.getText().toString());
                    if(currentlySelected ==1){
                        ml.setText((desiredD - currentD)*tankSize * 0.714 + " ml");
                        floz.setText((desiredD - currentD)*tankSize * 0.024 +" fl oz");
                        tsp.setText((desiredD - currentD)*tankSize * 0.142 + " tsp");
                    }
                    else if(currentlySelected == 2){
                        ml.setText((desiredD - currentD)*tankSize * 0.2 + " ml");
                        floz.setText((desiredD - currentD)*tankSize * 0.067 +" fl oz");
                        tsp.setText((desiredD - currentD)*tankSize * 0.4 + " tsp");

                    }
                    else if(currentlySelected == 3 ){
                        ml.setText((desiredD - currentD)*tankSize * .04 + " ml");
                        floz.setText((desiredD - currentD)*tankSize * 0.0013 +" fl oz");
                        tsp.setText((desiredD - currentD)*tankSize * 0.008 + " tsp");
                    }

                }catch (Exception ex){
                    Toast.makeText(getBaseContext(), "Make sure all forms are filled.", Toast.LENGTH_SHORT).show();
                }
            }

        });

        spin = (Spinner) findViewById(R.id.spinner2);
        adapterSpinner = ArrayAdapter.createFromResource(this, R.array.measurements,android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapterSpinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){currentlySelected = 1;}
                else if(position == 1){currentlySelected = 2;}
                else if(position == 2){currentlySelected = 3;}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //1==dKH
        //2==meq/l
        //3==ppm


    }


}
