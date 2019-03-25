package com.djp.administrator.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;


/**
 *利用AsyncTack异步任务
 *okHttpClient作为网络传输
 */

public class AsyncTackActivity extends Activity {


    private OkHttpClient okHttpClient;
    private Button button;
    private ImageView imageView;
    private ProgressDialog progressDialog;//进度对话框
    private final  String IMAGE = "http://www.zt5.com/uploadfile/2019/0127/20190127010113674.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        //弹出要给progressDialog
        progressDialog = new ProgressDialog(AsyncTackActivity.this);
        progressDialog.setTitle("提示信息");
        progressDialog.setMessage("正在下载中请稍后！！");
        //设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
        progressDialog.setCancelable(false);
        //设置ProgressDialog样式为水平
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在UI Thread当中实例化AsyncTask对象，并调用execute方法
                new MyAsyncTack().execute(IMAGE);
            }
        });

    }
    class MyAsyncTack extends AsyncTask<String,Integer,Bitmap >{

        /**
         * 这个方法是在执行异步任务的时候执行
         */
        @Override
        protected void onPreExecute() {
            //在onPreExecute()中我们让ProgressDialog显示出来
            progressDialog.show();

        }
        /**
         * 异步任务执行中
         * @param strings
         * @return
         */
        @Override
        protected Bitmap  doInBackground(String... strings) {

            Bitmap bitmap =  getImageBitmap(IMAGE);
           return  bitmap;

            /*
             //不经常使用HttpClient
            // 通过Apache的HttpClient来访问请求网络中的一张图片
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(strings[0]);
            byte[] image = new  byte[]{};

            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity !=null && httpResponse.getStatusLine().getStatusCode() ==HttpStatus.SC_OK){
                    image = EntityUtils.toByteArray(httpEntity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                httpClient.getConnectionManager().shutdown();
            }*/
            //return image;
        }


        /**
         * 异步任务执行完毕后
         * @param result
         */
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            //更新我们的ImageView控件
            imageView.setImageBitmap(result);
            //使ProgressDialog框消失
            progressDialog.dismiss();
        }
    }

    public Bitmap getImageBitmap(String urlPath){

        Bitmap bitmap = null;
        okHttpClient = new OkHttpClient();
        URL url = null;

        try {
            url = new URL(urlPath);
            //获取请求对象
            Request request = new Request.Builder().url(url).build();
            //获取响应体
            ResponseBody responseBody = okHttpClient.newCall(request).execute().body();
            //获取流—写入数据
            InputStream inputStream = responseBody.byteStream();
            //转化成Bitmap
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bitmap;

    }


}
