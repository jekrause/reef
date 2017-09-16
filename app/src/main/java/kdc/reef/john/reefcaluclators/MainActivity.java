package kdc.reef.john.reefcaluclators;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.preference.PreferenceCategory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    Defaults d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        try{
            Gson gson = new Gson();
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
        }catch (IOException ex){
            d = new Defaults();
        }
        if(d.isPurchasedUpgrade()){
            findViewById(R.id.btnUpgrade).setVisibility(View.INVISIBLE);
        }
    }

    public void calculators(View view){
        Intent intent = new Intent(MainActivity.this, CalculatorsActivity.class);
        startActivity(intent);
    }

    public void tankInhabitants(View view){
        Intent intent = new Intent(MainActivity.this, TankInhabitantsActivity.class);
        startActivity(intent);
    }

    public void reefTestsClick (View view){
        Intent intent = new Intent(MainActivity.this, ReefTestsActivity.class);
        startActivity(intent);
    }

    public void alertsClick(View view){
        Intent intent = new Intent(MainActivity.this, AlertsActivity.class);
        startActivity(intent);
    }

    public void upgradeClick(View view){
        Intent intent = new Intent(MainActivity.this, UpgradeActivity.class);
        startActivity(intent);
    }
}