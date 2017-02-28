package com.example.jing.counter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.LinkedList;


class MyAdapter extends BaseAdapter {

    private Context mContext;
    //是用链表的List作为数据源的类型，主要是因为该类型有set方法，可以直接修改某位置的数据
    private LinkedList<String> mDatas;

    public MyAdapter(Context context, LinkedList<String> data) {
        this.mContext = context;
        if (data == null) {
            data = new LinkedList<>();
        }
        this.mDatas = data;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //给listview申请空间
    public void addData(String item) {
        mDatas.add(item);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_item_demo, null);
        TextView tvCount = (TextView) convertView.findViewById(R.id.tv_count);
        EditText etContent = (EditText) convertView.findViewById(R.id.et_content);

        etContent.setText((String)getItem(position));
        //给textview设置文本信息
        tvCount.setText("第" + (Integer.parseInt(String.valueOf(position)) + 1) + "个：");
        //给edittext设置文本监听，当文本发生变化时立即保存
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //每当内容改变时，就更新mDatas中的数据
                mDatas.set(position, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return convertView;
    }
}
