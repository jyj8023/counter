package com.example.jing.counter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by 62362 on 2017/2/8.
 */

public class SecondActivity extends Activity {

    private static String TAG = "MainActivity";

    private Button[] btn_operate;

    private double d_Num_data[];

    private TextView text_Result;
    private String str_text_Result;
    private double sum;
    private double average;
    private double variance;
    private double standardDeviation;
    private String result;

    private LinearLayout line;

    private LinkedList<String> mDatas;
    private MyAdapter mAdapter;

    double[] num2;
    int i_num_data;

    ListView listView;

    private Button btn_clean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page);

        initData();
        initView();

        init_Control();


    }

    private void initData() {
        //设置数据源
        mDatas = new LinkedList<>();
    }

    private void initView() {
        mAdapter = new MyAdapter(this, mDatas);
        listView = (ListView) findViewById(R.id.mylist);
        listView.setAdapter(mAdapter);

    }

    protected void init_Control() {
        btn_operate = new Button[8];

        btn_operate[0] = (Button) findViewById(R.id.btn_average);
        btn_operate[1] = (Button) findViewById(R.id.btn_variance);
        btn_operate[2] = (Button) findViewById(R.id.btn_Standard_Deviation);
        btn_operate[3] = (Button) findViewById(R.id.btn_zhuchafa);

        line = (LinearLayout) findViewById(R.id.line);
        line.setOrientation(LinearLayout.VERTICAL);

        d_Num_data = (double[])this.getIntent().getBundleExtra("sd").getSerializable("alistdata");

        text_Result = (TextView) findViewById(R.id.text_result);
        text_Result.setTextSize(20);

//        btn_clean = (Button) findViewById(R.id.clean_sweap);

//        btn_clean.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                line.removeAllViews();
//
//                listView.setAdapter(null);
//            }listView.setAdapter(new MyAdapter(SecondActivity.this, mDatas));
//        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_average:
                line.removeAllViews();
//                listView.setAdapter(null);
//                listView.removeAllViews();
                clean_adapter();

                if (d_Num_data.length == 1) {
                    result = d_Num_data[0] + "/" + d_Num_data.length + "=" + d_Num_data[0];
                    text_Result.setText(result + "\n均值为：" + getAverage());
                    break;
                }
                result = "(" + d_Num_data[0] + " + ";
                for (int i = 1; i < d_Num_data.length; i++) {
                    if (i == d_Num_data.length - 1) {
                        result = result + d_Num_data[i] + ")/" + d_Num_data.length + " = "+getAverage();
                    } else {
                        result = result + d_Num_data[i] + " + ";
                    }
                }
                text_Result.setText(result + "\n均值为：" + getAverage());

                line.addView(text_Result);
                break;
            case R.id.btn_variance:
//                listView.setAdapter(null);
                line.removeAllViews();
//              清空listview
                clean_adapter();

                if (d_Num_data.length == 1) {
                    result = d_Num_data[0] + "-" + d_Num_data[0] + "= 0";
                    text_Result.setText(result + "\n方差为：" + getVariance());
                    break;
                }
                result = "((" + d_Num_data[0] + " - ";
                average = getAverage();
                for (int i = 1; i < d_Num_data.length; i++) {
                    if (i == d_Num_data.length - 1) {
                        result = result + average + ")^2)/" + d_Num_data.length + " = " + getVariance();
                    } else {
//                        Log.i(TAG, "onClick: " + (i+1));
                        result = result + average + ")^2 + (" + d_Num_data[i+1] + " - ";
                    }
                }
                text_Result.setText(result + "\n方差为：" + getVariance());

                line.addView(text_Result);
                break;
            case R.id.btn_Standard_Deviation:
//                listView.setAdapter(null);
                line.removeAllViews();
//                listView.removeAllViews();
                clean_adapter();

                if (d_Num_data.length == 1) {
                    result = d_Num_data[0] + "-" + d_Num_data[0] + "= 0";
                    text_Result.setText(result + "\n标准差为：" + getVariance());
                    break;
                }
                result = "sqrt(((" + d_Num_data[0] + " - ";
                average = getAverage();
                for (int i = 1; i < d_Num_data.length; i++) {
                    if (i == d_Num_data.length - 1) {
                        result = result + average + ")^2)/" + d_Num_data.length + ") = " + getStandardDeviation();
                    } else {
//                        Log.i(TAG, "onClick: " + (i+1));
                        result = result + average + ")^2 + (" + d_Num_data[i+1] + " - ";
                    }
                }
                text_Result.setText(result + "\n标准差：" + getStandardDeviation());

                line.addView(text_Result);
                break;
            case R.id.btn_zhuchafa:
//                listView.setAdapter(null);
//                listView.removeAllViews();
                clean_adapter();
                final TextView textView = new TextView(SecondActivity.this);
                textView.setTextSize(25);
                line.removeAllViews();
                text_Result.setText("");

                TextView text = new TextView(SecondActivity.this);
                text.setText("请输入步长：");
                text.setTextSize(20);
                text.setTextColor(Color.WHITE);
                line.addView(text);

                final EditText edit = new EditText(SecondActivity.this);
                edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                edit.setHint("请输入步长值");
                line.addView(edit);

                Button btn_zhuchaOk = new Button(SecondActivity.this);
                btn_zhuchaOk.setText("确定");
                line.addView(btn_zhuchaOk);

                final double[] temp_num = new double[d_Num_data.length];

                for (int i = 0; i < d_Num_data.length; i++) {
                    temp_num[i] = d_Num_data[i];
                }

                Arrays.sort(temp_num);

                btn_zhuchaOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        result = "";
                        double buchang = Double.parseDouble(edit.getText().toString());

                        double[] temp = new double[d_Num_data.length / 2];
                        for (int i = 0; i < temp.length; i++) {
                            result = result + temp_num[i + temp.length] + "-" + temp_num[i] + "=" + (temp_num[i + temp.length]-temp_num[i]) + "\n";
                        }
                        result = result + "以上各值平方求和/" + d_Num_data.length / 2 * buchang + "=" + getZhuchafa(buchang);

                        textView.setText(result + "\n逐差法：" + getZhuchafa(buchang));

                        line.addView(textView);

                        edit.setEnabled(false);
                    }
                });

                line.addView(text_Result);
                break;
            case R.id.btn_zuixiaoercheng:
                clean_adapter();
                line.removeAllViews();

                i_num_data = d_Num_data.length;

                //初始化myadapter中的linkedlist
                for (int i = 0; i < i_num_data; i++) {
                    mAdapter.addData("");
                }

                final Button btn_ok = new Button(this);
                btn_ok.setText("确定");
                line.addView(btn_ok);

                num2 = new double[d_Num_data.length];

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        line.removeAllViews();
                        line.addView(btn_ok);

                        ListView list = (ListView) findViewById(R.id.mylist);

                        int flag = 0;

                        for (int i = 0; i < list.getChildCount(); i++) {
                            if ("".equals(mDatas.get(i).toString())) {
                                break;
                            } else {
                                flag++;
                            }
                        }

                        if (flag == list.getChildCount()) {
                            for (int i = 0; i < list.getChildCount(); i++) {
//                            LinearLayout layout = (LinearLayout) list.getChildAt(i);
//                            EditText et = (EditText) layout.getChildAt(1);
//
//                            num[i] = mDatas.get(i);
//
//                            num[i] = Double.parseDouble(et.getText().toString());

                                num2[i] = Double.parseDouble(mDatas.get(i));
                                Log.i(TAG, "onClick: " + Double.parseDouble(mDatas.get(i)));
                            }

                            double sum_xy = 0;
                            double sum_x = 0;
                            double sum_y = 0;
                            double sum_x2 = 0;
                            double sum_sum_x2 = 0;

                            for (int i = 0; i < i_num_data; i++) {
                                sum_xy += (d_Num_data[i] * num2[i]);

                                sum_x += d_Num_data[i];
                                sum_y += num2[i];

                                sum_x2 += d_Num_data[i] * d_Num_data[i];
                            }

                            str_text_Result = "Σxy = " + sum_xy + "\n";
                            str_text_Result = str_text_Result + "ΣxΣy = " + (sum_x * sum_y) + "\n";
                            str_text_Result = str_text_Result + "Σx² = " + sum_x2 + "\n";
                            str_text_Result = str_text_Result + "(Σx)² = " + sum_x*sum_x + "\n";

                            double rea = (sum_xy - sum_x * sum_y / i_num_data) / (sum_x2 - sum_x * sum_x / i_num_data);
                            double reb = sum_y / i_num_data - rea * sum_x / i_num_data;

                            str_text_Result = str_text_Result + "a = " + rea + "\n";
                            str_text_Result = str_text_Result + "b = " + reb;

                            text_Result.setText(str_text_Result);

                            line.addView(text_Result);
                        } else {
                            Toast.makeText(SecondActivity.this, "请输入完整数据！", Toast.LENGTH_LONG).show();
                        }


                    }
                });



                break;
