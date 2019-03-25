package com.djp.administrator.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 利用AsyncTask实现倒计时
 */
public class MainActivity extends AppCompatActivity {
    private EditText et;
    private TextView tv;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindID();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = Integer.parseInt(et.getText().toString());
                new MyTask().execute(time);
            }
        });
    }

    private void bindID() {
        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);
        bt = findViewById(R.id.bt);

    }

    /**
     * AsyncTask的泛型参数
     * 参数一：Params: 启动任务执行的输入参数。
     * 参数二：Progress: 后台任务执行的百分比。
     * 参数三：Result: 后台执行任务最终返回的结果。
     */
    class  MyTask extends AsyncTask<Integer,Integer,String>{

        /**
         * 此方法运行子线程中，比较耗时的操作方法在此方法中执行。
         * @param integers
         * @return
         */
        @Override
        protected String doInBackground(Integer... integers) {
            for (int i = integers[0];i>0;i--){

                try {
                    Thread.sleep(1000);
                    publishProgress(i);//用于触发onProgressUpdate方法
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return "计时结束";
        }

        /**
         * 显示当前进度，适用于下载或扫描这类需要实时显示进度的需求。
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tv.setText(values[0]+"");//+""表示转换为字符串
        }

        /**
         *异步任务执行完成后，调用此方法，运行在主线程，可以修改控件状态。例如：下载完成
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            tv.setText(s);
        }
    }
}
