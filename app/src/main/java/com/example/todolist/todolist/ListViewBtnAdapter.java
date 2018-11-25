package com.example.todolist.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ListViewBtnAdapter extends ArrayAdapter
{
    private ArrayList<ListViewBtnItem> items;
    private boolean buttonFlag = false;
    private Button button;
    private RadioButton radioButton;
    private EditText editText;

    // 버튼 클릭 이벤트를 위한 Listener 인터페이스 정의.
    public interface ListBtnClickListener {
        void onListBtnClick(int position) ;
    }

    // 생성자로부터 전달된 resource id 값을 저장.
    int resourceId ;
    // 생성자로부터 전달된 ListBtnClickListener  저장.
    private ListBtnClickListener listBtnClickListener ;


    // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가.
    ListViewBtnAdapter(Context context, int resource, ArrayList<ListViewBtnItem> list) {
        super(context, resource, list) ;

        // resource id 값 복사. (super로 전달된 resource를 참조할 방법이 없음.)
        this.resourceId = resource ;
        this.items = list;
    }

    // 새롭게 만든 Layout을 위한 View를 생성하는 코드
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position ;
        final Context context = parent.getContext();

        // 생성자로부터 저장된 resourceId(listview_btn_item)에 해당하는 Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.before_layout, parent, false);
        }

        editText = (EditText)convertView.findViewById(R.id.editText);
        button = (Button)convertView.findViewById(R.id.plus_btn);
        radioButton = (RadioButton)convertView.findViewById(R.id.radionButton);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Button button1 = (Button)convertView.findViewById(R.id.plus_btn);
        button1.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                if(buttonFlag == false)
                {
                    buttonFlag = true;

                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);

                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                    editText.setClickable(true);
                    editText.setEnabled(true);
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();

                    button.setText("OK");
                }
                else if(buttonFlag == true)
                {
                    buttonFlag = false;

                    InputMethodManager immhide = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);

                    immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    editText.setClickable(false);
                    editText.setEnabled(false);
                    editText.setFocusable(false);
                    editText.setFocusableInTouchMode(false);

                    button.setVisibility(View.INVISIBLE);
                    radioButton.setVisibility(View.VISIBLE);

                    ListViewBtnItem item = new ListViewBtnItem();

                    item.setSubject("ddzz");

                    items.add(item);

                    notifyDataSetChanged();
                }
            }
        });
        // button1 클릭 시 TextView(textView1)의 내용 변경.
        return convertView;
    }
}