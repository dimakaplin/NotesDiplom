package com.example.notes;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DataStorage {
    private RealmConfiguration mRealmConfiguration;  
// TODO конфигурацию реалм напиши
    public DataStorage(Context context) { 
        mRealmConfiguration = new RealmConfiguration();
        mRealmConfiguration.Builder(context).name("myOtherRealm.realm").build(); 
    }  

    public List<Note> getNotes() { 
        Realm realm = Realm.getInstance(mRealmConfiguration); 
        List<Note> notes = realm.copyFromRealm(realm.where(Note.class).findAll()); 
        realm.close(); 
        return notes; 
    }  

    public Note getNote(long id) { 
        Realm realm = Realm.getInstance(mRealmConfiguration); 
        Note note;
        Note realmNote = realm.where(Note.class).equalTo("id", id).findFirst(); 
        if (realmNote != null) { 
            note = realm.copyFromRealm(realmNote); 
        } 
        realm.close(); 
        return note; 
    }  


    public void updateNote(Note note) { 
            Realm realm = Realm.getInstance(mRealmConfiguration); 
            realm.insertOrUpdate(note); 
            realm.close(); 
            Realm.compactRealm(mRealmConfiguration); 

    } 
}
