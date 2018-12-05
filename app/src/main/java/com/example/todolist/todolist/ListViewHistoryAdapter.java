package com.example.todolist.todolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*import com.google.firebase.database.FirebaseDatabase;*/

public class ListViewHistoryAdapter extends ArrayAdapter
{
    private ArrayList<ListViewBtnItem> items;
    private TextView subjectText, timeDateHistoryText;
    private CheckBox checkBox;
    int resourceId ;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가.
    ListViewHistoryAdapter(Context context, int resource, ArrayList<ListViewBtnItem> list){
        super(context, resource, list) ;
        this.resourceId = resource ;
        this.items = list;
    }

    // 새롭게 만든 Layout을 위한 View를 생성하는 코드
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.after_layout, parent, false);
        }

        subjectText = (TextView)convertView.findViewById(R.id.subjectHistoryText);
        timeDateHistoryText = (TextView)convertView.findViewById(R.id.timeDateHistoryText);
        checkBox = (CheckBox)convertView.findViewById(R.id.checkboxHistory);

        subjectText.setFocusable(false);
        timeDateHistoryText.setFocusable(false);

        checkBox.setChecked(true);

        subjectText.setText(items.get(position).getSubject());


        long now = System.currentTimeMillis();

        Date date = new Date(now);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String getTime = sdf.format(date);

        if(getTime.equals(items.get(position).getDate()))
        {
            timeDateHistoryText.setText(items.get(position).getTime());
        }
        else
        {
            timeDateHistoryText.setText(items.get(position).getDate());
        }

        checkBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(checkBox.isChecked() == false)
                {
                    Map<String, Object> taskMap = new HashMap<>();
                    taskMap.put("flag", "N");
                    myRef.child(items.get(position).getPosition()).updateChildren(taskMap);

                    Log.d("SDfdsfsd" , " ㅇㅇㄴㄹ?ㅇㄴㄹ? : " );

                    notifyDataSetInvalidated();
                }
            }
        });

        return convertView;
    }
}