package kdc.reef.john.reefcaluclators;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.FormatException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.R.id.input;

public class Graph extends AppCompatActivity {
    TestData testData;
    ArrayAdapter<DataPoints> dataPointsArrayAdapter;
    ArrayAdapter<CharSequence> adapterSpinner;

    static List<Boolean> testBoolNumbers;

    TextView nameTV;
    GraphView graph;
    ListView list;
    LineGraphSeries<DataPoint> series;

    int currentlySelected = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        testBoolNumbers = ReefTests.getTestBoolArray();
        int index =0;
        while(index< testBoolNumbers.size()){
            if(testBoolNumbers.get(index)){
                testBoolNumbers.set(index,false);
                break;
            }
            index++;
        }

        testData = ReefTests.getTestArray().get(index);


        nameTV = (TextView) findViewById(R.id.testTitle);
        nameTV.setText(testData.getName());
        graph  = (GraphView) findViewById(R.id.graph);

        dataPointsArrayAdapter = new MyListAdapter();
        list = (ListView) findViewById(R.id.testScrollList);
        list.setAdapter(dataPointsArrayAdapter);

        listenForClicks();


        series = new LineGraphSeries<>();
        //graph.addSeries(series);
        updateGraph();
    }

    public void addTestClick(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter Test Name");

// Set an EditText view to get user input
        Context context = this.getApplicationContext();
        final LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);

//        final EditText titleBox = new EditText(context);
//        titleBox.setHint(todaysDate());
//        layout.addView(titleBox);

        final DatePicker picker = new DatePicker(this);
        picker.setCalendarViewShown(false);
        layout.addView(picker);

        final EditText descriptionBox = new EditText(context);
        descriptionBox.setHint("Value");

        layout.addView(descriptionBox);
        descriptionBox.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);

        alert.setView(layout);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Date date;
                int   day  = picker.getDayOfMonth();
                int   month= picker.getMonth();
                int   year = picker.getYear()-1900;

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String formatedDate = sdf.format(new Date(year, month, day));
                try{
                    date = sdf.parse(formatedDate);
                }catch (ParseException ex){
                    date = Calendar.getInstance().getTime();
                }

                String v = descriptionBox.getText().toString();
                if(v.length()>0){
                    try{
                        //testData.addSet(System.currentTimeMillis(), Double.parseDouble(v));
                        DataPoints dps = new DataPoints(date,Double.parseDouble(v));
                        testData.addSet(dps);
                        Collections.sort(testData.dataPoints, new DateComparator());
                        updateGraph();
                    }catch (RuntimeException ex){
                        //don't add it
                    }
                }
                else{
                    Toast.makeText(Graph.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do nothing
            }
        });

        alert.show();
    }

    public void changeLabelClick(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Measurement");

// Set an EditText view to get user input
        Context context = this.getApplicationContext();
        final LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);

        final Spinner spin = new Spinner(this);

        adapterSpinner = ArrayAdapter.createFromResource(this, R.array.measurements, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapterSpinner);
//        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });    Log.d("MyApp",testData.getLabel());
        if(testData.getLabel().equalsIgnoreCase("dKH")){spin.setSelection(0);}
        else if(testData.getLabel().equals("meq/l")){spin.setSelection(1);}
        else if(testData.getLabel().equals("ppm")){spin.setSelection(2);}
        adapterSpinner.notifyDataSetChanged();

        layout.addView(spin);

        alert.setView(layout);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = spin.getSelectedItemPosition();
                if(position == 0){testData.setLabel("dKH");}
                else if(position == 1){testData.setLabel("meq/l");}
                else if(position == 2){testData.setLabel("ppm");}
                dataPointsArrayAdapter.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    private void updateGraph(){
        //graph.removeAllSeries();
        int i=0;

        Log.d("www", testData.dataPoints+"");

        DataPoint[] dpArr = new DataPoint[testData.dataPoints.size()];
        for(DataPoints x : testData.dataPoints){
            dpArr[i] = new DataPoint(x.getDate(), x.getV());
            i++;
        }
        series.resetData(dpArr);

        if(dpArr.length!=0){
            // set date label formatter
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
            graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

// set manual x bounds to have nice steps
            graph.getViewport().setMinX(dpArr[0].getX());
            graph.getViewport().setMaxX(dpArr[i-1].getX());
            graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
            graph.getGridLabelRenderer().setHumanRounding(false);
        }

        dataPointsArrayAdapter.notifyDataSetChanged();

    }

    public class MyListAdapter extends ArrayAdapter<DataPoints>{
        public MyListAdapter(){
            super(Graph.this, R.layout.test_list_view, testData.dataPoints);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //make sure we have a view to work with
            View testView = convertView;
            if(testView == null){
                testView = getLayoutInflater().inflate(R.layout.test_list_view,parent,false);
            }
            DataPoints currentPosition = testData.dataPoints.get(position);

            if(currentPosition!=null){
                TextView tvValue = (TextView) testView.findViewById(R.id.testValue);
                tvValue.setText(Double.toString(currentPosition.getV()) + " " + testData.getLabel());
                if(currentPosition.getDate()!=null){
                    TextView tvDate = (TextView) testView.findViewById(R.id.testDate);
                    String x = new SimpleDateFormat("MM/dd/yyyy").format(currentPosition.getDate());
                    tvDate.setText(x);
                }
            }
            return testView;
        }
    }

    private void listenForClicks(){

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Graph.this);

                builder.setTitle("Confirm Deletion");
                builder.setMessage("Are you sure you want to delete this profile?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        testData.dataPoints.remove(position);
                        Collections.sort(testData.dataPoints, new DateComparator());
                        dataPointsArrayAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });
    }



    private void saveData(){
        Gson gson = new Gson();
        String str = gson.toJson(ReefTests.test);

        try{
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    // Explain to the user why we need to read the contacts
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 80085);
                return;
            }
            FileOutputStream fileout = openFileOutput("tests.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(str);
            outputWriter.close();
        }catch(Exception ex){

        }
        super.onBackPressed();
    }

    public class DateComparator implements Comparator<DataPoints> {
        public int compare(DataPoints p, DataPoints q) {
            if(p.getDate().after(q.getDate()))
                return 1;
            if(p.getDate().before(q.getDate()))
                return -1;
            else
                return 0;
        }
    }

    private String todaysDate(){
        String x = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
        return x;
    }

    @Override
    public void onBackPressed(){
        saveData();
    }


}
