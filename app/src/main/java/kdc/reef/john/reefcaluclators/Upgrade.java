package kdc.reef.john.reefcaluclators;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class Upgrade extends AppCompatActivity implements BillingProcessor.IBillingHandler{

    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        bp = new BillingProcessor(this, null, this);
    }

    public void doUpgradeClick(View view){
        bp.purchase(Upgrade.this, "android.test.purchased");
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Toast.makeText(this, "You made the purchase. Thank you!", Toast.LENGTH_SHORT).show();
        ChangeDefaults.purchasedUpgrade = true;
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
