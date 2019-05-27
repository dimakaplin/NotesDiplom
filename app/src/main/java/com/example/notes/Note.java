package com.example.notes;


import java.util.Date;
import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Note extends RealmObject {
    @PrimaryKey
    @Required
    int id;

    String title;
    String content;
    Date createdTime;
    Date changeTime;
    Date deadLine;


    public Note(String title, String content, Date deadLine) {
        this.title = title;
        this.content = content;
        this.createdTime = new Date();
        this.changeTime = new Date();
        this.deadLine = deadLine;
        this.id = Objects.hash(this.createdTime, this.deadLine, this.title);
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdTime = new Date();
        this.changeTime = new Date();
        this.deadLine = null;
        this.id = Objects.hash(this.createdTime, this.title);
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

    public void setDeadLine(Date deadLine) {
        if(!deadLine.equals(this.deadLine)) {
            this.changeTime = new Date();
            this.deadLine = deadLine;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(title, note.title) &&
                Objects.equals(content, note.content) &&
                Objects.equals(createdTime, note.createdTime) &&
                Objects.equals(changeTime, note.changeTime) &&
                Objects.equals(deadLine, note.deadLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, createdTime, changeTime, deadLine);
    }
}
