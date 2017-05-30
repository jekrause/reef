package kdc.reef.john.reefcaluclators;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TankInhabitants extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_inhabitants);
    }

    public void fishButton(View view){
        Intent intent = new Intent(TankInhabitants.this, FishList.class);
        startActivity(intent);
    }

    public void coralButton(View view){
        Intent intent = new Intent(TankInhabitants.this, CoralList.class);
        startActivity(intent);
    }

    public void invertsButton(View view){
        Intent intent = new Intent(TankInhabitants.this, InvertsList.class);
        startActivity(intent);
    }

    public void changeDefaultsButton(View view){
        Intent intent = new Intent(TankInhabitants.this, ChangeDefaults.class);
        startActivity(intent);
    }
}
