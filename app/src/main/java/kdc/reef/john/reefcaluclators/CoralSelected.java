package kdc.reef.john.reefcaluclators;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

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
    private Uri photoChosen;
    private Drawable defaultDrawable;

    Boolean[] coralNumbers;
    private CoralProfile coralProfile;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coralprofile);

        //find coral profile
        coralNumbers= CoralList.getCoralNumbers();
        int index =0;
        while(index<coralNumbers.length){
            if(coralNumbers[index]){
                CoralList.setBool(index);
                break;
            }
            index++;
        }

        coralProfile = CoralList.getCoralProfileArrayList().get(index);


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
            coral1imageView.setImageURI(photoChosen);


        } else {
            coral1imageView = (ImageView) findViewById(R.id.coral1imageView);
            coral1imageView.setBackground(iconCoralId);
        }

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
                    coral1imageView.setImageURI(uri);
//                    String []projection={MediaStore.Images.Media.DATA};
//                    Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(projection[0]);
//                    URI filepath = cursor.getString(columnIndex);
//                    cursor.close();

                    coralProfile.setPhotoChosen(uri);
                    photoChosen = uri;

//                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filepath);
//
//                    coral1imageView.setImageBitmap(yourSelectedImage);

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
        //coralProfile.setPrice(Double.parseDouble(coral1PricetextView.getText().toString()));
        coralProfile.setPurchasedFrom(coral1PFtextView.getText().toString());
        //coralProfile.setSize(Double.parseDouble(coral1SizetextView.getText().toString()));
        //photo chosen is already set

    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        if (!photoChosen.equals("null")) {
//
//            coral1imageView.setImageDrawable(null);
//            Bitmap yourSelectedImage = BitmapFactory.decodeFile(photoChosen);
//
//
//            coral1imageView.setImageBitmap(yourSelectedImage);
//        }
//    }
}
