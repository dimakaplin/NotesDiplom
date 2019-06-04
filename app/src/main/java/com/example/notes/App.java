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
    private static DateFormat dateToDay;

    @Override
    public void onCreate() {
        super.onCreate();


        try {
            keystore = new KeyCheck(this);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataStorage = new DataStorage(this);
        dateParser = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dateToDay = new SimpleDateFormat("dd-MM-yyyy");
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

    public static DateFormat getDateToDay() {
        return dateToDay;
    }
}
