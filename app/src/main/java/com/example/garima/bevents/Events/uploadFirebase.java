package com.example.garima.bevents.Events;

/**
 * Created by Garima on 25-09-2017.
 */

public class uploadFirebase {
    public String currentuid;
    public String name;
    public String url;
    public String desc;


    public uploadFirebase() {
    }

    public uploadFirebase(String cuid, String name, String desc, String url) {
        this.currentuid = cuid;
        this.name = name;
        this.desc = desc;
        this.url= url;
    }
    public String getUserKey(){
        return currentuid;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
    public String getDesc() {
        return desc;
    }

}
