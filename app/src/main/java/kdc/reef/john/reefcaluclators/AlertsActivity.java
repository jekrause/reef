package kdc.reef.john.reefcaluclators;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AlertsActivity extends AppCompatActivity {

    public static List<Alert> lsAlerts;
    ArrayAdapter<Alert> oAlertsArrayAdapter;

    ListView viewListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        oAlertsArrayAdapter = new AlertsActivity.MyListAdapter();
        lsAlerts = new ArrayList<>();
        viewListView = (ListView) findViewById(R.id.listViewAlarms);
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
        public View getView(int position, View convertView, ViewGroup parent) {
            //make sure we have a view to work with
            View coralView = convertView;
            if(coralView == null){
                coralView = getLayoutInflater().inflate(R.layout.alert_list_item,parent,false);

            }

            return coralView;
        }
    }

    public void addAlert(View view){
        lsAlerts.add(new Alert(null,null,true,null));
        oAlertsArrayAdapter.notifyDataSetChanged();
    }

}
