package com.example.leo.mfilemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * @Author:Leo
 * @Description
 * @Date: Created in 0:12 2018/3/9
 * @Modified
 */
public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载启动图片
        setContentView(R.layout.activity_launch);
        //后台处理耗时任务
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(LaunchActivity.this,MainActivity.class);
                LaunchActivity.this.startActivity(mainIntent);
                LaunchActivity.this.finish();
            }
        },1700);

    }

}