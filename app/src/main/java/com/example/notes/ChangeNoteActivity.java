package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import javax.annotation.Nullable;

public class ChangeNoteActivity extends AppCompatActivity {

    private DataBase dataStorage;

    private EditText title;
    private EditText content;
    private EditText deadline;

    private Button saveBtn;
    private DateFormat dateparser;

    private String id;
    private Note changedNote;

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

        saveBtn.setOnClickListener(v-> {
            saveNote();
        });

    }

    private void fillEdits() {
        if(!"".equals(id)) {
            Log.d("NOTES","CAHNGE " + id);
            changedNote = dataStorage.getNote(id);

            title.setText(changedNote.getTitle());
            content.setText(changedNote.getContent());
            if(changedNote.hasDeadLine()) {
                deadline.setText(dateparser.format(changedNote.getDeadLine()));
            }



        }
    }

    private void saveNote() {
        String noteTitle = title.getText().toString();
        String noteContent = content.getText().toString();
        String noteDeadline = deadline.getText().toString();

        if(noteDeadline.equals("")) {
            if("".equals(id)) {
                Note note = new Note(noteTitle, noteContent);
                dataStorage.updateNote(note);
            } else {
                changedNote.setTitle(noteTitle);
                changedNote.setContent(noteContent);
            }
        } else {
            Date deadlineTime;
            try{
                deadlineTime = dateparser.parse(noteDeadline);
                if("".equals(id)) {
                    Note note = new Note(noteTitle, noteContent, deadlineTime);
                    dataStorage.updateNote(note);
                } else {
                    changedNote.setTitle(noteTitle);
                    changedNote.setContent(noteContent);
                    changedNote.setDeadLine(deadlineTime);
                }

            }catch(Exception e){
                String message = getText(R.string.check_date_failed).toString();
                Toast.makeText(ChangeNoteActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

    }


}
