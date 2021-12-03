package com.example.zdx.studentces.Activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zdx.studentces.R;
import com.example.zdx.studentces.Util.DBHelper;

/**
 * 员工菜单
 */
public class Staff_Menu extends AppCompatActivity {
    private Button teaquery;
    private Button teain;
    private Button teaout;
    private Button staffedit;
    private DBHelper dbHelper;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_menu);

        teaquery=(Button) findViewById(R.id.teaquery);
        teain=(Button) findViewById(R.id.teain);
        teaout=(Button) findViewById(R.id.teaout);
        staffedit=(Button) findViewById(R.id.staffedit);
        dbHelper = DBHelper.getInstance(this);
        intent = getIntent();


        //跳转到茶叶查询界面
        teaquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Staff_Menu.this, query_tea_info.class);
                startActivity(intent);
            }
        });

        //跳转到茶叶入仓界面
        teain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Staff_Menu.this, add_tea_info.class);
                intent.putExtra("haveData","false");
                startActivity(intent);
            }
        });

        //跳转到茶叶出仓界面
        teaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {/////////////////////////////////////////////////////////////还未完成
                Intent intent=new Intent(Staff_Menu.this, out_tea_info.class);
                startActivity(intent);
            }
        });

        staffedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Staff_Menu.this, "该功能开发中...", Toast.LENGTH_LONG).show();
            }
        });



    }
}
