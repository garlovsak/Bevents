package com.example.garima.bevents.Events;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Garima on 22-09-2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {



    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Explore tab1 = new Explore();
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
    public int getCount() {
        return 3;
    }
}


