package com.example.notes;

import android.app.Application;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class App extends Application {
    private static DataBase dataStorage;
    private static KeyStore keystore;
    private static DateFormat dateParser;

    @Override
    public void onCreate() {
        super.onCreate();

        dataStorage = new DataStorage(this);
        try {
            keystore = new KeyCheck(this);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dateParser = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    }


    public static DataBase getDataStorage() {
        return dataStorage;
    }

    public static KeyStore getKeystore() {
        return keystore;
    }

    public static DateFormat getDatePattern() {
        return dateParser;
    }
}
