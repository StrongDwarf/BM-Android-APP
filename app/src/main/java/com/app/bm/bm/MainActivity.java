package com.app.bm.bm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}
*/
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bm.bm.common.Ajax;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Function;

import static com.app.bm.bm.common.Services.getImage;

public class MainActivity extends AppCompatActivity {
    private String str;
    private MyHandler handler1;
    private GetDate getDate;
    private MyHandler1 handler2;

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            ImageView imageView = (ImageView)findViewById(R.id.image);
            imageView.setImageBitmap((Bitmap)msg.obj);
        }
    }

    class MyHandler1 extends Handler{
        @Override
        public void handleMessage(Message msg) {
            TextView textView = (TextView)findViewById(R.id.tv_1);
            textView.setText((String)msg.obj.toString());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler1 = new MyHandler();

        handler2 = new MyHandler1();
        Log.i("xiaobaicai","hello");
        getDate = new GetDate("xiao");
        Ajax.get("http://192.168.43.74:9001/test",getDate,GetDate.class,handler2);


        Button button = (Button) findViewById(R.id.test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Ajax.get("http://192.168.43.74:9001/test",getDate,GetDate.class,handler2);

                        /*
                        Log.i("xiaobaicai","run");
                        String path = "http://192.168.43.74:8080/hello.png";
                        Message msg = new Message();
                        msg.obj = getImage(path);
                        handler1.sendMessage(msg);
                        */
                    }
                }).start();

            }
        });
    }

    protected class GetDate{
        private String name;

        public GetDate(){

        }

        public GetDate(String name){
            this.name=name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "User{"+"name='"+name+'}';
        }
    }

}
