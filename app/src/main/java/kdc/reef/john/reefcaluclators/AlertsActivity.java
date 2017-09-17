package kdc.reef.john.reefcaluclators;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AlertsActivity extends AppCompatActivity {

    public static List<Alert> lsAlerts;
    ArrayAdapter<Alert> oAlertsArrayAdapter;
    int iMaxNumber = 2;
    int iCurrentNumber = 0;

    ListView viewListView;
    TextView txtCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addAlert()){
                    Snackbar.make(view, "Alarm Added", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
                else{
                    Snackbar.make(view, "Please consider upgrading.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
        lsAlerts = new ArrayList<>();
        viewListView = (ListView) findViewById(R.id.listViewAlarms);
        txtCounter = (TextView) findViewById(R.id.txtCounter);
        oAlertsArrayAdapter = new AlertsActivity.MyListAdapter();
        //curNumber = coralProfileArrayList.size();
        viewListView.setCacheColorHint(Color.TRANSPARENT); // not sure if this is required for you.
        viewListView.setFastScrollEnabled(true);
        viewListView.setScrollingCacheEnabled(false);
        viewListView.setAdapter(oAlertsArrayAdapter);
    }

    public class MyListAdapter extends ArrayAdapter<Alert>{
        public MyListAdapter(){
            super(AlertsActivity.this, R.layout.alert_list_item, lsAlerts);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //make sure we have a view to work with
            View alertView = convertView;
            if(alertView == null) {
                alertView = getLayoutInflater().inflate(R.layout.alert_list_item, parent, false);
            }
            final ImageView imageViewIcon = (ImageView) alertView.findViewById(R.id.itemIcon);
            imageViewIcon.setBackgroundResource(lsAlerts.get(position).iIcon);
            TextView txtName = (TextView) alertView.findViewById(R.id.txtAlertItemTitle);
            txtName.setText(lsAlerts.get(position).sName);
            final Switch switch1 = (Switch) alertView.findViewById(R.id.switch1);
            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        switch1.setText("ON ");
                        lsAlerts.get(position).iIcon = R.drawable.alarm_clock_red;
                    }
                    else{
                        switch1.setText("OFF");
                        lsAlerts.get(position).iIcon = R.drawable.alarm_clock_black;
                    }
                    oAlertsArrayAdapter.notifyDataSetChanged();
                }
            });
            return alertView;
        }
    }

    public boolean addAlert(){
        if(iCurrentNumber<iMaxNumber){
            lsAlerts.add(new Alert(null,null,true));
            iCurrentNumber ++;
            oAlertsArrayAdapter.notifyDataSetChanged();
            updateCounter();
            registerClickCallBack();
            return true;
        }
        return false;
    }

    private void updateCounter(){
        txtCounter.setText(iCurrentNumber + " of " + iMaxNumber);
        if(iCurrentNumber == iMaxNumber){
            txtCounter.setTextColor(Color.RED);
        }
        else{
            txtCounter.setTextColor(Color.WHITE);
        }
    }

    //sets on click listeners
    private void registerClickCallBack(){
        viewListView = (ListView) findViewById(R.id.listViewAlarms);
        viewListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlertsActivity.this);
                builder.setTitle("Confirm Deletion");
                builder.setMessage("Are you sure you want to delete this profile?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        //TODO
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
        viewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
                Toast.makeText(AlertsActivity.this, "Edit Popup", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
