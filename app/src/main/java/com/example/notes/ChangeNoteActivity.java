package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import javax.annotation.Nullable;

public class ChangeNoteActivity extends AppCompatActivity implements DeadPicker.DateListener {
    // TODO Переверстать активити
// TODO Добавить выделение просранного дедлайна
    private DataBase dataStorage;

    private EditText title;
    private EditText content;
    private TextView deadline;

    private Button saveBtn;
    private DateFormat dateparser;

    private String id;
    private Note changedNote;
    private long changedTime;
    private ImageButton deadAdd;
    private ImageButton deadDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_note);
        id = getIntent().hasExtra("id") ? getIntent().getStringExtra("id") : "";
        init();
        fillEdits();
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

        deadAdd.setOnClickListener(v-> {
            pickDeadLine();
        });

        deadDelete.setOnClickListener(v-> {
            deadline.setText("");
        });

    }

    private void pickDeadLine() {
        DeadPicker deadPicker = new DeadPicker();
        Bundle args = new Bundle();
        args.putLong("time", changedNote.hasDeadLine() ? changedTime : new Date().getTime());
        deadPicker.setArguments(args);
        deadPicker.show(getSupportFragmentManager(), "picker");
    }

    private void fillEdits() {
        if (!"".equals(id)) {
            Log.d("NOTES", "CAHNGE " + id);
            changedNote = dataStorage.getNote(id);
            changedTime = changedNote.getDeadLine();

            title.setText(changedNote.getTitle());
            content.setText(changedNote.getContent());
            if (changedNote.hasDeadLine()) {
                deadline.setText(dateparser.format(new Date(changedTime)));
            }


        }
    }

    public void getDate(long time) {
        Log.d("NOTES", String.valueOf(time));
        changedTime = time;
        deadline.setText(dateparser.format(new Date(changedTime)));
    }

    private boolean saveNote() {
        String noteTitle = title.getText().toString();
        String noteContent = content.getText().toString();
        String noteDeadline = deadline.getText().toString();

        if (noteDeadline.equals("")) {
            if ("".equals(id)) {
                Note note = new Note(noteTitle, noteContent);
                dataStorage.saveNote(note);
            } else {
                dataStorage.updateNote(changedNote.getId(), noteTitle, noteContent);
            }
        } else {
            Date deadlineTime;
            try {
                deadlineTime = dateparser.parse(noteDeadline);
                if ("".equals(id)) {
                    Note note = new Note(noteTitle, noteContent, deadlineTime.getTime());
                    dataStorage.saveNote(note);
                } else {
                    dataStorage.updateNote(changedNote.getId(), noteTitle, noteContent, deadlineTime.getTime());
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



