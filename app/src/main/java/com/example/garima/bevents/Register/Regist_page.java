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
import com.example.garima.bevents.MainActivity;
import com.example.garima.bevents.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Regist_page extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseauth;
    private DatabaseReference mdatabase;

    private EditText textemail, textpass;
    private Button save,clear;
    private TextView newpass,signin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_page);

        firebaseauth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference();



        textemail = (EditText)findViewById(R.id.email);
        textpass = (EditText)findViewById(R.id.rpassword);

        save  = (Button)findViewById(R.id.rsave);
        save.setOnClickListener(this);

        clear = (Button)findViewById(R.id.rclear);
        clear.setOnClickListener(this);

        newpass = (TextView)findViewById(R.id.rnewpass);
        newpass.setOnClickListener(this);

        signin = (TextView)findViewById(R.id.regist);
        signin.setOnClickListener(this);
    }
    @Override
    protected void onStart(){
        super.onStart();
        if(firebaseauth.getCurrentUser() != null){
           /* finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            */
           onAuthSuccess(firebaseauth.getCurrentUser());
        }
    }

    @Override
    public void onClick(View view) {
        if(view == save){
            registerUser();
        }

        if( view == clear){
            textemail.setText("");
            textpass.setText("");
        }

        if(view == signin){
            startActivity(new Intent(getApplicationContext(),home_page.class));
        }

        if(view == newpass){
            startActivity(new Intent(getApplicationContext(),Create_pass.class));
        }


    }

    private void registerUser() {

        String email = textemail.getText().toString().trim();
        String password = textpass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseauth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            onAuthSuccess(task.getResult().getUser());
                            //Toast.makeText(Regist_page.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Regist_page.this, "Regiseration Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            
        

    }
    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(Regist_page.this, EventsListActivity.class));
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

        mdatabase.child("users").child(userId).setValue(user);
    }

}
