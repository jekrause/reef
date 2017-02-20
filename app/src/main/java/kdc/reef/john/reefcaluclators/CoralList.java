package kdc.reef.john.reefcaluclators;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class CoralList extends AppCompatActivity {
    private  List<CoralProfile> coralProfileArrayList;

    SharedPreferences mPrefs;
    ArrayAdapter<CoralProfile> coralArrayAdapter;
    ListView list;
    private String name;
    private String datePurchased;
    private int iconCoralId;
    private double price;
    private double size;
    private String purchasedFrom;
    private CoralProfile coralProfile;
    private String photoChosen;
    int x =1;
    static boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coral);
        coralProfileArrayList = new ArrayList<>();

        populateCoralList(x);
        populateListView();
        if(!firstTime){
            loadCoral();
        }
        firstTime=false;


        registerClickCallback();

    }


    @Override
    protected void onResume() {
        super.onResume();
        loadCoral();
        System.out.println("onResume");
        coralArrayAdapter.notifyDataSetChanged();
    }


    public void loadCoral(){
        try {
            FileInputStream fis = getApplicationContext().openFileInput("coralSaved1");
            ObjectInputStream is = new ObjectInputStream(fis);
            CoralProfile coralProfileInput = (CoralProfile) is.readObject();
            is.close();
            fis.close();
            coralProfile = coralProfileInput;
            coralProfileArrayList.remove(0);
            coralProfileArrayList.add(0, coralProfile);
            x++;

        } catch (Exception ex) {

        }
    }



    public void populateCoralList(int x){
        for (int i = x; i <=50; i++){
            CoralProfile coralProfileAuto = new CoralProfile("CoralProfile #"+i,"1/1/2000",R.drawable.coral,100.00);
            coralProfileArrayList.add(coralProfileAuto);
        }
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
            else {
                name = currentCoralProfile.getName();
                datePurchased = currentCoralProfile.getDatePurchased();
                iconCoralId = currentCoralProfile.getIconCoralId();
                price = currentCoralProfile.getPrice();
                size = currentCoralProfile.getSize();
                purchasedFrom = currentCoralProfile.getPurchasedFrom();
                photoChosen = currentCoralProfile.getPhotoChosen();

                //fill the view
                ImageView imageView = (ImageView) coralView.findViewById(R.id.itemIcon);

                if(photoChosen!=null){
                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(photoChosen);
                    imageView.setImageBitmap(yourSelectedImage);
                }
                else{
                    imageView.setImageResource(currentCoralProfile.getIconCoralId());
                }


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
                CoralProfile clickedCoralProfile = coralProfileArrayList.get(position);
                if(position == 0){

                    Intent i = new Intent(CoralList.this, CoralSelected.class);
                    i.putExtra("coralObject", clickedCoralProfile);

                    startActivity(i);

                }
            }
        });
    }


}
