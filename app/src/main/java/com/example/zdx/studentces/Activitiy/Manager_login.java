package com.example.zdx.studentces.Activitiy;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zdx.studentces.R;
import com.example.zdx.studentces.Util.DBHelper;

public class Manager_login extends AppCompatActivity {
//管理员登录界面
    private EditText id;//用户名
    private EditText password;//用户密码
    private Button login;//登录按钮
    private TextView register;//注册
    private TextView forgetNum;//忘记密码
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_login);

        //初始化相关控件
        dbHelper = DBHelper.getInstance(this);
        id=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);
        login=(Button)findViewById(R.id.button);
        register = (TextView) findViewById(R.id.register);
        forgetNum=(TextView)findViewById(R.id.forgetNum);


        //跳转到登录过的管理员界面
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idInfo = id.getText().toString();
                String passwordInfo = password.getText().toString();
                //从数据库中获取密码并判断是否相同
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select password from admin where id=?", new String[]{idInfo});
                String pi = null;
                if (cursor.moveToNext()){
                    pi = cursor.getString(cursor.getColumnIndex("password"));//获取密码
                }

                //如果密码正确跳转到登录后的界面
                if (passwordInfo.equals(pi)){
                    Toast.makeText(Manager_login.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(Manager_login.this, Manager_Menu.class);
                    startActivity(intent);
                    cursor.close();
                }else{
                    Toast.makeText(Manager_login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //自定义AlertDiaglog用于注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Manager_login.this);
                LayoutInflater factory = LayoutInflater.from(Manager_login.this);
                final View textEntryView = factory.inflate(R.layout.register,null);
                builder.setTitle("用户注册");
                builder.setView(textEntryView);

                final EditText code = (EditText) textEntryView.findViewById(R.id.register_code);
                final EditText name = (EditText) textEntryView.findViewById(R.id.register_name);
                final EditText id = (EditText) textEntryView.findViewById(R.id.register_id);
                final EditText firstPassword = (EditText) textEntryView.findViewById(R.id.register_pwd);
                final EditText secondPassword = (EditText) textEntryView.findViewById(R.id.register_sure);

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String codeInfo = code.getText().toString();
                        //注册码admin
                        if (codeInfo.equals("8888")){
                            String nameInfo = name.getText().toString();
                            String idInfo = id.getText().toString();
                            String firstPasswordInfo = firstPassword.getText().toString();
                            String secondPasswordInfo = secondPassword.getText().toString();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            //检测密码是否为6个数字
                            if (firstPasswordInfo.matches("[0-9A-Za-z]{6}")&&idInfo.matches("[0-9]{1,}")){
                                //两次密码相同
                                if (firstPasswordInfo.equals(secondPasswordInfo)){
                                    Cursor cursor = db.rawQuery("select name from admin where id=? ", new String[]{idInfo});
                                    Toast.makeText(Manager_login.this,"注册成功",Toast.LENGTH_SHORT).show();

                                    //用户是否存在
                                    if (cursor.moveToNext()){
                                        Toast.makeText(Manager_login.this,"该用户已经存在",Toast.LENGTH_SHORT).show();
                                    }else{
                                        db.execSQL("insert into admin(id,name,password)values(?,?,?)", new String[]{idInfo,nameInfo,firstPasswordInfo});
                                    }
                                }else{
                                    Toast.makeText(Manager_login.this, "两次密码不相同", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(Manager_login.this, "密码为6位", Toast.LENGTH_SHORT).show();

                            }
                        } else{
                            Toast.makeText(Manager_login.this, "注册码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.create().show();
            }
        });



        /**
         * 忘记密码
         */
        forgetNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Manager_login.this, "此功能暂不支持", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
