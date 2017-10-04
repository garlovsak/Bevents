package com.example.garima.bevents.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.garima.bevents.Events.EventsListActivity;
import com.example.garima.bevents.R;

public class home_page extends AppCompatActivity implements View.OnClickListener {

    private EditText email,pass;
    private TextView regist,newpass;
    private Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.editText2);
        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(this);
        regist = (TextView) findViewById(R.id.regist);
        regist.setOnClickListener(this);
        newpass =  (TextView) findViewById(R.id.newpass);
        newpass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == save)
        {
            CheckUserData();

        }
        if(view == regist)
        {
            Intent i = new Intent(home_page.this,Regist_page.class);
            startActivity(i);
        }
        if(view == newpass){
            Intent in = new Intent(home_page.this,Create_pass.class);
            startActivity(in);
        }


    }

    private void CheckUserData() {
        Intent i= new Intent(home_page.this, EventsListActivity.class);
        startActivity(i);
    }
}
