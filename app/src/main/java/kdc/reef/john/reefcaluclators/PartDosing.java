package kdc.reef.john.reefcaluclators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PartDosing extends AppCompatActivity {

    EditText tankSize;
    CheckBox cb1;
    CheckBox cb2;
    boolean recipeType;

    CheckBox cbFO;
    CheckBox cbLowD;
    CheckBox cbMixed;
    CheckBox cbSPS;
    boolean tankType;


    TextView editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_dosing);

        tankSize = (EditText) findViewById(R.id.editTextTankSize);
        cb1 = (CheckBox) findViewById(R.id.checkBox1);
        cb2 = (CheckBox) findViewById(R.id.checkBox2);
        recipeType = false;

        cbFO = (CheckBox) findViewById(R.id.checkBoxFO);
        cbLowD = (CheckBox) findViewById(R.id.checkBoxLowDemand);
        cbMixed = (CheckBox) findViewById(R.id.checkBoxMixedReef);
        cbSPS = (CheckBox) findViewById(R.id.checkBoxSPS);
        tankType = false;

        editText2 = (TextView) findViewById(R.id.editText2);
    }

    public void calculateDoseButton(View view){
        double dosage = 0.0;
        double tankVolume = 0.0;
        if(correctSettings()){
            try {
                tankVolume = Integer.parseInt(tankSize.getText().toString());
            }catch (RuntimeException ex){
                Toast.makeText(PartDosing.this, "Invalid. Enter an Integer", Toast.LENGTH_SHORT).show();
            }
            if(cb1.isChecked()){
                if(cbFO.isChecked()){
                    dosage = calculateTwoPart(tankVolume,0.1);
                }
                if(cbMixed.isChecked()){
                    dosage = calculateTwoPart(tankVolume,0.5);
                }
                if(cbLowD.isChecked()){
                    dosage = calculateTwoPart(tankVolume,0.3);
                }
                if(cbSPS.isChecked()){
                    dosage = calculateTwoPart(tankVolume,1);
                }
            }

            if(cb2.isChecked()){
                if(cbFO.isChecked()){
                    dosage = 2*calculateTwoPart(tankVolume,0.1);
                }
                if(cbMixed.isChecked()){
                    dosage = 2*calculateTwoPart(tankVolume,0.5);
                }
                if(cbLowD.isChecked()){
                    dosage = 2*calculateTwoPart(tankVolume,0.3);
                }
                if(cbSPS.isChecked()){
                    dosage = 2*calculateTwoPart(tankVolume,1);
                }
            }

            editText2.setText(dosage +" ml");
        }
    }

    public double calculateTwoPart(double tankSize, double dosage){
        double x = 0.0;

        x = tankSize*dosage;

        return  x;
    }

    public boolean correctSettings(){
        if(cb1.isChecked()^cb2.isChecked()){
            if(cbFO.isChecked()^cbLowD.isChecked()^cbSPS.isChecked()^cbMixed.isChecked()){
                if(!tankSize.getText().equals(null)){
                    return true;
                }
            }
        }
        Toast.makeText(PartDosing.this, "You must select 1 recipe and 1 tank type.", Toast.LENGTH_SHORT).show();
        return false;
        
    }

    public void resetDoseButton(View view){
        cb1.setChecked(false);
        cb2.setChecked(false);
        cbFO.setChecked(false);
        cbLowD.setChecked(false);
        cbMixed.setChecked(false);
        cbSPS.setChecked(false);
        tankSize.setText("");
        editText2.setText("");

    }
}
