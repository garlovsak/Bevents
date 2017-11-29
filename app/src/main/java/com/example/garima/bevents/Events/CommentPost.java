package com.example.garima.bevents.Events;

/**
 * Created by Garima on 27-11-2017.
 */

public class CommentPost {

    public String uid;
    public String author;
    public String text;

    public CommentPost() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public CommentPost(String uid, String author, String text) {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }
    public CommentPost(String author,String text){
        this.author = author;
        this.text = text;

    }

}
