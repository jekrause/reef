package kdc.reef.john.reefcaluclators;

import android.app.Application;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 5/31/2017.
 */

public class Defaults implements Serializable{

    private String currency = "$";
    private int curPos =0;
    private String measurement = "\"";
    private int measPos =0;
    public static List<Integer> lsIssuedAlertCodes = new ArrayList<>();
    private boolean purchasedUpgrade = false;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getCurPos() {
        return curPos;
    }

    public void setCurPos(int curPos) {
        this.curPos = curPos;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public int getMeasPos() {
        return measPos;
    }

    public void setMeasPos(int measPos) {
        this.measPos = measPos;
    }

    public boolean isPurchasedUpgrade() {
        return purchasedUpgrade;
    }

    public void setPurchasedUpgrade(boolean purchasedUpgrade) {
        this.purchasedUpgrade = purchasedUpgrade;
    }
    /*
    used to find a unique code not in the list and returns the int it finds which gets assigned to the alert
     */
    public int addAndGetAlertCode(){
        //find the lowest available positive int
        int i;
        for(i=0; i<250; i++){
            if(!lsIssuedAlertCodes.contains(Integer.valueOf(i))){
                Log.d("MyApp","next alert code is " + i);
                lsIssuedAlertCodes.add(i);
                return i;
            }
        }
        return i;
    }
    /*
    removes the specified alert code from the list
     */
    public boolean removeAlertCode(int piAlertCode){
        return lsIssuedAlertCodes.remove(Integer.valueOf(piAlertCode));
    }
}
