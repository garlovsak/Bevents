package com.example.garima.bevents.Events;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.garima.bevents.R;

/**
 * Created by Garima on 22-09-2017.
 */

public class Calender extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.calender, container, false);
        TextView textView = (TextView) v;
        ((TextView) v).setText("Calender Tab");
        return v;
    }
}
