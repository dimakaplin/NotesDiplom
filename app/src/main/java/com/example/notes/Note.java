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

    private Date changeTime;
    private Date deadLine;

    public Date getChangeTime() {
        return changeTime;
    }



    public boolean hasDeadLine() {
        return hasDeadLine;
    }

    private boolean hasDeadLine;

    public Note() {

    }


    public Note(String title, String content, Date deadLine) {
        this.title = title;
        this.content = content;
        this.changeTime = new Date();
        this.deadLine = deadLine;
        this.hasDeadLine = true;
        this.id = String.valueOf(Objects.hash(this.changeTime.getTime(), this.deadLine, this.title));
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.changeTime = new Date();
        this.deadLine = null;
        this.hasDeadLine = false;
        this.id = String.valueOf(Objects.hash(this.changeTime.getTime(), this.title, this.content));
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(!title.equals(this.title)) {
            this.changeTime = new Date();
            this.title = title;
        }

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if(!content.equals(this.content)) {
            this.changeTime = new Date();
            this.content = content;
        }


    }

    public Date getDeadLine() {
        return deadLine;
    }

    public String getId() {
        return id;
    }

    public void setDeadLine(Date deadLine) {
        if(!deadLine.equals(this.deadLine)) {
            this.changeTime = new Date();
            this.deadLine = deadLine;
            this.hasDeadLine = true;
        }

    }

    public void deleteDeadLine() {
        this.deadLine = null;
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
