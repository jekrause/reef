package kdc.reef.john.reefcaluclators;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReefTestsActivity extends AppCompatActivity {

    ArrayAdapter <TestData>arrayAdapter;
    static List<TestData> test;
    static List<Boolean> testBoolArray = new ArrayList<>();
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reef_tests);
        loadData();
        arrayAdapter = new MyListAdapter();

        list = (ListView) findViewById(R.id.coralListView);
        list.setAdapter(arrayAdapter);
        listenForClicks();
    }

    public void addButton(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter Test Name");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(input.getText().toString().length()!=0){
                    test.add(new TestData(input.getText().toString()));
                    arrayAdapter.notifyDataSetChanged();
                    testBoolArray.add(Boolean.FALSE);
                }
                else{
                    Toast.makeText(ReefTestsActivity.this, "Please type a name for the test", Toast.LENGTH_SHORT).show();
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

    public class MyListAdapter extends ArrayAdapter<TestData>{
        public MyListAdapter(){
            super(ReefTestsActivity.this, R.layout.test_view, test);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //make sure we have a view to work with
            View testView = convertView;
            if(testView == null){
                testView = getLayoutInflater().inflate(R.layout.test_view,parent,false);
            }
            TestData currentPosition = test.get(position);

            if(currentPosition!=null){
                TextView tvName = (TextView) testView.findViewById(R.id.testID);
                tvName.setText(currentPosition.getName());
                if(currentPosition.getMostRecentDate()!=null){
                    TextView tvDate = (TextView) testView.findViewById(R.id.testDate);
                    tvDate.setText(new SimpleDateFormat("MM/dd/yyy").format(currentPosition.getMostRecentDate()));
                }
                else{
                    TextView tvDate = (TextView) testView.findViewById(R.id.testDate);
                    tvDate.setText("Date");
                }
            }

            return testView;

        }
    }

    private void listenForClicks(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReefTestsActivity.this, GraphActivity.class);
                testBoolArray.set(position,true);
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ReefTestsActivity.this);

                builder.setTitle("Confirm Deletion");
                builder.setMessage("Are you sure you want to delete this profile?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        test.remove(position);
                        testBoolArray.remove(position);
                        arrayAdapter.notifyDataSetChanged();
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

    public static List<TestData> getTestArray(){
        return test;
    }
    public static List<Boolean> getTestBoolArray(){return testBoolArray;}

    private void saveData(){
        Gson gson = new Gson();
        String str = gson.toJson(test);

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

    private void loadData() {
        Gson gson = new Gson();
        try {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 80085);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique

                return;
            }

            FileInputStream filein = openFileInput("tests.txt");
            InputStreamReader isr = new InputStreamReader(filein);
            BufferedReader buffreader = new BufferedReader(isr);

            String readString = buffreader.readLine();

            isr.close();
            buffreader.close();
            filein.close();

            if (!readString.isEmpty()) {
                test = gson.fromJson(readString,new TypeToken<Collection<TestData>>(){}.getType() );
                for(TestData t: test){
                    testBoolArray.add(Boolean.FALSE);
                }
            }

            }catch(Exception ex){
                test = new ArrayList();
            }
    }

    public List<TestData> getData(){
        return test;
    }

    @Override
    public void onBackPressed(){
        saveData();
    }

    @Override
    public void onResume(){
        arrayAdapter.notifyDataSetChanged();
        super.onResume();
    }


}
