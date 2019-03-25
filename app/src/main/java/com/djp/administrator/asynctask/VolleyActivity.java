package com.djp.administrator.asynctask;
/**
 * 使用Volley框架StringRequest
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.RequestQueue;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

public class VolleyActivity extends Activity {
    private OkHttpClient okHttpClient;

    private ImageView imageView;
    private Button button;
    com.android.volley.RequestQueue requestQueue;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        //String url = "http://v.juhe.cn/toutiao/index?type=keji&key=65d4c89f2460e131bd8b288f3f70bff6";
                        final String url = "http://www.zt5.com/uploadfile/2019/0127/20190127010113674.jpg";

                        //创建一个请求队列RequestQueue
                        requestQueue = Volley.newRequestQueue(VolleyActivity.this);
                        //创建stringrequest对象
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String s) {





                            }
                        }, new Response.ErrorListener() {
                            //请求失败
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.d("this", "请求错误");

                            }
                        });
                        //将请求对象添加到请求队列中
                        requestQueue.add(stringRequest);





                    }
                }).start();


            }
        });



    }
}
