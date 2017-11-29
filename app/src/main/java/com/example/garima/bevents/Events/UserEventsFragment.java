package com.example.garima.bevents.Events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.garima.bevents.R;
import com.example.garima.bevents.Register.Regist_page;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Garima on 22-09-2017.
 */

public class UserEventsFragment extends Fragment implements View.OnClickListener{
    Button cEvent;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View Inputfregv =  inflater.inflate(R.layout.myevents, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        cEvent = (Button) Inputfregv.findViewById(R.id.button2);
        cEvent.setOnClickListener(this);

        return Inputfregv;

    }


    @Override
    public void onClick(View view) {

        if(view == cEvent){
            firebaseAuth.signOut();
            //ref.unauth();
            Intent i = new Intent(getActivity(),Regist_page.class);
            startActivity(i);
        }

    }
}
