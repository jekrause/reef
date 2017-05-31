package kdc.reef.john.reefcaluclators;

import java.util.ArrayList;

/**
 * Created by John on 5/30/2017.
 */

public class InvertsProfile {
    //constructed variables
    private String name = "test";
    private String datePurchased = "null";
    private int iconCoralId = R.drawable.invert;
    private String photoChosen; //uriString
    private double price = 00.00;



    private ArrayList<ImageList> imageArrayList;

    //Non constructed variables
    private String purchasedFrom = "Seller";
    private double size = 0.0;

    public InvertsProfile(String name, String datePurchased, double price){
        imageArrayList = new ArrayList<>();
        this.setName(name);
        this.setDatePurchased(datePurchased);
        this.price = price;
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

    public String getPhotoChosen() {
        return photoChosen;
    }

    public void setPhotoChosen(String photoChosen) {
        this.photoChosen = photoChosen;
    }

    public ArrayList<ImageList> getImageArrayList() {
        return imageArrayList;
    }
}
