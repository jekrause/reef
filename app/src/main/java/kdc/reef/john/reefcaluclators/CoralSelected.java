package kdc.reef.john.reefcaluclators;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.lucasr.twowayview.TwoWayView;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static kdc.reef.john.reefcaluclators.CoralList.coralProfileArrayList;

public class CoralSelected extends AppCompatActivity {
    ImageView coral1imageView;
    //ImageView temporaryScrollImage;
    ImageList temporaryScrollImageList;
    EditText coral1NametextView;
    EditText coral1PricetextView;
    EditText coral1DPtextView;
    EditText coral1PFtextView;
    EditText coral1SizetextView;
    TextView tv;
    TwoWayView twv;

    private static final int SELECTED_PICTURE =1;
    private static final int SELECTED_PICTURE_SCROLL = 2;

    private String name;
    private String datePurchased;
    private Drawable iconCoralId;
    private double price;
    private double size;
    private String seller;
    private String photoChosen;
    private Drawable defaultDrawable;

    static private int numberOfImages = 0;
    final static private int MAX_NUMBER_OF_IMAGES = 3 ;

    UsersAdapter adapter;

    List <Boolean> coralNumbers;
    List<ImageList> imageListList;
    private CoralProfile coralProfile;
    private int index;
    private String tempURI = "drawable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MyApp","OnCreate in CoralSelected");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coralprofile_test);

        //find coral profile
        coralNumbers= CoralList.getCoralNumbers();
        Log.d("myApp",coralNumbers.size()+"");
        int index =0;
        while(index<coralNumbers.size()){
            if(coralNumbers.get(index)){
                //coralNumbers[index] = false;
                coralNumbers.set(index,false);
                Log.d("myApp", index+"");
                break;
            }
            index++;
        }

        coralProfile = CoralList.getCoralProfileArrayList().get(index);

        imageListList = coralProfile.getImageArrayList();
        tv = (TextView) findViewById(R.id.coralProfileImageCounter);

        //set image number count
        numberOfImages = imageListList.size();
        updateCounter();

        // Create the adapter to convert the array to views
        adapter = new UsersAdapter(this, imageListList);
        // Attach the adapter to a ListView
        twv = (TwoWayView) findViewById(R.id.lvItems);
        twv.setAdapter(adapter);
        //click listeners
        registerClickCallback();


        name = coralProfile.getName();

        datePurchased = coralProfile.getDatePurchased();
        iconCoralId = getApplicationContext().getResources().getDrawable(coralProfile.getIconCoralId());
        price = coralProfile.getPrice();
        size = coralProfile.getSize();
        seller = coralProfile.getPurchasedFrom();
        photoChosen = coralProfile.getPhotoChosen();



        if (photoChosen!=null) {
            //Bitmap yourSelectedImage = BitmapFactory.decodeFile(photoChosen);
            coral1imageView = (ImageView) findViewById(R.id.coral1imageView);
            coral1imageView.setImageURI(Uri.parse(photoChosen));


        } else {
            coral1imageView = (ImageView) findViewById(R.id.coral1imageView);
            coral1imageView.setBackground(iconCoralId);
        }

        coral1NametextView = (EditText) findViewById(R.id.coral1NametextView);

        coral1NametextView.setText(name);

        coral1PricetextView = (EditText) findViewById(R.id.coral1CosttextView);
        coral1PricetextView.setText("$" + String.format("%.2f",price));

        coral1DPtextView = (EditText) findViewById(R.id.coral1DPtextView);
        coral1DPtextView.setText(datePurchased);

        coral1PFtextView = (EditText) findViewById(R.id.coral1PFtextView);
        coral1PFtextView.setText(seller);

        coral1SizetextView = (EditText) findViewById(R.id.coral1SizetextView);
        coral1SizetextView.setText(Double.toString(size));
    }

    public void newImageInList(View view){
        if(numberOfImages<MAX_NUMBER_OF_IMAGES){
            //LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.pictureLinear);
            //imageListList.add( new ImageList("drawable://"+R.drawable.coral));
            imageListList.add(new ImageList("default", todaysDate()));
            Log.d("MyApp", "drawable://"+R.drawable.coral);
            numberOfImages++;
            updateCounter();
            adapter.notifyDataSetChanged();
        }


    }

    private void updateCounter(){
        tv.setText(numberOfImages+ " of 3");
        if(numberOfImages==MAX_NUMBER_OF_IMAGES){
            tv.setTextColor(Color.RED);
        }
        else{
            tv.setTextColor(Color.WHITE);
        }
    }

    public void newImage(View view){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECTED_PICTURE);
    }

