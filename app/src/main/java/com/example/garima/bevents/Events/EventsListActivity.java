package com.example.garima.bevents.Events;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
//import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.garima.bevents.R;

public class EventsListActivity extends AppCompatActivity  {
    CharSequence tabs[] = {"ExploreEventsFragment", "Calender", "My EventsListActivity"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        FloatingActionButton floatButton = (FloatingActionButton)findViewById(R.id.create_event);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(EventsListActivity.this,CreateEventActivity.class);
                startActivity(in);
            }
        });

        // Create an adapter that knows which fragment should be shown on each page
        PagerAdapter adapter = new PagerAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


    }
}