//            case R.id.btn_errors:
//                break;
        }

    }

    protected double getSum() {
        sum = 0;
        for (int i = 0; i < d_Num_data.length; i++) {
            sum += d_Num_data[i];
        }
        return sum;
    }

    protected double getAverage() {
        average = getSum() / d_Num_data.length;
        return average;
    }

    protected double getVariance() {
        double result = 0;
        double d_temp;
        getAverage();
        for (int i = 0; i < d_Num_data.length; i++) {
            d_temp = d_Num_data[i] - average;
            d_temp = Math.pow(d_temp, 2);
            result += d_temp;
        }
        variance = result / d_Num_data.length;
        return variance;
    }

    protected double getStandardDeviation() {
        standardDeviation = Math.sqrt(getVariance());
        return standardDeviation;
    }

    protected double getZhuchafa(double len) {
        double[] temp_num = new double[d_Num_data.length];

        for (int i = 0; i < d_Num_data.length; i++) {
            temp_num[i] = d_Num_data[i];
        }

        Arrays.sort(temp_num);

        double[] temp = new double[d_Num_data.length / 2];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp_num[i + temp.length] - temp_num[i];
        }
        double jiange = d_Num_data.length / 2 * len;
        double sum_result = 0;
        for (int i = 0; i < temp.length; i++) {
            sum_result += Math.pow(temp[i], 2);
        }

        return sum_result / Math.pow(jiange, 2);
    }

    protected void clean_adapter() {
        initData();
        initView();
    }
}
