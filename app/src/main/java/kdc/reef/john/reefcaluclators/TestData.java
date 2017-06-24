package kdc.reef.john.reefcaluclators;

import android.support.annotation.NonNull;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by John on 6/19/2017.
 */

public class TestData {
    private String name;
    List<DataPoints> dataPoints;
    private String label = "ppm";

    public TestData(String name){
        this.name = name;
        dataPoints = new ArrayList<>();
        label = "ppm";
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void  setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Date getMostRecentDate(){
        if(dataPoints.size()==0){
            return null;
        }
        return dataPoints.get(dataPoints.size()-1).getDate();
    }

    public void addSet(DataPoints dp){
        dataPoints.add(dp);
    }



}
