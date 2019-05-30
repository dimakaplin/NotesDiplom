package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NotesList extends AppCompatActivity {

    private List<Note> notes = new ArrayList<>();
    private DataBase dataStorage;

    private ListView list;
    private NotesAdapter notesAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.add_note:
                Intent intent = new Intent(NotesList.this, ChangeNoteActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        init();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("NOTES", "resumed");
        init();
    }

    private void init() {
        dataStorage = App.getDataStorage();
        fillNotesList();
        list = findViewById(R.id.list);
        notesAdapter = new NotesAdapter(this, notes);
        list = findViewById(R.id.list);
        list.setAdapter(notesAdapter);

    }

    private void fillNotesList() {
        notes = dataStorage.getNotes();
    }
}
