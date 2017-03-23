package kdc.reef.john.reefcaluclators;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class CoralList extends AppCompatActivity {


    public static  List<CoralProfile> coralProfileArrayList = new ArrayList<>();
    ArrayAdapter<CoralProfile> coralArrayAdapter;

    public static Boolean[] coralNumbers = new Boolean[50];
    ListView list;
    private String name;
    private String datePurchased;
    private int iconCoralId;
    private double price;
    private double size;
    private String purchasedFrom;
    private CoralProfile coralProfile;
    private String photoChosen;
    static boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coral);


        if(firstTime){
            for(int i=coralProfileArrayList.size(); i<=50; i++){
                coralProfileArrayList.add(new CoralProfile("CoralNumber #"+i, "date", 0.0));
            }
            firstTime=false;
        }
        else{

        }
        populateListView();

        registerClickCallback();

    }


    @Override
    protected void onResume() {
        super.onResume();
        Gson gson = new Gson();
        String datax = "";
        try{
            FileInputStream filein = openFileInput("coralData.txt");
            InputStreamReader isr = new InputStreamReader ( filein ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;


            isr.close ( ) ;
            buffreader.close();
            filein.close();

           filein.close();
            if(!readString.isEmpty()){
                //Log.d("MyApp",readString);
                coralProfileArrayList = gson.fromJson(readString,  new TypeToken<Collection<CoralProfile>>(){}.getType());
                Toast.makeText(this, "loaded ", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "No data to load ", Toast.LENGTH_SHORT).show();
            }

        }catch(Exception ex){
            Toast.makeText(this, "Failed ", Toast.LENGTH_SHORT).show();
        }
        coralArrayAdapter.notifyDataSetChanged();
    }




    public void populateListView(){

        coralArrayAdapter = new MyListAdapter();
        list = (ListView) findViewById(R.id.coralListView);

        list.setAdapter(coralArrayAdapter);

    }

    public class MyListAdapter extends ArrayAdapter<CoralProfile>{
        public MyListAdapter(){
            super(CoralList.this, R.layout.coral_view, coralProfileArrayList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //make sure we have a view to work with
            View coralView = convertView;
            if(coralView == null){
                coralView = getLayoutInflater().inflate(R.layout.coral_view,parent,false);
            }

            System.out.println(coralProfileArrayList.get(position));
            //find coralProfile

            CoralProfile currentCoralProfile = coralProfileArrayList.get(position);

            if(currentCoralProfile == null){

            }
            else{
                name = currentCoralProfile.getName();
                datePurchased = currentCoralProfile.getDatePurchased();
                iconCoralId = currentCoralProfile.getIconCoralId();
                price = currentCoralProfile.getPrice();
                size = currentCoralProfile.getSize();
                purchasedFrom = currentCoralProfile.getPurchasedFrom();
                photoChosen = currentCoralProfile.getPhotoChosen();

                //fill the view
                ImageView imageView = (ImageView) coralView.findViewById(R.id.itemIcon);

//                if(photoChosen!=null){
//                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(photoChosen);
//                    imageView.setImageBitmap(yourSelectedImage);
//                }
//                else{
//                    //imageView.setBackground(iconCoralId);
//                    imageView.setBackground(getApplicationContext().getDrawable(iconCoralId));
//                }
                imageView.setBackground(getApplicationContext().getDrawable(iconCoralId));

                //Image
                ImageView imageView1 = (ImageView) coralView.findViewById(R.id.itemIcon);
                //Name
                TextView nameText = (TextView) coralView.findViewById(R.id.coralViewNameText);
                nameText.setText(name);

                //Date
                TextView dateText = (TextView) coralView.findViewById(R.id.coralViewDateText);
                dateText.setText("Date: " + datePurchased);

                //price
                TextView priceText = (TextView) coralView.findViewById(R.id.coralViewPriceText);
                priceText.setText("$" + Double.toString(price));
            }

            return coralView;

        }
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.coralListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                coralNumbers[position]=true;
                Intent i = new Intent(CoralList.this, CoralSelected.class);
                startActivity(i);
            }
        });
    }


    //Getters
    public static Boolean[] getCoralNumbers() {
        return coralNumbers;
    }
    public static List<CoralProfile> getCoralProfileArrayList() {
        return coralProfileArrayList;
    }



}
