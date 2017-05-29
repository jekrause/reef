package kdc.reef.john.reefcaluclators;

import android.graphics.drawable.Drawable;
import android.support.v4.app.AppLaunchChecker;

/**
 * Created by John on 5/23/2017.
 */

public class ImageList {
    private String imageURI;
    private String dateOfImage;

    public ImageList(String x, String y){
        imageURI = x;
        dateOfImage = y;
    }

    public  void setDateOfImage(String x){
        dateOfImage = x;
    }
    public String getDateOfImage(){
        return dateOfImage;
    }

    public void set(String x){
        imageURI =x;
    }
    public String get(){
        return imageURI;
    }
}
