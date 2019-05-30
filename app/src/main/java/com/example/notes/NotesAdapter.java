package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesAdapter extends BaseAdapter {
    final String LOG_TAG = "myLogs";
    Context ctx;
    LayoutInflater lInflater;
    DataBase storage = App.getDataStorage();
    DateFormat dateParser = App.getDatePattern();
    private List<Note> notes;


    public NotesAdapter(Context ctx, List<Note> notes) {
        this.notes = notes;
        this.ctx = ctx;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Note getNote(int position) {
        return ((Note) getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.note_list_elem, parent, false);
        }

        Note note = getNote(position);

        ((TextView) view.findViewById(R.id.title)).setText(note.getTitle());
        ((TextView) view.findViewById(R.id.content)).setText(note.getContent());

        if(note.hasDeadLine()) {
            ((TextView) view.findViewById(R.id.deadline)).setText(dateParser.format(new Date(note.getDeadLine())));
        } else {
            ((TextView) view.findViewById(R.id.deadline)).setText("");
        }

        ((TextView) view.findViewById(R.id.was_changed)).setText(dateParser.format(new Date(note.getChangeTime())));

        ImageButton btnDelete = (ImageButton) view.findViewById(R.id.btn_delete);
        ImageButton btnAdd = (ImageButton) view.findViewById(R.id.btn_add);

        btnDelete.setOnClickListener((v) -> {
            Note deleteNote = this.notes.get((int) v.getTag());

            this.notes.remove((int) v.getTag());
            storage.removeNote(deleteNote);
            notifyDataSetChanged();
        });

        btnAdd.setOnClickListener((v) -> {
            Note changedNote = this.notes.get((int) v.getTag());
            Intent intent = new Intent(ctx, ChangeNoteActivity.class);
            intent.putExtra("id", changedNote.getId());
            Log.d("NOTES", "ADAPTER " + changedNote.getId());
            ctx.startActivity(intent);
        });

        btnDelete.setTag(position);
        btnAdd.setTag(position);
        return view;
    }

}
