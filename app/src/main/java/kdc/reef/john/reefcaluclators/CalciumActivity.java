package kdc.reef.john.reefcaluclators;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalciumActivity extends AppCompatActivity {

    double tankSize,currentD,desiredD;
    TextView ml,floz,tsp;
    EditText gallons,current,desired;

    Button resetCalc, calculateCalc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcium);

        ml = (TextView) findViewById(R.id.textViewml);
        floz = (TextView) findViewById(R.id.textViewfloz);
        tsp = (TextView) findViewById(R.id.textViewtsp);
        gallons = (EditText) findViewById(R.id.alkalinityGallons);
        current = (EditText) findViewById(R.id.currentLvl);
        desired = (EditText) findViewById(R.id.desiredLvl);

        resetCalc = (Button) findViewById(R.id.resetCalc);
        calculateCalc = (Button) findViewById(R.id.calculateCalc);

        resetCalc.setOnClickListener(new View.OnClickListener() {
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

        calculateCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    tankSize = Double.parseDouble(gallons.getText().toString());
                    currentD = Double.parseDouble(current.getText().toString());
                    desiredD = Double.parseDouble(desired.getText().toString());

                    ml.setText((desiredD - currentD)*tankSize * 0.1023 + " ml");
                    floz.setText((desiredD - currentD)*tankSize * 0.0034 +" fl oz");
                    tsp.setText((desiredD - currentD)*tankSize * 0.0204 + " tsp");


                }catch (Exception ex){
                    Toast.makeText(getBaseContext(), "Make sure all forms are filled.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
