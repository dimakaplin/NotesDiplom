package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

// TODO Сделать удаление элемента через алерт
public class NotesList extends AppCompatActivity {

    private List<Note> notes = new ArrayList<>();
    private DataBase dataStorage;

    private ListView list;
    private NotesAdapter notesAdapter;

    private ImageButton dateSort;
    private ImageButton deadlineSort;
    private ImageButton alphaSort;
    private ImageView dirSort;
    private String typeSort = "changeTime";

    private boolean desc= true;


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

        dirSort = findViewById(R.id.dir_sort);

        dateSort = findViewById(R.id.date_sort);
        deadlineSort = findViewById(R.id.deadlne_sort);
        alphaSort = findViewById(R.id.alpha_sort);


        dateSort.setOnClickListener(v-> clickSort(v));
        deadlineSort.setOnClickListener(v-> clickSort(v));
        alphaSort.setOnClickListener(v-> clickSort(v));

    }

    private void clickSort(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.date_sort:
                desc = typeSort.equals("changeTime") != desc;
                typeSort = "changeTime";
                dateSort.setBackgroundResource(R.drawable.low_green_circle);
                break;
            case R.id.deadlne_sort:
                desc = typeSort.equals("deadLine") != desc;
                typeSort = "deadLine";
                break;
            case R.id.alpha_sort:
                desc = typeSort.equals("title") != desc;
                typeSort = "title";
                break;
        }
        setBackgroundCircle();


        dirSort.setImageResource(desc ? R.drawable.up_sort : R.drawable.down_sort);

        notes.clear();
        notes.addAll(dataStorage.getNotes(typeSort, desc));
        notesAdapter.notifyDataSetChanged();


    }

    private void setBackgroundCircle() {
        switch (typeSort) {
            case "changeTime":
                dateSort.setBackgroundResource(R.drawable.low_green_circle);
                deadlineSort.setBackgroundResource(R.drawable.white_circle);
                alphaSort.setBackgroundResource(R.drawable.white_circle);
                break;
            case "deadLine":
                dateSort.setBackgroundResource(R.drawable.white_circle);
                deadlineSort.setBackgroundResource(R.drawable.low_green_circle);
                alphaSort.setBackgroundResource(R.drawable.white_circle);
                break;
            case "title":
                dateSort.setBackgroundResource(R.drawable.white_circle);
                deadlineSort.setBackgroundResource(R.drawable.white_circle);
                alphaSort.setBackgroundResource(R.drawable.low_green_circle);
                break;
        }
    }


    private void fillNotesList() {
        notes = dataStorage.getNotes();
    }
}
