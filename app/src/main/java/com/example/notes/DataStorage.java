package com.example.notes;

import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.RealmSchema;
import io.realm.Sort;


public class DataStorage implements DataBase {
    RealmConfiguration config;
;

    public DataStorage(Context context) {
        Realm.init(context);
        config = new RealmConfiguration.Builder().name("notes.realm").build();

    }

    public List<Note> getNotes() {
        Realm realm = Realm.getInstance(config);
        realm.beginTransaction();
        List<Note> notes = realm.copyFromRealm(realm.where(Note.class).findAll());
        realm.commitTransaction();
        realm.close();

        return notes;
    }

    public List<Note> getNotes(String sortType, boolean desc) {
        Log.d("NOTES", String.valueOf(desc));
        Realm realm = Realm.getInstance(config);
        realm.beginTransaction();
        RealmResults<Note> results = desc ? realm.where(Note.class).findAll().sort(sortType, Sort.DESCENDING) :
                realm.where(Note.class).findAll().sort(sortType, Sort.ASCENDING);
        List<Note> notes = realm.copyFromRealm(results);
        realm.commitTransaction();
        realm.close();

        Log.d("NOTES", "sorted with" + notes.get(0).getTitle());

        return notes;
    }

    public Note getNote(String id) {
        Log.d("NOTES", "asdasdasd "+ id);
        Realm realm = Realm.getInstance(config);
        realm.beginTransaction();
        Note realmNote = realm.where(Note.class).equalTo("id", id).findFirst();
        Log.d("NOTES", realmNote.getTitle() + " "+ id);
        Note note = realm.copyFromRealm(realmNote);
        realm.commitTransaction();
        realm.close();
        return note;
    }

    public void saveNote(Note note) {
        Realm realm = Realm.getInstance(config);
        realm.beginTransaction();
        realm.insertOrUpdate(note);
        realm.commitTransaction();
        realm.close();
    }

    public void updateNote(String id, String title, String content, long deadline) {
        Log.d("NOTES", "update "+ id);
        Realm realm = Realm.getInstance(config);
        realm.beginTransaction();
        Note realmNote = realm.where(Note.class).equalTo("id", id).findFirst();
        realmNote.setTitle(title);
        realmNote.setContent(content);
        realmNote.setDeadLine(deadline);
        realm.commitTransaction();
        realm.close();
    }

    public void updateNote(String id, String title, String content) {
        Log.d("NOTES", "update "+ id);
        Realm realm = Realm.getInstance(config);
        realm.beginTransaction();
        Note realmNote = realm.where(Note.class).equalTo("id", id).findFirst();
        realmNote.deleteDeadLine();
        realmNote.setTitle(title);
        realmNote.setContent(content);
        realm.commitTransaction();
        realm.close();
    }




    public void removeNote(Note note) {
        Realm realm = Realm.getInstance(config);
        realm.beginTransaction();
        RealmResults<Note> rows= realm.where(Note.class).equalTo("id", note.getId()).findAll();
        rows.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }
}