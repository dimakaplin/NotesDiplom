package com.example.notes;

import android.app.Application;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class App extends Application {
    private static DataBase dataStorage;
    private static KeyStore keystore;
    private static DateFormat dateParser;
    @Override
    public void onCreate() {
        super.onCreate();

        /* Конкретная реализация выбирается только здесь.
           Изменением одной строчки здесь,
           мы заменяем реализацию во всем приложении!
        */

        dataStorage = new DataStorage(this);
        keystore = new KeyCheck(this);
        dateParser = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    }
    // Возвращаем интерфейс, а не конкретную реализацию!
    public static DataBase getDataStorage() {
        return dataStorage;
    }
    // Возвращаем интерфейс, а не конкретную реализацию!
    public static KeyStore getKeystore() {
        return keystore;
    }
    public static DateFormat getDatePattern() {
        return dateParser;
    }
}
