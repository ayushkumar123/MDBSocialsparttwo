package com.ayush.mdbsocials;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    final String name;
    private final String description;
    final String date;
    final String email;
    private Object timestamp;
    String firebaseImageUrl;
    String id;
    int num_interested;


    public Message() {
        this.name = null;
        this.description = null;
        this.email = null;
        this.timestamp = null;
        this.firebaseImageUrl = null;
        this.date = null;


    }

    public Message(String name, String description, String email, String date, String firebaseImageUrl, int num_interested) {
        this.firebaseImageUrl =  firebaseImageUrl;
        this.name = name;
        this.description = description;
        this.email = email;
        this.timestamp = timestamp;
        this.date = date;

    }



    public String getMessage() {
        return message;
    }

    public String getFirebaseImageUrl() {
        return firebaseImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFirebaseImageUrl(String firebaseImageUrl) {
        this.firebaseImageUrl = firebaseImageUrl;
    }
}