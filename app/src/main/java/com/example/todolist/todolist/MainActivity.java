package com.example.todolist.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private boolean buttonFlag = false;
    private EditText editText;
    private Button button;
    private RadioButton radioButton;
    private ListViewBtnAdapter adapter;
    private ListViewBtnItem item;
    private ArrayList<ListViewBtnItem> items;
    private ListView listview;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.navigation_hide:
                    Intent intent = new Intent(getApplicationContext(),MainHistory.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        items = new ArrayList<ListViewBtnItem>() ;

        // items 로드.
        loadItemsFromDB(items) ;

        // Adapter 생성
        adapter = new ListViewBtnAdapter(this, R.layout.before_layout, items) ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);

        editText = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.plus_btn);
        radioButton = (RadioButton)findViewById(R.id.radionButton);

        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object o = listview.getItemAtPosition(position);
            }
        });
    }

    public boolean loadItemsFromDB(ArrayList<ListViewBtnItem> list) {
        ListViewBtnItem item;
        int i;

        if (list == null) {
            list = new ArrayList<ListViewBtnItem>();
        }

        // 순서를 위한 i 값을 1로 초기화.
        i = 1;

        // 아이템 생성.
        item = new ListViewBtnItem();
        item.setSubject("ddd");
        list.add(item);
        i++;

        return true;
    }
}
