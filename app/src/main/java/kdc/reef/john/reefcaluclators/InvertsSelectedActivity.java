package kdc.reef.john.reefcaluclators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InvertsSelectedActivity extends AppCompatActivity {

    ImageView inverts1imageView;
    //ImageView temporaryScrollImage;
    ImageList temporaryScrollImageList;
    EditText inverts1NametextView;
    EditText inverts1PricetextView;
    EditText inverts1DPtextView;
    EditText inverts1PFtextView;
    EditText inverts1SizetextView;
    TextView costLabel;
    TextView sizeLabel;
    TextView tv;
    TwoWayView twv;

    private static final int SELECTED_PICTURE =1;
    private static final int SELECTED_PICTURE_SCROLL = 2;

    private String name;
    private String datePurchased;
    private Drawable invertsCoralId;
    private double price;
    private double size;
    private String seller;
    private String photoChosen;
    private Drawable defaultDrawable;

    static private int numberOfImages = 0;
    static private int MAX_NUMBER_OF_IMAGES = 3 ;

    UsersAdapter adapter;

    List <Boolean> invertsNumbers;
    List<ImageList> imageListList;

    private InvertsProfile invertsProfile;
    private int index;
    private String tempURI = "drawable";
    Defaults d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inverts_selected);

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

        }catch(Exception ex){
            d = new Defaults();
        }

        if(d.isPurchasedUpgrade()){
            MAX_NUMBER_OF_IMAGES = Integer.MAX_VALUE;
        }

        //find coral profile
        invertsNumbers = InvertsListActivity.getInvertsNumbers();
        int index =0;
        while(index< invertsNumbers.size()){
            if(invertsNumbers.get(index)){
                invertsNumbers.set(index,false);
                break;
            }
            index++;
        }

        invertsProfile = InvertsListActivity.getInvertsProfileArrayList().get(index);

        imageListList = invertsProfile.getImageArrayList();
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

        name = invertsProfile.getName();
        datePurchased = invertsProfile.getDatePurchased();
        invertsCoralId = getApplicationContext().getResources().getDrawable(invertsProfile.getIconCoralId());
        price = invertsProfile.getPrice();
        size = invertsProfile.getSize();
        seller = invertsProfile.getPurchasedFrom();
        photoChosen = invertsProfile.getPhotoChosen();

        if (photoChosen!=null) {
            inverts1imageView = (ImageView) findViewById(R.id.coral1imageView);
            inverts1imageView.setImageURI(Uri.parse(photoChosen));


        } else {
            inverts1imageView = (ImageView) findViewById(R.id.coral1imageView);
            inverts1imageView.setBackground(invertsCoralId);
        }

        inverts1NametextView = (EditText) findViewById(R.id.coral1NametextView);

        inverts1NametextView.setText(name);

        inverts1PricetextView = (EditText) findViewById(R.id.coral1CosttextView);
        inverts1PricetextView.setText(String.format("%.2f",price));

        inverts1DPtextView = (EditText) findViewById(R.id.coral1DPtextView);
        inverts1DPtextView.setText(datePurchased);

        inverts1PFtextView = (EditText) findViewById(R.id.coral1PFtextView);
        inverts1PFtextView.setText(seller);

        inverts1SizetextView = (EditText) findViewById(R.id.coral1SizetextView);
        inverts1SizetextView.setText(Double.toString(size));

        costLabel = (TextView) findViewById(R.id.sizeLabel);
        costLabel.setText(ChangeDefaultsActivity.measurement);

        sizeLabel = (TextView) findViewById(R.id.costLabel);
        sizeLabel.setText(ChangeDefaultsActivity.currency);
    }

    public void newImageInList(View view){
        if(numberOfImages<MAX_NUMBER_OF_IMAGES){
            imageListList.add(new ImageList("default", todaysDate()));
            numberOfImages++;
            updateCounter();
            adapter.notifyDataSetChanged();
        }
    }

    private void updateCounter(){
        if(MAX_NUMBER_OF_IMAGES==3){
            tv.setText(numberOfImages+ " of 3");
        }
        else{
            tv.setText(numberOfImages+"");
        }

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
                    inverts1imageView.setBackground(null);
                    //set new image
                    inverts1imageView.setImageURI(Uri.parse(uri));

                    invertsProfile.setPhotoChosen(uri);
                    photoChosen = uri;
                }
                break;
            case SELECTED_PICTURE_SCROLL:
                if(resultCode==RESULT_OK){
                    tempURI = data.getData().toString();
                    temporaryScrollImageList.set(tempURI);
                    tempURI = "default";
                    adapter.notifyDataSetChanged();
                }
                break;

        }
    }

    @Override
    public void onBackPressed(){
        Gson gson = new Gson();
        saveFields();
        String str = gson.toJson(CoralListActivity.getCoralProfileArrayList());

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
            FileOutputStream fileout = openFileOutput("invertsData.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(str);
            outputWriter.close();
        }catch(Exception ex){
            Toast.makeText(this, "Failed ..", Toast.LENGTH_SHORT).show();
        }
        super.onBackPressed();

    }

    public void saveFields(){
        String temp;
        temp = inverts1DPtextView.getText().toString();
        if(temp.length()==0){
            invertsProfile.setDatePurchased(todaysDate());
        }
        else {
            invertsProfile.setDatePurchased(inverts1DPtextView.getText().toString());
        }

        temp = inverts1NametextView.getText().toString();
        if(temp.length()==0){
            invertsProfile.setName("Inverts Name");
        }
        else{
            invertsProfile.setName(inverts1NametextView.getText().toString());
        }

        temp = inverts1PricetextView.getText().toString();
        if(temp.length()>0){
            invertsProfile.setPrice(Double.parseDouble(temp));
        }
        else{
            invertsProfile.setPrice(0);
        }

        temp = inverts1PFtextView.getText().toString();
        if(temp.length()==0){
            invertsProfile.setPurchasedFrom("Seller");
        }
        else{
            invertsProfile.setPurchasedFrom(inverts1PFtextView.getText().toString());
        }

        temp = inverts1SizetextView.getText().toString();
        if(temp.equals("")){
            invertsProfile.setSize(0);
        }
        else{
            invertsProfile.setSize(Double.parseDouble(temp));
        }

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
        public View getView(final int position, View convertView, ViewGroup parent) {
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(InvertsSelectedActivity.this);
                    builder.setTitle("MENU");
                    builder.setMessage("Photo Options Menu");
                    builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            Log.d("MyApp","yes");
                            imageListList.remove(user);
                            //coralProfileArrayList.remove(user);
                            numberOfImages--;
                            updateCounter();
                            //refreshListings();
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("VIEW", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("MyApp","view_full_screen");
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            if(imageListList.get(position).get().equals("default")){
                                //TODO
                            }
                            else {
                                intent.setDataAndType(Uri.parse(imageListList.get(position).get()), "image/*");
                                startActivity(intent);
                            }
                        }
                    });
                    builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("MyApp","cancel");
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
            String tempDate = user.getDateOfImage();
            if(tempDate == null){
                edt.setText("NO DATE");
            }
            else{
                edt.setText(user.getDateOfImage());
            }
            tempImage.setImageDrawable(null);
            tempImage.setImageDrawable(getApplicationContext().getDrawable(R.drawable.invert));

            if(user.get()!=null && !user.get().equals("default")){
                Glide.with(this.getContext()).load(Uri.parse(user.get())).into(tempImage);
                adapter.notifyDataSetChanged();
            }
            // Return the completed view to render on screen
            return convertView;
        }
    }

    //click listeners
    private void registerClickCallback(){

        twv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InvertsSelectedActivity.this);

                builder.setTitle("Confirm Deletion");
                builder.setMessage("Are you sure you want to delete this profile image?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        imageListList.remove(position);
                        //fishProfileArrayList.remove(position);
                        FishListActivity.getFishProfileArrayList().remove(position);
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
