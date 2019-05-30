package com.example.notes;


import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Note extends RealmObject {

    @PrimaryKey
    @Nullable
    private String id;

    private String title;
    private String content;

    private long changeTime;
    private long deadLine;

    public long getChangeTime() {
        return changeTime;
    }



    public boolean hasDeadLine() {
        return hasDeadLine;
    }

    private boolean hasDeadLine;

    public Note() {

    }


    public Note(String title, String content, long deadLine) {
        this.title = title;
        this.content = content;
        this.changeTime = new Date().getTime();
        this.deadLine = deadLine;
        this.hasDeadLine = true;
        this.id = String.valueOf(Objects.hash(this.changeTime, this.deadLine, this.title));
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.changeTime = new Date().getTime();
        this.deadLine = 0;
        this.hasDeadLine = false;
        this.id = String.valueOf(Objects.hash(this.changeTime, this.title, this.content));
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(!title.equals(this.title)) {
            this.changeTime = new Date().getTime();
            this.title = title;
        }

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if(!content.equals(this.content)) {
            this.changeTime = new Date().getTime();
            this.content = content;
        }


    }

    public long getDeadLine() {
        return deadLine;
    }

    public String getId() {
        return id;
    }

    public void setDeadLine(long deadLine) {
        if(deadLine != this.deadLine) {
            this.changeTime = new Date().getTime();
            this.deadLine = deadLine;
            this.hasDeadLine = true;
        }

    }

    public void deleteDeadLine() {
        this.deadLine = 0;
        this.hasDeadLine = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(title, note.title) &&
                Objects.equals(content, note.content) &&
                Objects.equals(changeTime, note.changeTime) &&
                Objects.equals(deadLine, note.deadLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, changeTime, deadLine);
    }
}
