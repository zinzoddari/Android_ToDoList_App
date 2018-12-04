package com.example.todolist.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/*import com.google.firebase.database.FirebaseDatabase;*/

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewBtnAdapter extends ArrayAdapter
{
    private ListViewBtnItem item;
    private ArrayList<ListViewBtnItem> items;
    private boolean buttonFlag = false;
    private Button button;
    private EditText editText;
    private ImageView imageView;
    private CheckBox checkBox;
    private ListView listView;
    private ViewHolder holder;
    private int flagInt = 1;
    private boolean flag = false;
    private TextView posId;
    private TextView timeDateText, subjectText;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    // 버튼 클릭 이벤트를 위한 Listener 인터페이스 정의.
    public interface ListBtnClickListener {
        void onListBtnClick(int position) ;
    }

    // 생성자로부터 전달된 resource id 값을 저장.
    int resourceId ;
    // 생성자로부터 전달된 ListBtnClickListener  저장.
    private ListBtnClickListener listBtnClickListener ;


    // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가.
    ListViewBtnAdapter(Context context, int resource, ArrayList<ListViewBtnItem> list){
        super(context, resource, list) ;

        // resource id 값 복사. (super로 전달된 resource를 참조할 방법이 없음.)

        this.resourceId = resource ;
        this.items = list;
    }

    // 새롭게 만든 Layout을 위한 View를 생성하는 코드
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position ;
        final Context context = parent.getContext();
        //final FirebaseDatabase ref = new FirebaseDatabase("https://jacaopensemina.firebaseIO.com/Test");

        // 생성자로부터 저장된 resourceId(listview_btn_item)에 해당하는 Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.before_layout, parent, false);
        }
        editText = (EditText) convertView.findViewById(R.id.editText);
        button = (Button)convertView.findViewById(R.id.plus_btn);
        imageView = (ImageView)convertView.findViewById(R.id.infoBtn);
        listView = (ListView)convertView.findViewById(R.id.listView);
        posId = (TextView)convertView.findViewById(R.id.posId);
        checkBox = (CheckBox)convertView.findViewById(R.id.checkbox);
        subjectText = (TextView)convertView.findViewById(R.id.subjectText);
        timeDateText = (TextView)convertView.findViewById(R.id.timeDateText);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SDfsdfdsfdsf", "야 눌렀다 : " + position);

                if(checkBox.isChecked() == false)
                {
                    Map<String, Object> taskMap = new HashMap<>();
                    taskMap.put("flag", "Y");
                    myRef.child(items.get(position).getPosition()).updateChildren(taskMap);
                }
            }
        });

        if(flag == false)
        {
            if(items != null && flagInt != 0)
            {
                editText.setClickable(false);
                editText.setEnabled(false);
                editText.setFocusable(false);
                //editText.setFocusableInTouchMode(false);
                button.setFocusable(false);
                checkBox.setFocusable(false);
                imageView.setFocusable(false);

                editText.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
                checkBox.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                subjectText.setVisibility(View.VISIBLE);
                timeDateText.setVisibility(View.VISIBLE);

                posId.setText(items.get(position).getPosition());
                subjectText.setText(items.get(position).getSubject());


                long now = System.currentTimeMillis();

                Date date = new Date(now);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String getTime = sdf.format(date);

                if(getTime.equals(items.get(position).getDate()))
                {
                    timeDateText.setText(items.get(position).getTime());
                }
                else
                {
                    timeDateText.setText(items.get(position).getDate());
                }

                flagInt++;
                Log.d("ㅇㄻㄴㄹ", "야111111111111111 : " + items.get(position).getPosition());
            }
            if(flagInt == items.size()+1)
            {
                ListViewBtnItem item = new ListViewBtnItem();
                item.setSubject("");
                items.add(item);
                notifyDataSetChanged();
                editText.setVisibility(View.VISIBLE);

                flagInt = 0;
            }


            int postionTmp = position;
            if(items.get(position).getSubject().equals(""))
            {
                button.setVisibility(View.VISIBLE);
                checkBox.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.VISIBLE);

                flag = true;
            }
        }
        else
        {
            flag = false;
        }

        Log.d("afdsfdsf","야position : " + position);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final Button button1 = (Button)convertView.findViewById(R.id.plus_btn);

        final View finalConvertView = convertView;
        button1.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                if(buttonFlag == false)
                {
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);

                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                    editText.setVisibility(View.VISIBLE);
                    editText.setClickable(true);
                    editText.setEnabled(true);
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();

                    button.setText("OK");

                    buttonFlag = true;
                }
                else if(buttonFlag == true)
                {
                    InputMethodManager immhide = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);

                    immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    editText.setVisibility(View.INVISIBLE);
                    editText.setClickable(false);
                    editText.setEnabled(false);
                    editText.setFocusable(false);
                    //editText.setFocusableInTouchMode(false);
                    button.setFocusable(false);
                    checkBox.setFocusable(false);
                    imageView.setFocusable(false);

                    button.setVisibility(View.INVISIBLE);
                    checkBox.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    ListViewBtnItem item = new ListViewBtnItem();
                    item.setPosition(String.valueOf(position));
                    item.setSubject(editText.getText().toString());

                    Date date = new Date();

                    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

                    Calendar cal = Calendar.getInstance();

                    item.setDate(sdformat.format(cal.getTime()));

                    sdformat = new SimpleDateFormat("HH:mm");
                    cal.add(Calendar.MINUTE, -date.getMinutes());
                    cal.add(Calendar.HOUR, 2);

                    item.setTime(sdformat.format(cal.getTime()));

                    item.setFlag("N");
                    item.setMemo("");

                    items.set(items.size()-1, item);



                    Log.d("sdfasdfsdafsa", "야호호 : " + (items.size()-1));

                    myRef.push().setValue(item);

                    Toast.makeText(context, items.get(items.size()-1).getSubject(), Toast.LENGTH_LONG).show();

                    item.setSubject("a");

                    items.add(item);

                    notifyDataSetChanged();
                    buttonFlag = false;
                }
            }
        });



        return convertView;
    }
}