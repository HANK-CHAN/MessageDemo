package com.tw.ch.lcc.hankchan.messagedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private Button tostbtn,snackbarbtn,alterbtn1,alterbtn2,alterbtn3,notifybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }


    private void findViews() {
        tostbtn = findViewById(R.id.tostbtn);
        snackbarbtn=findViewById(R.id.snackbarbtn);
        alterbtn1=findViewById(R.id.alterbtn1);
        alterbtn2=findViewById(R.id.alterbtn2);
        alterbtn3=findViewById(R.id.alterbtn3);
        notifybtn=findViewById(R.id.notifybtn);

        //onClickListener 實作onclick 方法 v->View
        tostbtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this,"這是通知訊息",Toast.LENGTH_SHORT).show();
        });

        snackbarbtn.setOnClickListener(v -> {
            //一般型
            //Snackbar.make(v,"舉重世界第一",Snackbar.LENGTH_SHORT).show();

            //回復型
            Snackbar.make(v,"東澳快訊!!!",Snackbar.LENGTH_SHORT).setAction("開啟",v1->{

                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                //啟動跳轉Activity
                startActivity(intent);

            }).setActionTextColor(Color.RED).show();

        });

        alterbtn1.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("對話框")
                    .setMessage("一般對話框，沒有按鈕!")
                    .show();
        });

        alterbtn2.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("詢問???")
                    .setMessage("咖啡買一送一，是否要加購")
                    .setCancelable(false);  // 是否可以按旁邊螢幕取消 false 不可以

            //用於確定按鈕用的
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alterbtn2.setText("你選擇Yes");
                }
            });

            //否定按鈕
            builder.setNegativeButton("否", (dialog, which) -> {
                alterbtn2.setText("你選擇NO");
            });

            builder.setNeutralButton("我考慮一下", (dialog, which) -> {
                alterbtn2.setText("我考慮一下");
            });

            builder.show();


        });

        alterbtn3.setOnClickListener(v -> {
            String[] items = {"買一送一","買十送五","都不要"};
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("請選擇")
                    .setCancelable(false);

            builder.setItems(items,(dialog,which)->{
                alterbtn3.setText(items[which]);
            });

            builder.show();

        });

        notifybtn.setOnClickListener(v->{

            NotifyMessage("舉重第一","咖啡買一送一","7-11 及 全家");

        });

    }


    @SuppressLint("WrongConstant")
    private void NotifyMessage(String title, String subtitle, String msg){
        //Android 8.0
        String channelId = "lcc";
        String channelName = "訊息推播";

        //所有版本適用
        int notifyID = 1;

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,channelId);
        builder.setContentTitle(title)
                .setSubText(subtitle)
                .setContentText(msg)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Android 8,0之後應用
            NotificationChannel channel = new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_HIGH);
            builder.setChannelId(channelId);
            manager.createNotificationChannel(channel);

        }else{
            //Android 8.0之前適用
            builder.setDefaults(Notification.DEFAULT_ALL)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }

        manager.notify(notifyID,builder.build()); //執行推播

    }

}