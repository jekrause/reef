package kdc.reef.john.reefcaluclators;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.TotalCaptureResult;
import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CoralList extends AppCompatActivity {


    public static  List<CoralProfile> coralProfileArrayList = new ArrayList<>();
    ArrayAdapter<CoralProfile> coralArrayAdapter;
    public static List<Boolean> coralNumbers = new ArrayList<>();

    ListView list;
    private String name;
    private String datePurchased;
    private int iconCoralId;
    private double price;
    private double size;
    private String purchasedFrom;
    private CoralProfile coralProfile;
    private String photoChosen;

    final static int maxNumber = 10;
    static int curNumber =0;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MyApp","OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coral);
        tv = (TextView) findViewById(R.id.textView4);

        refreshListings();
        populateListView();
        registerClickCallback();

        updateCounter();
    }

    /*
    add new profile to list
     */
    public void addButton(View view){
        if(curNumber<maxNumber){
            curNumber++;
            coralProfileArrayList.add(new CoralProfile("New Coral",todaysDate(),0.0));
            coralNumbers.add(Boolean.FALSE);
            coralArrayAdapter.notifyDataSetChanged();
            //refreshListings();
            populateListView();


            updateCounter();

        }
        else{
            Toast.makeText(this, "No more available. Give me your money!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {

        updateCounter();
        coralArrayAdapter.notifyDataSetChanged();
        refreshListings();
        super.onResume();


    }

    private void updateCounter(){
        tv.setText(curNumber+ " of 10");
        if(curNumber==maxNumber){
            tv.setTextColor(Color.RED);
        }
        else{
            tv.setTextColor(Color.WHITE);
        }
    }

    private String todaysDate(){
        String x = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
        return x;
    }
    public void populateListView(){

        coralArrayAdapter = new MyListAdapter();
        list = (ListView) findViewById(R.id.coralListView);
        curNumber = coralProfileArrayList.size();

        list.setCacheColorHint(Color.TRANSPARENT); // not sure if this is required for you.
        list.setFastScrollEnabled(true);
        list.setScrollingCacheEnabled(false);

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
                //imageView.setBackground(null);
                Glide.with(this.getContext()).load(R.drawable.coral).into(imageView);
                //imageView.setBackground(getApplicationContext().getDrawable(iconCoralId));

                if(photoChosen!=null){
                    Uri uri = Uri.parse(photoChosen);
//                    //Bitmap yourSelectedImage = BitmapFactory.decodeFile(photoChosen);
                    imageView.setBackground(null);
//                    imageView.setImageURI(Uri.parse(photoChosen));
                    Log.d("myApp","glide, bb");
                    Glide.with(this.getContext()).load(uri).into(imageView);
                }
                else{
                    //imageView.setImageURI(null);
                    //imageView.setBackground(getApplicationContext().getDrawable(iconCoralId));
                }
                //Name
                TextView nameText = (TextView) coralView.findViewById(R.id.coralViewNameText);
                nameText.setText(name);

                //Date
                TextView dateText = (TextView) coralView.findViewById(R.id.coralViewDateText);
                dateText.setText("Date: " + datePurchased);

                //price
                TextView priceText = (TextView) coralView.findViewById(R.id.coralViewPriceText);
                priceText.setText("$" + String.format("%.2f",price));
            }

            return coralView;

        }
    }

    private void refreshListings(){
        if(coralArrayAdapter!=null){
            coralArrayAdapter.notifyDataSetChanged();
        }
        else{
            Gson gson = new Gson();
            //String datax = "";
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
                    Log.d("MyApp","start");
                    coralProfileArrayList = gson.fromJson(readString,  new TypeToken<Collection<CoralProfile>>(){}.getType());
                    for(CoralProfile cp : coralProfileArrayList){
                        coralNumbers.add(Boolean.FALSE);
                    }
                    Log.d("MyApp","read in " + readString);
                    //coralArrayAdapter.notifyDataSetChanged();
                    //populateListView();

                    populateListView();
                    Log.d("MyApp","finished");
                }
                else{
                    Log.d("MyApp", "readString is empty");
                }

            }catch(Exception ex){
                Log.d("MyApp","blown up");
                Log.d("MyApp", ex.getLocalizedMessage());

                Toast.makeText(this, ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }



    }


    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.coralListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                coralNumbers.set(position, true);
                Intent i = new Intent(CoralList.this, CoralSelected.class);
                startActivity(i);
            }
        });

        //long click listener
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CoralList.this);

                builder.setTitle("Confirm Deletion");
                builder.setMessage("Are you sure you want to delete this profile?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Log.d("MyApp","yes");
                        coralNumbers.remove(position);
                        coralProfileArrayList.remove(position);
                        curNumber--;
                        updateCounter();
                        refreshListings();


                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MyApp","no");

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });
    }

    //Getters
    public static List<Boolean> getCoralNumbers() {
        return coralNumbers;
    }
    public static List<CoralProfile> getCoralProfileArrayList() {
        return coralProfileArrayList;
    }

    @Override
    public void onBackPressed(){
        Gson gson = new Gson();
        String str = gson.toJson(CoralList.getCoralProfileArrayList());

        try{
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 80085);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique

                return;
            }
            FileOutputStream fileout = openFileOutput("coralData.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(str);
            outputWriter.close();
        }catch(Exception ex){
            Toast.makeText(this, "Failed ..", Toast.LENGTH_SHORT).show();
        }
        super.onBackPressed();

    }

}
