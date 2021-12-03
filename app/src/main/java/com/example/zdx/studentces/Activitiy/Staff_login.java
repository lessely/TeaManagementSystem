package com.example.zdx.studentces.Activitiy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zdx.studentces.R;
import com.example.zdx.studentces.Util.DBHelper;

public class Staff_login extends AppCompatActivity {
    private EditText id;
    private  EditText pwd;
    private Button login;
    private DBHelper dbHelper;
//主页选择员工登录界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        //初始化控件
        id=(EditText)findViewById(R.id.num);
        pwd=(EditText)findViewById(R.id.pwd);
        login=(Button)findViewById(R.id.Login);

        dbHelper=DBHelper.getInstance(this);

        //点击登录按钮，作如下判断
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentId=id.getText().toString();
                String studentPassword=pwd.getText().toString();
                //当工号和密码都不为空的时候，开始判断
                if (!TextUtils.isEmpty(studentId)&& !TextUtils.isEmpty(studentPassword)){
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor cursor = db.rawQuery("select password from user where id=?", new String[]{studentId});
                    if (cursor.moveToNext()){
                        String password = cursor.getString(cursor.getColumnIndex("password"));
                        if (password.equals(studentPassword)){
                            //登陆成功后将id传过去
                            Intent intent = new Intent(Staff_login.this, Staff_Menu.class);
                            intent.putExtra("id",id.getText().toString());
                            startActivity(intent);
                            Toast.makeText(Staff_login.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Staff_login.this,"密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(Staff_login.this, "该账号未注册，请联系管理员", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}
