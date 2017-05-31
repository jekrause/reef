package kdc.reef.john.reefcaluclators;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by John on 5/31/2017.
 */
@Singleton
public class Preferences {

    private SharedPreferences sharedPreferences;
    private  String currency = "$";
    private  String curPos ="0";
    private  String measurement = "\"";
    private  String measPos ="0";
    private  String purchasedUpgrade = "false";

    @Inject
    public Preferences(Context context){
        sharedPreferences = context.getSharedPreferences("Preferences", 0);
    }

    public  void setCurrency(String currency) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.currency,currency);
        edit.commit();
    }

    public  void setCurPos(int curPos) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(this.curPos, curPos);
        edit.commit();
    }

    public void setMeasurement(String measurement) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.measurement, measurement);
        edit.commit();
    }

    public void setMeasPos(int measPos) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(this.measPos, measPos);
        edit.commit();
    }

    public void setPurchasedUpgrade(boolean purchasedUpgrade) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(this.purchasedUpgrade, purchasedUpgrade);
        edit.commit();
    }

    public String getCurrency() {
        return sharedPreferences.getString(currency, "$");
    }

    public int getCurPos() {
        return sharedPreferences.getInt(curPos, 0);
    }

    public String getMeasurement() {
        return sharedPreferences.getString(measurement, "\"");
    }

    public int getMeasPos() {
        return sharedPreferences.getInt(measPos, 0);
    }

    public boolean isPurchasedUpgrade() {
        return sharedPreferences.getBoolean(purchasedUpgrade, false);
    }

}
