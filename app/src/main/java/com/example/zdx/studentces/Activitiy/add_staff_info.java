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

/**
 * 添加学生信息的界面,修改学生信息的界面
 */
public class add_staff_info extends AppCompatActivity {

    private EditText name;
    private EditText id;
    private EditText password;

    private String oldID;//用于防止修改信息时将ID也修改了，而原始的有该ID的学生信息还保存在数据库中


    private Button sure;//确定按钮
    private DBHelper dbHelper;
    Intent oldData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_staff_info);

        name = (EditText) findViewById(R.id.editTextname);
        id = (EditText) findViewById(R.id.editTextnum);
        password = (EditText) findViewById(R.id.editTextpasswd);

        dbHelper = DBHelper.getInstance(this);

        oldData = getIntent();
        if (oldData.getStringExtra("haveData").equals("true")) {
            initInfo();//恢复旧数据
        }


        sure = (Button) findViewById(R.id.button);
        //将数据插入数据库
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_ = id.getText().toString();
                String name_ = name.getText().toString();
                String password_ = password.getText().toString();

                if (!TextUtils.isEmpty(id_) && !TextUtils.isEmpty(name_)) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.beginTransaction();//开启事务
                        db.execSQL("delete from user where id=?", new String[]{oldID});//删除旧数据

                        //判断工号是否重复
                        Cursor cursor = db.rawQuery("select * from user where id=?", new String[]{id_});
                        if (cursor.moveToNext()) {
                            Toast.makeText(add_staff_info.this, "已有员工使用该工号,请重新输入", Toast.LENGTH_SHORT).show();
                        } else {
                            db.execSQL("insert into user(id,name,password) values(?,?,?)", new String[]{id_, name_, password_});
                            db.setTransactionSuccessful();//事务执行成功
                            db.endTransaction();//结束事务
                            Toast.makeText(add_staff_info.this, "添加员工信息成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(add_staff_info.this, Manager_Menu.class);
                            startActivity(intent);
                        }

                } else {
                    Toast.makeText(add_staff_info.this, "姓名,工号不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //恢复旧数据
    private void initInfo() {
        String oldName = oldData.getStringExtra("name");
        name.setText(oldName);
        String oldId = oldData.getStringExtra("id");
        oldID = oldId;
        id.setText(oldId);
        String oldPassword = oldData.getStringExtra("password");
        password.setText(oldPassword);
    }


}
