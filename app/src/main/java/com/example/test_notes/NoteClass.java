package com.example.test_notes;

public class NoteClass {
    //Класс "заметок", который хранит текст и дату создания/редактирования
    private String data;
    private String date;


    public NoteClass (String _data, String _date){
        data = _data;
        date = _date;

    }

    public String  getData(){
        return data;
    }

    public String getDate(){
        return date;
    }


}
