package kdc.reef.john.reefcaluclators;

import android.graphics.drawable.Drawable;
import android.support.v4.app.AppLaunchChecker;

/**
 * Created by John on 5/23/2017.
 */

public class ImageList {
    String imageURI;

    public ImageList(String x){
        imageURI = x;
    }
    public void set(String x){
        imageURI =x;
    }
    public String get(){
        return imageURI;
    }
}
