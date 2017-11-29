package com.example.garima.bevents.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Garima on 25-09-2017.
 */

public class UploadFirebase {
    private String snapShotId;
    private String currentuid;
    private String title;
    private String Startdate, Enddate;
    private String Starttime, Endtime;

    private String imageurl;
    private String description;
    private String Location;
    private ArrayList<String> interestedUserIds = new ArrayList<>();

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();


    public UploadFirebase() {
    }

    public UploadFirebase(String cuid, String title, String sdate, String stime,
                          String edate, String etime, String location, String desc, String url) {

        this.currentuid = cuid;
        this.title = title;
        this.Startdate = sdate;
        this.Starttime = stime;
        this.Enddate = edate;
        this.Endtime = etime;
        this.Location = location;
        this.description = desc;
        this.imageurl = url;
    }

    public void copyValues(UploadFirebase uploadFirebase) {
        snapShotId = uploadFirebase.snapShotId;
        currentuid = uploadFirebase.currentuid;
        title = uploadFirebase.title;
        Startdate = uploadFirebase.Startdate;
        Enddate = uploadFirebase.Enddate;
        Starttime = uploadFirebase.Starttime;
        Endtime = uploadFirebase.Endtime;
        imageurl = uploadFirebase.imageurl;
        description = uploadFirebase.description;
        Location = uploadFirebase.Location;
        interestedUserIds = uploadFirebase.interestedUserIds;
    }

    public void setCurrentuid(String currentuid) {
        this.currentuid = currentuid;
    }


    public String getCurrentuid() {
        return currentuid;
    }

    public String getEnddate() {
        return Enddate;
    }

    public String getEndtime() {
        return Endtime;
    }

    public String getStartdate() {
        return Startdate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return Location;
    }

    public String getStarttime() {
        return Starttime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnddate(String enddate) {
        Enddate = enddate;
    }

    public void setEndtime(String endtime) {
        Endtime = endtime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setStartdate(String startdate) {
        Startdate = startdate;
    }

    public void setStarttime(String starttime) {
        Starttime = starttime;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public void setStars(Map<String, Boolean> stars) {
        this.stars = stars;
    }

    public Map<String, Boolean> getStars() {
        return stars;
    }

    public ArrayList<String> getInterestedUserIds() {
        return interestedUserIds;
    }

    public void setInterestedUserIds(ArrayList<String> interestedUserIds) {
        this.interestedUserIds = interestedUserIds;
    }

    public String getSnapShotId() {
        return snapShotId;
    }

    public void setSnapShotId(String snapShotId) {
        this.snapShotId = snapShotId;
    }
}
