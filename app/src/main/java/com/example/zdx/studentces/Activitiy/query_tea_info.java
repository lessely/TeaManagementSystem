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
import com.example.zdx.studentces.Util.tea;
import com.example.zdx.studentces.Util.teaAdapter;

import java.util.ArrayList;
import java.util.List;

//管理员登陆成功后跳转管理员菜单
// 1.茶叶信息查询
//显示茶叶信息list
public class query_tea_info extends AppCompatActivity {

    private List<tea> teaList = new ArrayList<tea>();
    private DBHelper dbHelper;
    private ListView listView;
    private teaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tea_query);

        dbHelper = DBHelper.getInstance(this);
        inittea();//从数据库中检索所有茶叶信息
        adapter = new teaAdapter(query_tea_info.this, R.layout.tea_item, teaList);
        listView = (ListView) findViewById(R.id.list_view2);
        listView.setAdapter(adapter);

        //listView点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final tea tea = teaList.get(position);//捕获茶叶实例
                AlertDialog.Builder builder = new AlertDialog.Builder(query_tea_info.this);
                LayoutInflater factory = LayoutInflater.from(query_tea_info.this);
                final View textEntryView1 = factory.inflate(R.layout.query_tea_info_menu, null);//加载AlertDialog自定义布局
                builder.setView(textEntryView1);
                builder.setTitle("请选择相关操作");

                //查看茶叶详细信息
                Button selectInfo1 = (Button) textEntryView1.findViewById(R.id.tea_info_select);
                selectInfo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder select_builder = new AlertDialog.Builder(query_tea_info.this);
                        select_builder.setTitle("茶叶详细信息");
                        StringBuilder sb = new StringBuilder();
                        sb.append("茶叶编号：" + tea.getTeanum() + "\n");
                        sb.append("茶叶名称：" + tea.getTeaname() + "\n");
                        sb.append("茶叶库存：" + tea.getTeastock() + "\n");
                        sb.append("茶叶价格：" + tea.getTeaprice() + "\n");
                        select_builder.setMessage(sb.toString());
                        select_builder.create().show();
                    }
                });


                //删除茶叶信息
                Button delete_info1 = (Button) textEntryView1.findViewById(R.id.tea_info_delete);
                delete_info1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder delete_builder = new AlertDialog.Builder(query_tea_info.this);
                        delete_builder.setTitle("警告！！！！");
                        delete_builder.setMessage("您将删除该茶叶信息，此操作不可逆，请谨慎操作！");
                        delete_builder.setNegativeButton("取消", null);
                        delete_builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.execSQL("delete from tea where teanum=?", new String[]{tea.getTeanum()});
                                teaList.remove(position);//移除
                                adapter.notifyDataSetChanged();//刷新列表

                            }
                        });
                        delete_builder.create().show();

                    }
                });

                //修改员工信息,通过intent传递旧学生信息
                Button update_info1 = (Button) textEntryView1.findViewById(R.id.tea_info_update);
                update_info1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到添加学生信息的界面,通过intent传递数据
                        Intent intent = new Intent(query_tea_info.this, add_tea_info.class);
                        intent.putExtra("haveData", "true");
                        intent.putExtra("teaname", tea.getTeaname());
                        intent.putExtra("teanum", tea.getTeanum());
                        intent.putExtra("teastock", tea.getTeastock());
                        intent.putExtra("teaprice", tea.getTeaprice());
                        startActivity(intent);
                    }
                });

                builder.create().show();
            }
        });

    }

    //初始化茶叶信息
    private void inittea() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tea order by teanum", null);
        while (cursor.moveToNext()) {
            String teanum = cursor.getString(cursor.getColumnIndex("teanum"));
            String teaname = cursor.getString(cursor.getColumnIndex("teaname"));
            int teastock = cursor.getInt(cursor.getColumnIndex("teastock"));
            String teaprice = cursor.getString(cursor.getColumnIndex("teaprice"));
            teaList.add(new tea(teaname, teanum, teaprice, teastock));
        }
        cursor.close();


    }


}