//    public void newImageScroll(View view, ){
//        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(i, SELECTED_PICTURE_SCROLL);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case SELECTED_PICTURE:
                if(resultCode==RESULT_OK){
                    String uri = data.getData().toString();
                    //delete old image
                    coral1imageView.setBackground(null);
                    //set new image
                    coral1imageView.setImageURI(Uri.parse(uri));

                    coralProfile.setPhotoChosen(uri);
                    photoChosen = uri;
                }
                break;
            case SELECTED_PICTURE_SCROLL:
                if(resultCode==RESULT_OK){
                    tempURI = data.getData().toString();
                    temporaryScrollImageList.set(tempURI);
                    tempURI = "default";
//                    Log.d("MyApp", tempURI);
//                    temporaryScrollImage.setImageURI(null);
//                    temporaryScrollImage.setImageURI(Uri.parse(tempURI));
                    adapter.notifyDataSetChanged();
                }
                break;

        }
    }

    @Override
    public void onBackPressed(){
        Gson gson = new Gson();
        saveFields();
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

    public void saveFields(){
        coralProfile.setDatePurchased(coral1DPtextView.getText().toString());
        coralProfile.setName(coral1NametextView.getText().toString());
        coralProfile.setPrice(Double.parseDouble(coral1PricetextView.getText().toString().substring(1)));
        coralProfile.setPurchasedFrom(coral1PFtextView.getText().toString());
        coralProfile.setSize(Double.parseDouble(coral1SizetextView.getText().toString()));
        //photo chosen is already set

        for(int i=0; i<numberOfImages; i++){
            ImageList o =  (ImageList) twv.getItemAtPosition(i);

            EditText ed = (EditText) twv.getChildAt(i).findViewById(R.id.scrollImageDate);
            o.setDateOfImage(ed.getText().toString() );
        }
    }

    public class UsersAdapter extends ArrayAdapter<ImageList> {
        public UsersAdapter(Context context, List<ImageList> corals) {
            super(context, 0, corals);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final ImageList user = imageListList.get(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item_1, parent, false);
            }
            // Lookup view for data population
            final ImageView tempImage = (ImageView) convertView.findViewById(R.id.imageSimp);
            tempImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                    temporaryScrollImageList = user;
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SELECTED_PICTURE_SCROLL);
                }
            });

            tempImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(CoralSelected.this, "long click registered", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(CoralSelected.this);

                    builder.setTitle("Confirm Deletion");
                    builder.setMessage("Are you sure you want to delete this profile image?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            Log.d("MyApp","yes");
                            imageListList.remove(user);
                            coralProfileArrayList.remove(user);
                            numberOfImages--;
                            updateCounter();
                            //refreshListings();
                            adapter.notifyDataSetChanged();


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

            EditText edt = (EditText) convertView.findViewById(R.id.scrollImageDate);
            // Populate the data into the template view using the data object
//            if(user.get()!=null){
//                tempImage.setImageURI(null);
//                tempImage.setImageURI(Uri.parse(user.get()));
//            }
            String tempDate = user.getDateOfImage();
            if(tempDate == null){
                edt.setText("NO DATE");
            }
            else{
                edt.setText(user.getDateOfImage());
            }

            //else{
                //tempImage.setImageDrawable(null);
                tempImage.setImageDrawable(null);
                tempImage.setImageDrawable(getApplicationContext().getDrawable(R.drawable.coral));



            if(user.get()!=null && !user.get().equals("default")){
                //tempImage.setImageDrawable(null);
                //tempImage.invalidate();
                //tempImage.setImageURI(null); below
                Glide.with(this.getContext()).load(Uri.parse(user.get())).into(tempImage);

                //tempImage.setImageURI(Uri.parse(user.get())); //below
                //tempImage.invalidate(); this line was used when it was working before Glide
                adapter.notifyDataSetChanged();
            }
            //}

            // Return the completed view to render on screen
            return convertView;
        }
    }

    //click listeners
    private void registerClickCallback(){

        twv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(CoralSelected.this, "long click registered", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(CoralSelected.this);

                builder.setTitle("Confirm Deletion");
                builder.setMessage("Are you sure you want to delete this profile image?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Log.d("MyApp","yes");
                        imageListList.remove(position);
                        coralProfileArrayList.remove(position);
                        numberOfImages--;
                        updateCounter();
                        //refreshListings();
                        adapter.notifyDataSetChanged();


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

    private String todaysDate(){
        String x = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
        return x;
    }

}
