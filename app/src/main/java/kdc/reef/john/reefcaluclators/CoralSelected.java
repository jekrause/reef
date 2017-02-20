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

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    private int iconCoralId;
    private double price;
    private double size;
    private String seller;
    private String photoChosen = "null";
    private Drawable defaultDrawable;

    private CoralProfile coralProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coral1);
        defaultDrawable = getResources().getDrawable(R.drawable.coral);

            Intent i = getIntent();
            coralProfile = (CoralProfile) i.getSerializableExtra("coralObject");

        if(coralProfile !=null) {
            try {
                FileInputStream fis = getApplicationContext().openFileInput("coralSaved1");
                ObjectInputStream is = new ObjectInputStream(fis);
                CoralProfile coralProfileInput = (CoralProfile) is.readObject();
                is.close();
                fis.close();
                coralProfile = coralProfileInput;

                name = coralProfile.getName();

                datePurchased = coralProfile.getDatePurchased();
                iconCoralId = coralProfile.getIconCoralId();
                price = coralProfile.getPrice();
                size = coralProfile.getSize();
                seller = coralProfile.getPurchasedFrom();
                photoChosen = coralProfile.getPhotoChosen();
            } catch (Exception ex) {

            }
        }






        if(!photoChosen.equals("null")){
            Bitmap yourSelectedImage = BitmapFactory.decodeFile(photoChosen);
            coral1imageView = (ImageView) findViewById(R.id.coral1imageView);
            coral1imageView.setImageBitmap(yourSelectedImage);


            //coral1imageView.setImageDrawable(d);
            //coral1imageView.setImageBitmap(yourSelectedImage);

        }
        else {
            coral1imageView = (ImageView) findViewById(R.id.coral1imageView);
            coral1imageView.setImageResource(iconCoralId);
        }

//        coral1imageView = (ImageView) findViewById(R.id.coral1imageView);
//        coral1imageView.setImageResource(iconCoralId);

        coral1NametextView = (EditText) findViewById(R.id.coral1NametextView);
        coral1NametextView.setText(name);

        coral1PricetextView = (EditText) findViewById(R.id.coral1CosttextView);
        coral1PricetextView.setText("$"+Double.toString(price));

        coral1DPtextView = (EditText) findViewById(R.id.coral1DPtextView);
        coral1DPtextView.setText(datePurchased);

        coral1PFtextView = (EditText) findViewById(R.id.coral1PFtextView);
        coral1PFtextView.setText(seller);

        coral1SizetextView = (EditText) findViewById(R.id.coral1SizetextView);
        coral1SizetextView.setText(Double.toString(size));


    }

    public void saveToFile(Context context){
        try {
            FileOutputStream fos = context.openFileOutput("coralSaved1", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(coralProfile);

        }catch (IOException ex){

        }

    }

    @Override
    public void onBackPressed() {
        //name
        if(!(coral1NametextView.getText().toString().equals(name))){
            coralProfile.setName(coral1NametextView.getText().toString());
        }
        //price
        if(!(coral1PricetextView.getText().toString().equals(price))){
            coralProfile.setPrice(Double.parseDouble(coral1PricetextView.getText().toString().substring(1,coral1PricetextView.getText().toString().length())));
        }
        //datePurchased
        if(!(coral1DPtextView.getText().toString().equals(datePurchased))){
            coralProfile.setDatePurchased(coral1DPtextView.getText().toString());
        }
        //seller
        if(!(coral1PFtextView.getText().toString().equals(seller))){
            coralProfile.setPurchasedFrom(coral1PFtextView.getText().toString());
        }
        //size
        if(!(coral1SizetextView.getText().toString().substring(1).equals(size))){
            coralProfile.setSize(Double.parseDouble(coral1SizetextView.getText().toString()));
        }
        saveToFile(getApplicationContext());

        super.onBackPressed();
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
}
