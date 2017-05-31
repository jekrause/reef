package kdc.reef.john.reefcaluclators;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new Gson();
        try{
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

            FileInputStream filein = openFileInput("defaultData.txt");
            Log.d("MyApp","1");
            InputStreamReader isr = new InputStreamReader ( filein ) ;
            Log.d("MyApp","2");
            BufferedReader buffreader = new BufferedReader ( isr ) ;
            Log.d("MyApp","3");

            String readString = buffreader.readLine ( ) ;
            Log.d("MyApp","4");


            isr.close ( ) ;
            buffreader.close();
            filein.close();


            if(!readString.isEmpty()){
                Defaults cd  = gson.fromJson(readString, Defaults.class);

                Log.d("MyApp", readString);

                Toast.makeText(this, cd.isPurchasedUpgrade()+" "+ cd.getCurrency() + " "+ cd.getMeasurement(), Toast.LENGTH_SHORT).show();

                Log.d("MyApp","read in " + readString);

                Log.d("MyApp","finished");
            }
            else{
                Log.d("MyApp", "readString is empty");
            }

        }catch(Exception ex){
            Log.d("MyApp","blown up");
            Log.d("MyApp", ex.getLocalizedMessage());

        }
    }


    public void calculators(View view){
        Intent intent = new Intent(MainActivity.this, Calculators.class);
        startActivity(intent);
    }

    public void tankInhabitants(View view){
        Intent intent = new Intent(MainActivity.this, TankInhabitants.class);
        startActivity(intent);
    }


}
