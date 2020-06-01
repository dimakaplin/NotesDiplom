package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Date;

public class ChangeNoteActivity extends AppCompatActivity implements DeadPicker.DateListener {

    private DataBase dataStorage;

    private EditText title;
    private EditText content;
    private TextView deadline;

    private Button saveBtn;
    private DateFormat dateparser;

    private String id;
    private Note changedNote;
    private long deadLineTime;
    private ImageButton deadAdd;
    private ImageButton deadDelete;

    private boolean wasSaved = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.change_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.back:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_note);
        id = getIntent().hasExtra("id") ? getIntent().getStringExtra("id") : "";
        init();
        fillEdits();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (!wasSaved) {
//            saveNote();
//        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        wasSaved = false;
    }

    private void init() {
        dataStorage = App.getDataStorage();
        dateparser = App.getDatePattern();

        title = findViewById(R.id.title_ch);
        content = findViewById(R.id.content_ch);
        deadline = findViewById(R.id.deadline_ch);
        saveBtn = findViewById(R.id.btn_save);
        deadAdd = findViewById(R.id.add_dead);
        deadDelete = findViewById(R.id.delete_dead);

        saveBtn.setOnClickListener(v -> {
            if (saveNote()) {
                Intent intent = new Intent(ChangeNoteActivity.this, NotesList.class);
                startActivity(intent);
            }
        });

        deadAdd.setOnClickListener(v -> {
            pickDeadLine();
        });

        deadDelete.setOnClickListener(v -> {
            deadline.setText("");
        });

    }

    private void pickDeadLine() {
        DeadPicker deadPicker = new DeadPicker();
        Bundle args = new Bundle();
        if (changedNote != null) {
            args.putLong("time", changedNote.hasDeadLine() ? deadLineTime : new Date().getTime());
        } else {
            args.putLong("time", new Date().getTime());
        }


        deadPicker.setArguments(args);
        deadPicker.show(getSupportFragmentManager(), "picker");
    }

    private void fillEdits() {
        if (!"".equals(id)) {
            Log.d("NOTES", "CAHNGE " + id);
            changedNote = dataStorage.getNote(id);
            deadLineTime = changedNote.getDeadLine();

            title.setText(changedNote.getTitle());
            content.setText(changedNote.getContent());
            if (changedNote.hasDeadLine()) {
                deadline.setText(dateparser.format(new Date(deadLineTime)));
            }


        }
    }

    public void getDate(long time) {
        Log.d("NOTES", String.valueOf(time));
        deadLineTime = time;
        deadline.setText(dateparser.format(new Date(deadLineTime)));
    }

    private boolean saveNote() {
        String noteTitle = title.getText().toString();
        String noteContent = content.getText().toString();
        String noteDeadline = deadline.getText().toString();

        if (noteDeadline.equals("")) {
            if ("".equals(id)) {
                Note note = new Note(noteTitle, noteContent);
                dataStorage.saveNote(note);
                wasSaved = true;
            } else {
                dataStorage.updateNote(changedNote.getId(), noteTitle, noteContent);
                wasSaved = true;
            }
        } else {
            try {
                if ("".equals(id)) {
                    Note note = new Note(noteTitle, noteContent, deadLineTime);
                    dataStorage.saveNote(note);
                    wasSaved = true;
                } else {
                    dataStorage.updateNote(changedNote.getId(), noteTitle, noteContent, deadLineTime);
                    wasSaved = true;
                }

            } catch (Exception e) {
                String message = getText(R.string.check_date_failed).toString();
                Toast.makeText(ChangeNoteActivity.this, message, Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;

    }


}



