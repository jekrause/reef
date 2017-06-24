package kdc.reef.john.reefcaluclators;

import com.jjoe64.graphview.series.DataPoint;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by John on 6/21/2017.
 */

public class DataPoints {
    double v;
    Date date;

    public DataPoints( Date sdate, double dv){
        date = sdate;
        v = dv;
    }

    public double getV() {
        return v;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
