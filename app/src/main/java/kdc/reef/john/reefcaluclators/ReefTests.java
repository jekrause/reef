package kdc.reef.john.reefcaluclators;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReefTests extends AppCompatActivity {

    GraphView graph;
    EditText dateEdit;
    EditText valueEdit;


    DataPoint [] x = new DataPoint[100];
    static int cursor =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reef_tests);
        //find views
        graph =  (GraphView) findViewById(R.id.graph);
        dateEdit = (EditText) findViewById(R.id.dateEdit);
        valueEdit = (EditText) findViewById(R.id.valueEdit);

        dateEdit.setHint(todaysDate());

//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
//                new DataPoint(0,1),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(3, 2),
//        });





        graph.setTitle("TestName");
        //graph.addSeries(series);
    }

    public void submitDataClick(View view){
        double value = Double.parseDouble(valueEdit.getText().toString());
        int i = cursor;
        x[i] = new DataPoint(i, value);
        cursor++;
    }

    public  void graphItClick(View view){
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(x);
        graph.addSeries(series);
    }

    private String todaysDate(){
        String x = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
        return x;
    }
}
