package com.example.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class NotesAdapter extends BaseAdapter {
    final String LOG_TAG = "myLogs";
    final String HAS_TIME = "has_time";
    final String TODAY = "today";
    final String FAIL = "fail";
    Context ctx;
    LayoutInflater lInflater;
    DataBase storage = App.getDataStorage();
    DateFormat dateParser = App.getDatePattern();
    private List<Note> notes;
    DateFormat dateToDay = App.getDateToDay();
    AlertDialog.Builder ad;
    String deleteMessage;

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
        Note note = getNote(position);

        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.note_list_elem, parent, false);
        }

        TextView title = view.findViewById(R.id.title);
        TextView content = view.findViewById(R.id.content);
        TextView deadline = view.findViewById(R.id.deadline);
        TextView wasChanged = view.findViewById(R.id.was_changed);

        title.setText(note.getTitle());
        content.setText(note.getContent());


        if (!note.hasDeadLine()) {
            deadline.setText("");
            deadline.setBackgroundResource(R.color.white);
            deadline.getBackground().setAlpha(0);
        } else {
            deadline.setText(dateParser.format(new Date(note.getDeadLine())));
            String deadlineCheckingResult = checkDeadLineStroke(note);
            switch (deadlineCheckingResult) {
                case TODAY:
                    deadline.setBackgroundResource(R.drawable.orange_card);
                    deadline.getBackground().setAlpha(100);
                break;
                case HAS_TIME:
                    deadline.setBackgroundResource(R.color.white);
                    deadline.getBackground().setAlpha(0);
                    break;
                case FAIL:
                    deadline.setBackgroundResource(R.drawable.red_card);
                    deadline.getBackground().setAlpha(100);
                    break;
            }
        }

        wasChanged.setText(dateParser.format(new Date(note.getChangeTime())));

        ImageButton btnDelete = view.findViewById(R.id.btn_delete);
        ImageButton btnAdd = view.findViewById(R.id.btn_add);

        btnDelete.setOnClickListener((v) -> {
            initDeleteDialog(v);
            ad.show();
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

    private void initDeleteDialog(View v) {
        Note deleteNote = notes.get((int) v.getTag());
        ad = new AlertDialog.Builder(ctx);
        deleteMessage = ctx.getString(R.string.delete_req);
        ad.setMessage(deleteMessage + " \"" + deleteNote.getTitle() + "\" " + "?");
        ad.setPositiveButton(R.string.delete_yes, ((DialogInterface dialog, int arg1) -> {

            notes.remove((int) v.getTag());
            storage.removeNote(deleteNote);
            notifyDataSetChanged();
            dialog.cancel();
        }));

        ad.setNegativeButton(R.string.delete_no, ((DialogInterface dialog, int arg1) -> {
            dialog.cancel();
        }));
    }

    private String checkDeadLineStroke(Note note) {
        String result;
        if (note.getDeadLine() > new Date().getTime() && dateToDay.format(new Date(note.getDeadLine())).equals(dateToDay.format(new Date().getTime()))) {
            result = TODAY;
        } else if (note.getDeadLine() > new Date().getTime()) {
            result = HAS_TIME;
        } else {
            result = FAIL;
        }
        return result;
    }

}
