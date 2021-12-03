package com.example.zdx.studentces.Activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zdx.studentces.R;
import com.example.zdx.studentces.Util.DBHelper;

public class MainActivity extends AppCompatActivity {
    private long exit_time; //用于实现按两次返回键退出
    private Button manager;
    private Button staff;
    private Button iot;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (Button) findViewById(R.id.manager);
        staff = (Button) findViewById(R.id.staff);
        iot= (Button) findViewById(R.id.Sensor);
        dbHelper = DBHelper.getInstance(this);
        dbHelper.getWritableDatabase();

        //跳转到传感器界面
        iot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, web.class);
                startActivity(intent);
            }
        });

        //跳转到管理员登录界面
        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Manager_login.class);
                startActivity(intent);
            }
        });

        //跳转到员工登录界面
        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Staff_login.class);
                startActivity(intent);
            }
        });

    }
        @Override
        //按两次back键退出
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            //获取按键并比较两次按back的时间大于2s不退出，否则退出
            if (keyCode == android.view.KeyEvent.KEYCODE_BACK && event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                if (System.currentTimeMillis() - exit_time > 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                    exit_time = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }

                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
    }
