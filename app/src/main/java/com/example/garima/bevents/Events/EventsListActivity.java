package com.example.garima.bevents.Events;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
//import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.garima.bevents.R;

public class EventsListActivity extends AppCompatActivity  {
    CharSequence tabs[] = {"Explore", "Calender", "My EventsListActivity"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        // Create an adapter that knows which fragment should be shown on each page
        PagerAdapter adapter = new PagerAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        /*

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabs, numoftabs);

        // Assigning ViewPager View and setting the adapter

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        slidingTabLayout.setDistributeEvenly(true);

        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        slidingTabLayout.setViewPager(viewPager);*/
    }
}

