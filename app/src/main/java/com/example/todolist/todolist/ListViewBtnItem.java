package com.example.todolist.todolist;

import android.widget.EditText;

import java.util.Date;

public class ListViewBtnItem
{
    private String position;
    private String subject;
    private String date;
    private String memo;
    private String flag;

    public void ListViewBtnItem(String position, String subject, String date, String memo, String flag)
    {
        this.position = position;
        this.subject = subject;
        this.date = date;
        this.memo = memo;
        this.flag = flag;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return this.position;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getSubject() {
        return this.subject;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return this.date;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getMemo() {
        return this.memo;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getFlag() {
        return this.flag;
    }
}
