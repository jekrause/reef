package kdc.reef.john.reefcaluclators;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by John on 5/6/2016.
 */
public class CoralProfile implements Serializable{

    //constructed variables
    private String name = "test";
    private String datePurchased = "null";
    private int iconCoralId = R.drawable.coral;
    private Uri photoChosen;
    private double price = 00.00;

    //Non constructed variables
    private String purchasedFrom = "Seller";
    private double size = 0.0;

    public CoralProfile(String name, String datePurchased, double price){
        this.setName(name);
        this.setDatePurchased(datePurchased);
        this.price = price;

        System.out.println("CoralProfile has been added");
    }

    public String getName() {
        return name;
    }

    public int getIconCoralId(){return iconCoralId;}

    public String getDatePurchased() {
        return datePurchased;
    }




    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPurchasedFrom() {
        return this.purchasedFrom;
    }

    public void setPurchasedFrom(String purchasedFrom) {
        this.purchasedFrom = purchasedFrom;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDatePurchased(String datePurchased) {
        this.datePurchased = datePurchased;
    }

    public void setIconCoralId(int iconCoralId) {
        this.iconCoralId = iconCoralId;
    }

    public Uri getPhotoChosen() {
        return photoChosen;
    }

    public void setPhotoChosen(Uri photoChosen) {
        this.photoChosen = photoChosen;
    }
}
