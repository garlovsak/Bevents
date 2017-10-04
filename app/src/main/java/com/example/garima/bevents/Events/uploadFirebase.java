package com.example.garima.bevents.Events;

/**
 * Created by Garima on 25-09-2017.
 */

public class uploadFirebase {
    public String name;
    public String url;


    public uploadFirebase() {
    }

    public uploadFirebase(String name, String url) {
        this.name = name;
        this.url= url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}
