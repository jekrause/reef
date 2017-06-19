package kdc.reef.john.reefcaluclators;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by John on 6/19/2017.
 */

public class TestData {
    private String name;
    List<String> dates;
    List<Integer> values;

    public TestData(String name){
        this.name = name;
        values = new ArrayList<>();
        dates = new ArrayList<>();
    }

    public void  setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getMostRecentDate(){
        if(dates.size()==0){
            return null;
        }
        return dates.get(dates.size()-1);
    }

}
