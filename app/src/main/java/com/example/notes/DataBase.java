package com.example.notes;

import java.util.List;

public interface DataBase {
    List<Note> getNotes();

   Note getNote(String id);

    void updateNote(Note note);
    void removeNote(Note note);
}
