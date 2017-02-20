package kdc.reef.john.reefcaluclators;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class tankVolume extends AppCompatActivity {
    private double width;
    private double height;
    private double length;
    private double volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_volume);

    }

    public void resetButton(View view){
        EditText et1 = (EditText)findViewById(R.id.editWidth);
        EditText et2 = (EditText)findViewById(R.id.editHeight);
        EditText et3 = (EditText)findViewById(R.id.editLength);
        width =0.0;
        height =0.0;
        length =0.0;
        volume =0.0;

        et1.setText("");
        et2.setText("");
        et3.setText("");

    }

    public void calculateButton(View view){
        EditText et1 = (EditText)findViewById(R.id.editWidth);

        String str = et1.getText().toString();
        Double x =0.0;

        if(str.length()>0){
            try{
                x = Double.parseDouble(str);
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
        width = x;


        x =0.0;
        EditText et2 = (EditText)findViewById(R.id.editHeight);
        String str2 = et2.getText().toString();
        if(str.length()>0){
            try{
                x = Double.parseDouble(str2);
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
        height = x;

        x=0.0;
        EditText et3 = (EditText)findViewById(R.id.editLength);
        String str3 = et3.getText().toString();
        if(str.length()>0){
            try{
                x = Double.parseDouble(str3);
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
        length = x;

        Double vol =0.0;
        String volStr;
        TextView textVol = (TextView)findViewById(R.id.textVolume);
        if(width !=0.0 && height !=0.0 && length!=0.0){
            vol = width * height * length * 0.004329;
            DecimalFormat df = new DecimalFormat("###.###");
            volStr = df.format(vol);

            textVol.setText(volStr + " Gallons");
        }


    }
}
