package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.contentprovider.provider.RuiXin;

public class MainActivity extends AppCompatActivity {

    private ContentResolver contentResolver;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentResolver = MainActivity.this.getContentResolver();

        ContentValues values = new ContentValues();
        values.put(RuiXin.EMAIL, "1@baidu.com");
        values.put(RuiXin.USERNAME, "name");
        values.put(RuiXin.DATE, 2);
        values.put(RuiXin.SEX, "sex");
        contentResolver.insert(RuiXin.CONTENT_URI, values);

        findViewById(R.id.txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = contentResolver.query(
                        RuiXin.CONTENT_URI, new String[] {
                                RuiXin.EMAIL, RuiXin.USERNAME,
                                RuiXin.DATE,RuiXin.SEX }, null, null, null);
                while (cursor.moveToNext()) {
                    Toast.makeText(MainActivity.this,
                            cursor.getString(cursor.getColumnIndex(RuiXin.EMAIL))
                                    + " "
                                    + cursor.getString(cursor.getColumnIndex(RuiXin.USERNAME))
                                    + " "
                                    + cursor.getString(cursor.getColumnIndex(RuiXin.DATE))
                                    + " "
                                    + cursor.getString(cursor.getColumnIndex(RuiXin.SEX)),
                            Toast.LENGTH_SHORT).show();
                }

                startManagingCursor(cursor); //查找后关闭游标

                Bundle bundle = new Bundle();
                bundle.putInt("listener", 123);
                getContentResolver().call(RuiXin.CONTENT_URI, "LISTEN", null, bundle);


            }
        });

    }
}
