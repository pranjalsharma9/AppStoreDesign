package com.pranjal.appstoredesign;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pranjal.appstoredesign.adapters.AppItemAdapter;
import com.pranjal.appstoredesign.models.AppItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // The list of apps to be displayed in the recyclerView.
    private ArrayList<AppItem> appItems;

    // The RecyclerView.
    private RecyclerView recyclerView;

    // The Local BroadcastReceiver to listen progress changes.
    private BroadcastReceiver progressReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int progress = intent.getIntExtra("progress", -1);
            int position = intent.getIntExtra("position", -1);
            if (position != -1) {
                // If the position is valid get it's viewHolder and update the progressBar.
                appItems.get(position).setProgress(progress);
                AppItemAdapter.ViewHolder holder = (AppItemAdapter.ViewHolder)
                        recyclerView.findViewHolderForAdapterPosition(position);
                if (holder != null) {
                    holder.setProgress(progress);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init () {
        // Initialising the list of apps.
        appItems = new ArrayList<>();
        // Dummy apps.
        appItems.add(new AppItem("First App", "Version 2.4.3",
                "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Second App", "Version 4.3.2",
                "A lot of information\nin two lines",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Third App", "Version 3.4.2",
                "A lot of information\nin\nthree lines",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Fourth App", "Version 2.4.3",
                "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Fifth App", "Version 2.4.3",
                "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Sixth App", "Version 2.10.3",
                "A lot of information\nin\nthree lines",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Seventh App", "Version 1.2.0",
                "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Eighth App", "Version 2.4.3",
                "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Ninth App", "Version 4.3.2",
                "A lot of information\nin\nthree lines",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Tenth App", "Version 3.4.2",
                "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Eleventh App", "Version 2.4.3",
                "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Twelfth App", "Version 2.4.3",
                "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Thirteenth App", "Version 2.10.3",
                "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Fourteenth App", "Version 1.2.0",
                "A lot of information",
                R.mipmap.ic_launcher));

        // Getting the RecyclerView.
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_activity_main);

        // Creating an adapter for the recyclerView.
        AppItemAdapter adapter = new AppItemAdapter(appItems);

        // Setting up the recyclerView.
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );

        // Registering the broadcast receiver with this activity.
        LocalBroadcastManager.getInstance(this).registerReceiver(progressReceiver,
                new IntentFilter(App.PROGRESS_BROADCAST_NAME));
    }

    @Override
    protected void onDestroy () {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(progressReceiver);
        super.onDestroy();
    }

}
