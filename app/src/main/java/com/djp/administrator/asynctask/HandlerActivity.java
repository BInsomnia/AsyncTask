package com.djp.administrator.asynctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 利用Handler消息传递机制
 * HttpURLConnection作为网络传输
 */
public class HandlerActivity extends Activity {

    private Button button;
    private ImageView imageView;
    private Handler handler = new Handler(){
        //此方法在主线程中使用，用于刷新UI

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //将Image对象显示到图片里面去
                    imageView.setImageBitmap((Bitmap) msg.obj);
                    break;
                case  2:
                    Toast.makeText(HandlerActivity.this,"请求失败",Toast.LENGTH_SHORT).show();

            }
        }
    };
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        initView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new Thread(){
                    @Override
                    public void run() {
                        try {
                            String path = "http://www.zt5.com/uploadfile/2019/0127/20190127010113674.jpg";
                            //将网站封装成一个URL对象
                            URL url = new URL(path);
                            //获取客户端与服务器连接的对象，此时还未产生连接
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            //对连接对象初始化，建立连接对象的初始化，客户端向服务器端发送的请求
                            //设置请求方法,注意大写，客户端向服务器请求数据总共就两个，一个get，一个post，提交数据的时候用post
                            connection.setRequestMethod("GET");
                            //设置建立链接超时
                            connection.setConnectTimeout(5000);
                            //设置读取超时
                            connection.setReadTimeout(8000);
                            //发送1请求与服务器建立连接
                            connection.connect();
                            //这是服务器的请求码，如果响应为200，说明请求成功
                            if (connection.getResponseCode()==200){
                                //获取输入流，读取服务器的流，服务器与客户端是通过流的方式进行交互的，服务器通过流写给客户端
                                InputStream inputStream  = connection.getInputStream();
                                //转换图片格式
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                                //将消息发送给消息队列
                                Message msg = Message.obtain();
                                //消息对象可以携带数据，如果携带多个对象，那么Obj设置成一个数组就可
                                        msg.obj=bitmap;
                                        msg.what=1;
                                        handler.sendMessage(msg);
                            }else {
                                        Message msg = Message.obtain();
                                        msg.what = 2;
                                        handler.sendMessage(msg);
                            }

                        }  catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }.start();

            }
        });
    }

    public void initView(){
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
    }


}
