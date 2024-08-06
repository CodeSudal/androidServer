package com.example.androidserver.model;

public class MyResponse {
    private String message;

    public MyResponse() {}

    public MyResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
