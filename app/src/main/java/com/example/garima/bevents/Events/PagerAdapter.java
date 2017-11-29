package com.example.garima.bevents.Events;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.garima.bevents.R;

/**
 * Created by Garima on 22-09-2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;
    Context context;


    public PagerAdapter(Context context,FragmentManager fm){
        super(fm);
        this.context = context;

    }


    public PagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ExploreEventsFragment tab1 = new ExploreEventsFragment();
                return tab1;
            case 1:
                Calender tab2 = new Calender();
                return tab2;
            case 2:
                UserEventsFragment tab3 = new UserEventsFragment();
                return tab3;
            default:
                return null;

        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return context.getString(R.string.explore);
            case 1:
                return context.getString(R.string.Calender);
            case 2:
                return context.getString(R.string.Myevents);

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return 3;
    }
}

