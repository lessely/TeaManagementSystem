package com.example.zdx.studentces.Activitiy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zdx.studentces.R;
import com.example.zdx.studentces.Util.DBHelper;

public class out_tea_info extends AppCompatActivity {
    private Button photo;
    private Button query;
    private Button ok;
    private EditText Teanum;
    private EditText Teaname;
    private EditText Teastock;
    private EditText Teaprice;

    private DBHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_tea_info);

        Teanum = (EditText) findViewById(R.id.editqueryTeanum);
        Teaname = (EditText) findViewById(R.id.editqueryTeaname);
        Teastock = (EditText) findViewById(R.id.editqueryTeatock);
        Teaprice = (EditText) findViewById(R.id.editqueryTeaprice);

        photo=(Button) findViewById(R.id.button4);
        query=(Button) findViewById(R.id.button3);
        ok=(Button) findViewById(R.id.querybutton);
        dbHelper = DBHelper.getInstance(this);

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teanum = Teanum.getText().toString();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String _teaname=null;
                int _teastock=0;
                String _teaprice=null;
                if (teanum != null) {
                    Cursor cursor = db.rawQuery("select teaname,teastock,teaprice from tea where teanum=?", new String[]{teanum});
                    if (cursor.moveToNext()) {
                         _teaname = cursor.getString(cursor.getColumnIndex("teaname"));
                         _teastock = cursor.getInt(cursor.getColumnIndex("teastock"));
                         _teaprice = cursor.getString(cursor.getColumnIndex("teaprice"));
                    }else {
                        Toast.makeText(out_tea_info.this, "????????????????????????", Toast.LENGTH_SHORT).show();

                    }
                    Teaname.setText(_teaname);
                    Teastock.setText(Integer.toString(_teastock));
                    Teaprice.setText(_teaprice);

                } else {
                    Toast.makeText(out_tea_info.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.beginTransaction();//????????????
                String teastock=Teastock.getText().toString();
                String teanum = Teanum.getText().toString();

                Cursor cursor = db.rawQuery("select teaname,teastock,teaprice from tea where teanum=?", new String[]{teanum});
                if (cursor.moveToNext()){
                    db.execSQL("update tea set teastock=? where teanum=?",
                            new String[]{teastock, teanum});
                    db.setTransactionSuccessful();//??????????????????
                    db.endTransaction();//????????????
                    Toast.makeText(out_tea_info.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                }



            }
        });
        }
    }


