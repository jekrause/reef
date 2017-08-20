package kdc.reef.john.reefcaluclators;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    public void upgradeClick(View view){
        Intent intent = new Intent(TankInhabitants.this, Upgrade.class);
        startActivity(intent);
    }

    public void changeDefaultsButton(View view){
        Intent intent = new Intent(TankInhabitants.this, ChangeDefaults.class);
        startActivity(intent);
    }

    public void expensesClick(View view){
        Defaults d;
        List<FishProfile> fishList;
        List<CoralProfile> coralList;
        List<InvertsProfile> invertsList;

        int coralTotal, coralTotalPrice =0;
        int fishTotal, fishTotalPrice =0;
        int invertsTotal, invertsTotalPrice =0;


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
            InputStreamReader isr = new InputStreamReader ( filein ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String defaultString = buffreader.readLine ( ) ;


            isr.close ( ) ;
            buffreader.close();
            filein.close();

            if(!defaultString.isEmpty()){
                d  = gson.fromJson(defaultString, Defaults.class);
//                invertsList = gson.fromJson(invertsString,new TypeToken<Collection<InvertsProfile>>(){}.getType());
//                coralList = gson.fromJson(coralString, new TypeToken<Collection<CoralProfile>>(){}.getType());
//                fishList = gson.fromJson(fishString, new TypeToken<Collection<FishList>>(){}.getType());
            }
            else{
                d = new Defaults();
//                invertsList = new ArrayList<>();
//                coralList = new ArrayList<>();
//                fishList = new ArrayList<>();
            }

        }catch(Exception ex){
            d = new Defaults();
//            invertsList = new ArrayList<>();
//            coralList = new ArrayList<>();
//            fishList = new ArrayList<>();
        }

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

            FileInputStream filein = openFileInput("coralData.txt");
            InputStreamReader isr = new InputStreamReader ( filein ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String coralString = buffreader.readLine ( ) ;


            isr.close ( ) ;
            buffreader.close();
            filein.close();

            if(!coralString.isEmpty()){
//                d  = gson.fromJson(defaultString, Defaults.class);
//                invertsList = gson.fromJson(invertsString,new TypeToken<Collection<InvertsProfile>>(){}.getType());
                coralList = gson.fromJson(coralString, new TypeToken<Collection<CoralProfile>>(){}.getType());
//                fishList = gson.fromJson(fishString, new TypeToken<Collection<FishList>>(){}.getType());
            }
            else{
//                d = new Defaults();
//                invertsList = new ArrayList<>();
                coralList = new ArrayList<>();
//                fishList = new ArrayList<>();
            }

        }catch(Exception ex){
//            d = new Defaults();
//            invertsList = new ArrayList<>();
            coralList = new ArrayList<>();
//            fishList = new ArrayList<>();
        }

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

            FileInputStream filein = openFileInput("invertsData.txt");
            InputStreamReader isr = new InputStreamReader ( filein ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String invertsString = buffreader.readLine ( ) ;


            isr.close ( ) ;
            buffreader.close();
            filein.close();

            if(!invertsString.isEmpty()){
                invertsList = gson.fromJson(invertsString,new TypeToken<Collection<InvertsProfile>>(){}.getType());
            }
            else{
                invertsList = new ArrayList<>();
            }

        }catch(Exception ex){
            invertsList = new ArrayList<>();
        }

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

            FileInputStream filein = openFileInput("fishData.txt");
            InputStreamReader isr = new InputStreamReader ( filein ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String fishString = buffreader.readLine ( ) ;


            isr.close ( ) ;
            buffreader.close();
            filein.close();

            if(!fishString.isEmpty()){
                fishList = gson.fromJson(fishString, new TypeToken<Collection<FishList>>(){}.getType());
            }
            else{
                fishList = new ArrayList<>();
            }

        }catch(Exception ex){
            fishList = new ArrayList<>();
        }

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TankInhabitants.this);

        builder.setTitle("Total Expenses");

        coralTotal = coralList.size();
        invertsTotal = invertsList.size();
        fishTotal = fishList.size();

        for(int i=0; i<coralTotal; i++){
            coralTotalPrice+=coralList.get(i).getPrice();
        }
        for(int i=0; i<fishTotal; i++){
            fishTotalPrice+=fishList.get(i).getPrice();
        }
        for(int i=0; i<invertsTotal; i++){
            invertsTotalPrice+=invertsList.get(i).getPrice();
        }


        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView tvFish = new TextView(this);
        TextView tvInverts = new TextView(this);
        TextView tvCoral = new TextView(this);

        tvFish.setText("Fish \tNumber:"+fishTotal+"\tCost:"+d.getCurrency()+fishTotalPrice);
        tvInverts.setText("Inverts \tNumber:"+invertsTotal+"\tCost:"+d.getCurrency()+invertsTotalPrice);
        tvCoral.setText("Coral \tNumber:"+coralTotal+"\tCost:"+d.getCurrency()+coralTotalPrice);

        linearLayout.addView(tvFish);
        linearLayout.addView(tvInverts);
        linearLayout.addView(tvCoral);
        builder.setView(linearLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog



                dialog.dismiss();
            }
        });


        android.app.AlertDialog alert = builder.create();
        alert.show();

    }
}
