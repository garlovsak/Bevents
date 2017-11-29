package com.example.garima.bevents.Register;

/**
 * Created by Garima on 12-10-2017.
 */

class User {
    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
