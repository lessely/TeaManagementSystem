package com.example.zdx.studentces.Activitiy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
public class add_tea_info extends AppCompatActivity {

    private EditText Teaname;
    private EditText Teanum;
    private EditText Teastock;
    private EditText Teaprice;
    private String oldNum;//用于防治修改信息时将ID也修改了，而原始的有该ID的学生信息还保存在数据库中
    private Button sure;//确定按钮
    private DBHelper dbHelper;
    Intent oldData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tea_info);

        Teaname = (EditText) findViewById(R.id.editTeaname);
        Teanum = (EditText) findViewById(R.id.editTeanum);
        Teaprice = (EditText) findViewById(R.id.editTeaprice);
        Teastock = (EditText) findViewById(R.id.editTeatock);
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
                //sex不能为空否则程序崩溃，因为在StudentAdapter中有一个ImageView要设置图片
                //我这里要求id,name,sex都不能为空
                String teanum_ = Teanum.getText().toString();
                String teaname_ = Teaname.getText().toString();
                String teastock_ = Teastock.getText().toString();
                String teaprice_ =  Teaprice.getText().toString();


                if (!TextUtils.isEmpty(teanum_) && !TextUtils.isEmpty(teaname_) && !TextUtils.isEmpty(teaprice_)) {

                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.beginTransaction();//开启事务

                        //判断学号是否重复
                        Cursor cursor = db.rawQuery("select * from tea where teanum=?", new String[]{teanum_});
                        if (cursor.moveToNext()) {
                            Toast.makeText(add_tea_info.this, "该编号的茶叶已经存在,请重新输入", Toast.LENGTH_SHORT).show();
                        } else {
                            db.execSQL("insert into tea(teanum,teaname,teastock,teaprice) values(?,?,?,?)",
                                    new String[]{teanum_, teaname_, teastock_, teaprice_});
                            db.setTransactionSuccessful();//事务执行成功
                            db.endTransaction();//结束事务
                            Intent intent = new Intent(add_tea_info.this,Staff_Menu.class);
                            startActivity(intent);
                        }



                } else {
                    Toast.makeText(add_tea_info.this, "姓名，学号，性别均不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //恢复旧数据
    private void initInfo() {
        String oldName = oldData.getStringExtra("teaname");
        Teaname.setText(oldName);
        String oldNum1 = oldData.getStringExtra("teanum");
        oldNum = oldNum1;
        Teanum.setText(oldNum);
        String oldStock= oldData.getStringExtra("teastock");
        Teastock.setText(oldStock);
        String oldPrice = oldData.getStringExtra("teastock");
        Teaprice.setText(oldPrice);
    }


}
