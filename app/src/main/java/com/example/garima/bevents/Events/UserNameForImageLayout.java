package com.example.garima.bevents.Events;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Garima on 25-11-2017.
 */

public class UserNameForImageLayout {
    private DatabaseReference firebaseDatabase;
    String email;


    public String UserNameFunction(String Userkey) {

        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase.child("users").child(Userkey).child("email").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       // name = dataSnapshot.getValue().toString().trim();
                        email = dataSnapshot.getValue().toString();
                        System.out.println("the key user name is "+dataSnapshot.getValue());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        return email ;
    }
}










