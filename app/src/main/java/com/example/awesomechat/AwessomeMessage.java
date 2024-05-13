package com.example.awesomechat;

public class AwessomeMessage {
    private String text;
    private String name;
    private String sender;
    private String recepient;
    private String imageUrl;
    private boolean isMine;
    public AwessomeMessage(){}

    public AwessomeMessage(String text, String name, String sender,
                           String recepient, String imageUrl,
                           boolean isMine) {
        this.text = text;
        this.name = name;
        this.sender = sender;
        this.recepient = recepient;
        this.imageUrl = imageUrl;
        this.isMine = isMine;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public String getRecepient() {
        return recepient;
    }

    public void setRecepient(String recepient) {
        this.recepient = recepient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
