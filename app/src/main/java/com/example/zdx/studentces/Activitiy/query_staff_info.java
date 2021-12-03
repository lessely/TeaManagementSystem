package com.example.zdx.studentces.Activitiy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.zdx.studentces.R;
import com.example.zdx.studentces.Util.DBHelper;
import com.example.zdx.studentces.Util.user;
import com.example.zdx.studentces.Util.userAdapter;

import java.util.ArrayList;
import java.util.List;

//管理员登陆成功后跳转管理员菜单
// 1.员工账号查询
//显示员工信息list
public class query_staff_info extends AppCompatActivity {

    private List<user> userList = new ArrayList<user>();
    private DBHelper dbHelper;
    private ListView listView;
    private userAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_staff_query);

        dbHelper = DBHelper.getInstance(this);
        initStudent();//从数据库中检索所有学生信息
        adapter = new userAdapter(query_staff_info.this, R.layout.user_item, userList);
        listView = (ListView) findViewById(R.id.list_view1);
        listView.setAdapter(adapter);

        //listView点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final user user = userList.get(position);//捕获学生实例
                AlertDialog.Builder builder = new AlertDialog.Builder(query_staff_info.this);
                LayoutInflater factory = LayoutInflater.from(query_staff_info.this);
                final View textEntryView1 = factory.inflate(R.layout.query_staff_info_menu, null);//加载AlertDialog自定义布局
                builder.setView(textEntryView1);
                builder.setTitle("请选择相关操作");

                //查看员工详细信息
                Button selectInfo1 = (Button) textEntryView1.findViewById(R.id.staff_info_select);
                selectInfo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder select_builder = new AlertDialog.Builder(query_staff_info.this);
                        select_builder.setTitle("员工详细信息");
                        StringBuilder sb = new StringBuilder();
                        sb.append("员工姓名：" + user.getUsername() + "\n");
                        sb.append("员工工号：" + user.getUserid()+ "\n");
                        sb.append("员工密码：" + user.getUserpasswd()+ "\n");
                        select_builder.setMessage(sb.toString());
                        select_builder.create().show();
                    }
                });


                //删除员工信息
                Button delete_info1 = (Button) textEntryView1.findViewById(R.id.staff_info_delete);
                delete_info1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder delete_builder = new AlertDialog.Builder(query_staff_info.this);
                        delete_builder.setTitle("警告！！！！");
                        delete_builder.setMessage("您将删除该员工信息，此操作不可逆，请谨慎操作！");
                        delete_builder.setNegativeButton("取消", null);
                        delete_builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.execSQL("delete from user where id=?", new String[]{user.getUserid()});
                                userList.remove(position);//移除
                                adapter.notifyDataSetChanged();//刷新列表

                            }
                        });
                        delete_builder.create().show();

                    }
                });

                //修改员工信息,通过intent传递旧学生信息
                Button update_info1 = (Button) textEntryView1.findViewById(R.id.staff_info_update);
                update_info1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到添加学生信息的界面,通过intent传递数据
                        Intent intent  = new Intent(query_staff_info.this, add_staff_info.class);
                        intent.putExtra("haveData", "true");
                        intent.putExtra("name", user.getUsername());
                        intent.putExtra("id", user.getUserid());
                        intent.putExtra("password", user.getUserpasswd());
                        startActivity(intent);
                    }
                });

                builder.create().show();
            }
        });

    }

    //初始化学生信息
    private void initStudent() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user order by id", null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            userList.add(new user( name,id, password));
        }
        cursor.close();


    }


}
