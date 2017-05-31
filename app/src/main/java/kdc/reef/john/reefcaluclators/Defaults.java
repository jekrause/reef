package kdc.reef.john.reefcaluclators;

import android.app.Application;

import java.io.Serializable;

/**
 * Created by John on 5/31/2017.
 */

public class Defaults implements Serializable{

    private String currency = "$";
    private int curPos =0;
    private String measurement = "\"";
    private int measPos =0;
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

}
