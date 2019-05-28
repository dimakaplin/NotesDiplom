package com.example.notes;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class DataStorage {
    RealmConfiguration config;

    public DataStorage(Context context) {
        Realm.init(context);
        config = new RealmConfiguration.Builder().name("notes.realm").build();
    }

    public List<Note> getNotes() {
        Realm realm = Realm.getInstance(config);
        List<Note> notes = realm.copyFromRealm(realm.where(Note.class).findAll());
        realm.close();
        return notes;
    }

    public Note getNote(long id) {
        Realm realm = Realm.getInstance(config);
        Note note = realm.where(Note.class).equalTo("id", id).findFirst();
        realm.close();
        return note;
    }

    public void updateNote(Note note) {
        Realm realm = Realm.getInstance(config);
        realm.insertOrUpdate(note);
        realm.close();


    }
}