package com.kirilldikun.ishop.utilits;

public class Message {
    String message;

    public Message(String message) {
        this.message = message;
    }

    public String toJson() {
        return "{\"message\":" + "\"" + message + "\"}";
    }
}
