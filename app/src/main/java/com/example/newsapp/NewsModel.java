package com.example.newsapp;

public class NewsModel {
    int ID;
    String Header;
    String MainText;

    public NewsModel(int id, String header, String mainText){
        this.ID = id;
        this.Header = header;
        this.MainText = mainText;
    }

    public int getID() {
        return ID;
    }

    public String getHeader() {
        return Header;
    }

    public String getMainText() {
        return MainText;
    }
}
