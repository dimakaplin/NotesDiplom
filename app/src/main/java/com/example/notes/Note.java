package com.example.notes;

import java.util.Date;

public class Note {
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
}
