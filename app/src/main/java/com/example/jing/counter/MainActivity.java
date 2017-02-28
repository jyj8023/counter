package com.example.jing.counter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    protected static String TAG = "MainActivity";

    protected EditText edit_num_data;
    protected Button btn_ok;
    protected int i_num_data;

    protected LinearLayout lin_lay_center;
//    protected BaseAdapter adapter;
    private LinkedList<String> mDatas;
    private MyAdapter mAdapter;
    protected ListView mylist;

    protected Button btn_define;
    protected Button btn_clean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_control();

        initData();
        initView();
    }

    private void initData() {
        //设置数据源
        mDatas = new LinkedList<>();
    }

    private void initView() {
        mAdapter = new MyAdapter(this, mDatas);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(mAdapter);
    }

    protected void init_control () {
        edit_num_data = (EditText) findViewById(R.id.edit_num_data);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        lin_lay_center = (LinearLayout) findViewById(R.id.lay_center);
        mylist = (ListView) findViewById(R.id.listview);

        btn_define = (Button) findViewById(R.id.btn_define);
        btn_clean = (Button) findViewById(R.id.btn_clean);

        setOnclickListener();
    }

    protected void setOnclickListener () {
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_Num_data = edit_num_data.getText().toString();
                i_num_data = Integer.parseInt(str_Num_data);

                for (int i = 0; i < i_num_data; i++) {
                    mAdapter.addData("");
                }

                edit_num_data.setEnabled(false);
                btn_ok.setEnabled(false);
            }
        });

        btn_define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListView list = (ListView) findViewById(R.id.listview);
                double[] num = new double[i_num_data];

                int flag = 0;

                for (int i = 0; i < list.getChildCount(); i++) {
                    if ("".equals(mDatas.get(i).toString())) {
                        Log.i(TAG, "onClick: tiaochu");
                        break;
                    } else {
                        flag++;
                    }
                }

                Log.i(TAG, "onClick: flag = " + list.getChildCount());

                if (flag == list.getChildCount()) {
                    for (int i = 0; i < list.getChildCount(); i++) {

                        num[i] = Double.parseDouble(mDatas.get(i));
                        Log.i(TAG, "onClick: " + num[i]);

                    }

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class) ;
                    //需要将数据传入下一个activity
                    Bundle map = new Bundle();
                    map.putSerializable("alistdata", num);
                    intent.putExtra("sd", map);

                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "请输入完整数据！", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_num_data.setText("");

//                mylist.setAdapter(new MyAdapter(this, mDatas));

                initData();
                initView();

                edit_num_data.setEnabled(true);
                btn_ok.setEnabled(true);
            }
        });
    }
}
