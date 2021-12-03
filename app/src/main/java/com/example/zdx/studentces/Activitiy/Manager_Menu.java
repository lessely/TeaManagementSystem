package com.example.zdx.studentces.Activitiy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zdx.studentces.R;

public class Manager_Menu extends AppCompatActivity {
    private Button butuserquery;
    private Button butuseradd;
    private Button butsensor;
    private Button Butdevelop;

//管理员登陆成功后跳转管理员菜单
    // 1.员工账号查询
    // 2.员工账号添加
    // 3.传感器服务器设置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_menu);//登陆成功后跳转管理员菜单

        //初始化相关控件
        Butdevelop=(Button)findViewById(R.id.developing);
        butuserquery=(Button)findViewById(R.id.userquery);
        butuseradd=(Button)findViewById(R.id.useradd);


        //跳转到员工查询界面
        butuserquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager_Menu.this, query_staff_info.class);
                startActivity(intent);
            }
        });

        //跳转到员工添加界面
        butuseradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager_Menu.this, add_staff_info.class);
                intent.putExtra("haveData","false");
                startActivity(intent);
            }
        });



        Butdevelop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Manager_Menu.this, "其他功能开发中...", Toast.LENGTH_LONG).show();
            }
        });


    }
}
