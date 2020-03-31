package com.gn.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    /**
     * Looper运行在创建handler的线程中, looper的loop无限循环处理messgaequeue的消息
     * 也就是说messgaequeue的消息是在创建handler的线程中被处理
     *
     * sendmessage相当于往messgaequeue插入一条消息.
     * 所以handler在接收到messgaequeue里的消息后就会处理 最终loop里会调用msg.target.dispatchMessage(msg)
     * dispatchMessage会调用自己重写的handlemessage
     *
     *
     * 如果直接不用sendmessage而是用dispatchMessage发送消息,则handler里面的handlmessage还是运行在子线程中 因为没有走looper 和 messagequeue的流程.
     */

    Handler handler;
    Handler handler1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("@@", "Main:" + Thread.currentThread().getId());

        useMyHandler();

    }


    public void handlerPost() {

        handler = new Handler();

        new Thread("thread3") {
            @Override
            public void run() {
                super.run();
                Log.i("@@", "thread3:" + Thread.currentThread().getId());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("@@", "post");
                        Log.i("@@", "handleMessage:" + Thread.currentThread().getId());
                    }
                });
            }
        }.start();

    }

    MyHandler myHandler = new MyHandler();

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.i("@@", "MyHandler:" + Thread.currentThread().getId());

        }
    }


    public void useMyHandler() {
        new Thread() {
            @Override
            public void run() {
                Log.i("@@", "Thread:" + Thread.currentThread().getId());
                Message msg = Message.obtain(myHandler, new Runnable() {
                    // 如果此处有传入callback 则不会调用myhandler里面的handlemessage 原因见dispatchmessage的源码
                    @Override
                    public void run() {
                        Log.i("@@", "Thread:..." + Thread.currentThread().getId());

                    }
                });
                msg.what = 1;
                myHandler.sendMessage(msg);
            }
        }.start();

    }

    public void dispatchMessage() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.e("@@", "receive msg");

                if (msg.what == 1) {
                    // 这里打印的是子线程的id 原因是没走looper和messagequeue流程
                    Log.i("@@", "handleMessage:" + Thread.currentThread().getId());
                }
            }
        };

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread("Thread1") {
                    @Override
                    public void run() {
                        super.run();
                        Log.i("@@", "Thread:" + Thread.currentThread().getId());
                        Message msg = new Message();
                        msg.what = 1;
                        handler.dispatchMessage(msg);

                    }
                }.start();
            }
        });
    }

    public void callback() {
        handler1 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                Log.i("@@", "handleMessage:" + Thread.currentThread().getId());

                return false;
            }
        });

        new Thread() {
            @Override
            public void run() {
                Log.i("@@", "Thread:" + Thread.currentThread().getId());
                Message msg = handler1.obtainMessage();
                msg.what = 1;
                handler1.sendMessage(msg);
            }
        }.start();
    }

}
