package kdc.reef.john.reefcaluclators;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CalculatorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculators);
    }

    public void tankVolumeButton(View view){
        Intent intent = new Intent(CalculatorsActivity.this, tankVolumeActivity.class);
        startActivity(intent);
    }

    public void twoPartDosing(View view){
        Intent intent = new Intent(CalculatorsActivity.this, PartDosingActivity.class);
        startActivity(intent);
    }

    public void calciumClick(View view){
        Intent intent = new Intent(CalculatorsActivity.this, CalciumActivity.class);
        startActivity(intent);
    }

    public void alkalinityClick(View view){
        Intent intent = new Intent(CalculatorsActivity.this, AlkalinityActivity.class);
        startActivity(intent);
    }

}
