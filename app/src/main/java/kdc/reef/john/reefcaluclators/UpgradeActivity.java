package kdc.reef.john.reefcaluclators;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class UpgradeActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler{

    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

//        bp = new BillingProcessor(this, null, this);

        bp = new BillingProcessor(this,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArLcLXZmJfZAJEWi6vynifI2uT5OTLrBqzsNgM4aijqsFNG9OHPKaJrgi9PSVUiLPaI2aRKtEnrGBRG9QnoBhaIjq3f8Fpi11Cvh40OAOV3zDdeTI93pyD60zZU8SLq1q1U0sQUjDDCp9r5yAg38XWo9AoAFd7h2S224STWLG6npwPZhkMpMg6ZyLGbVbKPwmNoMMEqug7Zxj7Pq3NRAOBYwv9XT2y+NKnOw5W7fJ53J8edaMHyirfHKBDQ2eJWQwzfk0a1LJlcEhIvOr+LtljgSVtaqy5IF7orFCcKJiBd+mbpMAn/pQ2N6OpsH2SVDHHi1s3Fb/8bvB1jjVFAMkAQIDAQAB",this);
    }

    public void doUpgradeClick(View view){
        bp.purchase(UpgradeActivity.this, "unlock_all_features");
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Toast.makeText(this, "You made the purchase. Thank you!", Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        Defaults d;

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
        d.setPurchasedUpgrade(true);

        String temp = gson.toJson(d);
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
            FileOutputStream fileout = openFileOutput("defaultData.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(temp);
            outputWriter.close();
        }catch(Exception ex){
            Toast.makeText(this, "Failed ..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(bp.handleActivityResult(requestCode,resultCode,data))
            super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onDestroy(){
        if(bp!=null){
            bp.release();
            super.onDestroy();
        }
    }
}
