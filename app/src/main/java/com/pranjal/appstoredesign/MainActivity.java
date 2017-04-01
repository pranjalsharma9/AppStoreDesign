package com.pranjal.appstoredesign;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init () {
        // Initialising the list of apps.
        appItems = new ArrayList<>();
        appItems.add(new AppItem("First App", "Version 2.4.3", "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Second App", "Version 4.3.2", "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Third App", "Version 3.4.2", "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Fourth App", "Version 2.4.3", "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Fifth App", "Version 2.4.3", "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Sixth App", "Version 2.10.3", "A lot of information",
                R.mipmap.ic_launcher));
        appItems.add(new AppItem("Seventh App", "Version 1.2.0", "A lot of information",
                R.mipmap.ic_launcher));

        // Getting the RecyclerView.
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_activity_main);

        // Creating an adapter for the recyclerView.
        AppItemAdapter adapter = new AppItemAdapter(appItems);

        // Setting up the recyclerView.
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );

    }

}
