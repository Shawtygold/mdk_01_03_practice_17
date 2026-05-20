package com.example.notesmylnikov.domains.models;

public class Note {
    public int id;
    public String title;
    public String text;
    public String date;
    public String color;

    public Note(){

    }
    public Note (int _id, String _title, String _text, String _date, String _color){
        id = _id;
        title = _title;
        text = _text;
        date = _date;
        color = _color;
    }
}
