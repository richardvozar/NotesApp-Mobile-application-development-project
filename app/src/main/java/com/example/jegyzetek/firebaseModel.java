package com.example.jegyzetek;



public class firebaseModel {

    private String title;
    private String content;
    private long dateInMillis;

    public firebaseModel() {
    }

    public firebaseModel(String title, String content, long dateInMillis) {
        this.title = title;
        this.content = content;
        this.dateInMillis = dateInMillis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDateInMillis() {
        return dateInMillis;
    }

    public void setDateInMillis(long dateInMillis) {
        this.dateInMillis = dateInMillis;
    }
}
