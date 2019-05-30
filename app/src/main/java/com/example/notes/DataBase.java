package com.example.notes;

import android.provider.ContactsContract;

import java.util.Date;
import java.util.List;

public interface DataBase {
    List<Note> getNotes();
    List<Note> getNotes(String sortType, boolean desc);


   Note getNote(String id);
    void saveNote(Note note);
    void updateNote(String id, String title, String content, Date deadline);
    void updateNote(String id, String title, String content);
    void removeNote(Note note);
}
