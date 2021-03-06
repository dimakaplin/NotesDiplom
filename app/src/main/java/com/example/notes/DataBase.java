package com.example.notes;

import java.util.List;

public interface DataBase {

    List<Note> getNotes(String sortType, boolean desc);


    Note getNote(String id);

    void saveNote(Note note);

    void updateNote(String id, String title, String content, long deadline);

    void updateNote(String id, String title, String content);

    void removeNote(Note note);
}
