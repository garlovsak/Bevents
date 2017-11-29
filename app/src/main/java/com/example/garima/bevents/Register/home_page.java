package com.example.garima.bevents.Register;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.garima.bevents.Events.EventsListActivity;
import com.example.garima.bevents.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class home_page extends AppCompatActivity implements View.OnClickListener {

    private EditText textemail, textpass;
    private TextView regist,newpass;
    private Button save;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mdatabaserefrence;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        firebaseAuth = FirebaseAuth.getInstance();
        mdatabaserefrence = FirebaseDatabase.getInstance().getReference();




        textemail = (EditText)findViewById(R.id.email);
        textpass = (EditText)findViewById(R.id.editText2);

        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(this);

        regist = (TextView) findViewById(R.id.regist);
        regist.setOnClickListener(this);

        newpass =  (TextView) findViewById(R.id.newpass);
        newpass.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {
           /* finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));*/
            onAuthSuccess(firebaseAuth.getCurrentUser());
        }
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

        String email = textemail.getText().toString().trim();
        String password = textpass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            /*finish();
                            startActivity(new Intent(getApplicationContext(),EventsListActivity.class));
                            */
                            onAuthSuccess(task.getResult().getUser());
                        }else

                        {
                            Toast.makeText(home_page.this, "Sign In Failed....", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());


        writeNewUser(user.getUid(), username, user.getEmail());


        startActivity(new Intent(home_page.this, EventsListActivity.class));
        finish();
    }


    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mdatabaserefrence.child("users").child(userId).setValue(user);
    }
}
