package com.example.todolist.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MainDetail extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private ArrayList<ListViewBtnItem> items;
    private ListViewBtnItem item;
    private EditText subjectEdit;
    private TextView dateText, timeText, memo;
    private Calendar cal;
    private String position;
    private String checkFlag;
    private Intent intent;

    private String year, month, day, hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);

        Intent intent = getIntent();

        position = intent.getExtras().getString("position");
        checkFlag = intent.getExtras().getString("checkFlag");
        cal = Calendar.getInstance();

        subjectEdit = (EditText)findViewById(R.id.subjectEdit);
        dateText = (TextView)findViewById(R.id.date);
        timeText = (TextView)findViewById(R.id.time);
        memo = (TextView)findViewById(R.id.memo);

        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String tmp = dataSnapshot.child(position).child("subject").getValue(String.class);
                String date = dataSnapshot.child(position).child("date").getValue(String.class);
                String time = dataSnapshot.child(position).child("time").getValue(String.class);
                String memoStr = dataSnapshot.child(position).child("memo").getValue(String.class);

                dateText.setText(date);
                timeText.setText(time);
                subjectEdit.setText(tmp);
                memo.setText(memoStr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    public void onClick(View v)
    {
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("subject", subjectEdit.getText().toString());
        taskMap.put("date", dateText.getText().toString());
        taskMap.put("time", timeText.getText().toString());
        taskMap.put("memo", memo.getText().toString());
        myRef.child(position).updateChildren(taskMap);
        switch (checkFlag)
        {
            case "Main":
                intent = new Intent(getApplicationContext(),MainActivity.class);
                break;
            case "History":
                intent = new Intent(getApplicationContext(),MainHistory.class);
                break;
            default:
                    break;
        }
        startActivity(intent);
        finish();
    }

    public void mOnDateClick(View v)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                String msg="";
                if(month < 10){
                    //month = Integer.valueOf("0" + (month+1));
                    msg = String.format("%d-0%d-%d", year, month+1, date);
                }
                else if(date < 10){
                    //date = "0" + date;
                    msg = String.format("%d-%d-0%d", year, month+1, date);
                }
                else if(date < 10 && month < 10){
                    //date = "0" + date;
                    msg = String.format("%d-0%d-0%d", year, month+1, date);
                }
                else
                {
                    msg = String.format("%d-%d-%d", year, month+1, date);
                }
                dateText.setText(msg);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

        //dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
        dialog.show();
    }

    public void mOnTimeClick(View v) {
        TimePickerDialog dialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {

                int time=(min * 60 + hour * 60 * 60) * 1000;
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String formatted = format.format(time);

                timeText.setText(formatted);
            }
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지

        dialog.show();
    }
}
