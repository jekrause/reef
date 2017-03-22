package kdc.reef.john.reefcaluclators;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CoralSelected extends AppCompatActivity {
    ImageView coral1imageView;
    EditText coral1NametextView;
    EditText coral1PricetextView;
    EditText coral1DPtextView;
    EditText coral1PFtextView;
    EditText coral1SizetextView;

    private static final int SELECTED_PICTURE =1;

    private String name;
    private String datePurchased;
    private Drawable iconCoralId;
    private double price;
    private double size;
    private String seller;
    private String photoChosen = "null";
    private Drawable defaultDrawable;

    Boolean[] coralNumbers;
    private CoralProfile coralProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coral1);

        //find coral profile
        coralNumbers= CoralList.getCoralNumbers();
        int i =0;
        while(i<coralNumbers.length){
            if(coralNumbers[i])break;
            i++;
        }

        coralProfile = CoralList.getCoralProfileArrayList().get(i);


        name = coralProfile.getName();

        datePurchased = coralProfile.getDatePurchased();
        iconCoralId = getApplicationContext().getResources().getDrawable(coralProfile.getIconCoralId());
        price = coralProfile.getPrice();
        size = coralProfile.getSize();
        seller = coralProfile.getPurchasedFrom();
        photoChosen = coralProfile.getPhotoChosen();



        if (!photoChosen.equals("null")) {
            Bitmap yourSelectedImage = BitmapFactory.decodeFile(photoChosen);
            coral1imageView = (ImageView) findViewById(R.id.coral1imageView);
            coral1imageView.setImageBitmap(yourSelectedImage);


            //coral1imageView.setImageDrawable(d);
            //coral1imageView.setImageBitmap(yourSelectedImage);

        } else {
            coral1imageView = (ImageView) findViewById(R.id.coral1imageView);
            coral1imageView.setBackground(iconCoralId);
        }

//        coral1imageView = (ImageView) findViewById(R.id.coral1imageView);
//        coral1imageView.setImageResource(iconCoralId);

        coral1NametextView = (EditText) findViewById(R.id.coral1NametextView);
        coral1NametextView.setText(name);

        coral1PricetextView = (EditText) findViewById(R.id.coral1CosttextView);
        coral1PricetextView.setText("$" + Double.toString(price));

        coral1DPtextView = (EditText) findViewById(R.id.coral1DPtextView);
        coral1DPtextView.setText(datePurchased);

        coral1PFtextView = (EditText) findViewById(R.id.coral1PFtextView);
        coral1PFtextView.setText(seller);

        coral1SizetextView = (EditText) findViewById(R.id.coral1SizetextView);
        coral1SizetextView.setText(Double.toString(size));


    }


    public void newImage(View view){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECTED_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        switch (requestCode){
            case SELECTED_PICTURE:
                if(resultCode==RESULT_OK){
                    Uri uri = data.getData();
                    String []projection={MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filepath = cursor.getString(columnIndex);
                    cursor.close();

                    coralProfile.setPhotoChosen(filepath);

                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filepath);
//                    Drawable d = new BitmapDrawable(yourSelectedImage);



                    coral1imageView.setImageBitmap(yourSelectedImage);
                }
                break;
        }
    }

    @Override
    public void onBackPressed(){
        Gson gson = new Gson();
        String str = gson.toJson(CoralList.getCoralProfileArrayList());
        //not sure on mode
        File file = new File(getApplicationContext().getDir("data",0).getPath());
        try{
            FileWriter fw = new FileWriter(file);
            fw.write(str);
            Toast.makeText(this,"Data being saved", Toast.LENGTH_LONG).show();
        }catch(Exception ex){

        }

    }
}
