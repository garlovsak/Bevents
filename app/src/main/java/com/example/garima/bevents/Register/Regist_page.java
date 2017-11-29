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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Regist_page extends AppCompatActivity implements View.OnClickListener{

    private static final int RC_SIGN_IN = 10101;

    private FirebaseAuth firebaseauth;
    private DatabaseReference mdatabase;
    private  GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mauth;

    private EditText textemail, textpass;
    private Button save,clear;
    private TextView newpass,signin;
    private SignInButton signInButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_page);

        firebaseauth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference();

        signInButton = (SignInButton)findViewById(R.id.sign_in_button);

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        signInButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                GooglesignIn();
                                            }
                                        });
    }


        // Configure Google Sign In
    @Override
    protected void onStart(){
        super.onStart();
        if(firebaseauth.getCurrentUser() != null){
           /* finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            */
           onAuthSuccess(firebaseauth.getCurrentUser());
        }
        //mauth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mauth.removeAuthStateListener(mAuthListener);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           FirebaseUser userp = task.getResult().getUser();
                            String username = userp.getDisplayName().toString().trim();
                            String email = userp.getEmail().toString().trim();

                            // Write new user
                            writeNewUser(userp.getUid(), username, email);

                            // Go to MainActivity
                            startActivity(new Intent(Regist_page.this, EventsListActivity.class));
                            finish();
                        }
                        if (!task.isSuccessful()) {
                           // Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Regist_page.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    private void GooglesignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
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
