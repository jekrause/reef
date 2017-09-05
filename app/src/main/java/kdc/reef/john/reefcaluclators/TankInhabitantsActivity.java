package kdc.reef.john.reefcaluclators;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TankInhabitantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_inhabitants);
    }

    public void fishButton(View view){
        Intent intent = new Intent(TankInhabitantsActivity.this, FishListActivity.class);
        startActivity(intent);
    }

    public void coralButton(View view){
        Intent intent = new Intent(TankInhabitantsActivity.this, CoralListActivity.class);
        startActivity(intent);
    }

    public void invertsButton(View view){
        Intent intent = new Intent(TankInhabitantsActivity.this, InvertsListActivity.class);
        startActivity(intent);
    }

    public void upgradeClick(View view){
        Intent intent = new Intent(TankInhabitantsActivity.this, UpgradeActivity.class);
        startActivity(intent);
    }

    public void changeDefaultsButton(View view){
        Intent intent = new Intent(TankInhabitantsActivity.this, ChangeDefaultsActivity.class);
        startActivity(intent);
    }
}
