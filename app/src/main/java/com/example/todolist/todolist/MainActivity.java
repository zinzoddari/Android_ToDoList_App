package com.example.todolist.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.LongDef;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private boolean buttonFlag = false;
    private EditText editText;
    private Button button;
    private RadioButton radioButton;
    private ListViewBtnAdapter adapter;
    private ListViewBtnItem item;
    private ArrayList<ListViewBtnItem> items;
    private ListView listview;
    private ImageView imageView;
    private String subject;
    private Context context;
    private boolean flag = false;
    private TextView countText;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(getApplicationContext(), MainHistory.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        context = getApplicationContext();

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.plus_btn);
        imageView = (ImageView) findViewById(R.id.infoBtn);
        listview = (ListView) findViewById(R.id.listView);
        countText = (TextView)findViewById(R.id.countText);

        if (items == null) {
            items = new ArrayList<>();
        }
        Date date = new Date();

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();

        Log.d("Sdffsfsd", "야 ㄴㅇㄹㅇㄴ" + sdformat.format(cal.getTime()));

        myRef.orderByChild("flag").equalTo("N").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                if (dataSnapshot.getChildrenCount() == 0) {
                    item = new ListViewBtnItem();
                    item.setSubject("");
                    items.add(item);

                    adapter = new ListViewBtnAdapter(context, R.layout.before_layout, items);

                    listview.setAdapter(adapter);
                }
                else
                {
                    item = new ListViewBtnItem();
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        item = new ListViewBtnItem();

                        Log.d("SDfasdafDS", "양 : " + child.getKey());

                        item.setSubject(dataSnapshot.child(child.getKey()).child("subject").getValue(String.class));
                        item.setMemo(dataSnapshot.child(child.getKey()).child("memo").getValue(String.class));
                        item.setDate(dataSnapshot.child(child.getKey()).child("date").getValue(String.class));
                        item.setTime(dataSnapshot.child(child.getKey()).child("time").getValue(String.class));
                        item.setFlag(dataSnapshot.child(child.getKey()).child("flag").getValue(String.class));
                        item.setPosition(child.getKey());

                        Log.d("SDfasdafDS", "양 : " + item.getSubject());

                        items.add(item);
                    }
                        adapter = new ListViewBtnAdapter(context, R.layout.before_layout, items);

                        listview.setAdapter(adapter);

                        countText.setText(dataSnapshot.getChildrenCount() + " items due today");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), MainDetail.class);
                TextView tmp = ((TextView) view.findViewById(R.id.posId));
                String editTextTmp = tmp.getText().toString();

                intent.putExtra("position", editTextTmp);
                intent.putExtra("checkFlag", "Main");
                startActivity(intent);
            }
        });
    }
}
