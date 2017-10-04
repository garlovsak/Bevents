package com.example.garima.bevents.Events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.garima.bevents.R;

/**
 * Created by Garima on 22-09-2017.
 */

public class UserEventsFragment extends Fragment implements View.OnClickListener{
    Button cevent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View Inputfregv =  inflater.inflate(R.layout.calender, container, false);

        cevent = (Button) Inputfregv.findViewById(R.id.button2);
        cevent.setOnClickListener(this);

        return Inputfregv;

    }


    @Override
    public void onClick(View view) {
        if(view == cevent){
            Intent i = new Intent(getActivity(),CreateEventActivity.class);
            startActivity(i);
        }

    }
}
