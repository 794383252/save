package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity implements View.OnClickListener {

    EditText edittext;
    Button fileSave;
    Button fileRead;
    Button SharedPreferencesSave;
    Button SharedPreferencesRead;
    Button sqliteSave;
    Button sqliteRead;

    FileInputStream in;
    BufferedReader reader;
    FileOutputStream out;
    BufferedWriter writer;
    StringBuilder builder;

    private myDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        fileSave = (Button) findViewById(R.id.fileSave);
        fileRead = (Button) findViewById(R.id.fileRead);
        SharedPreferencesSave = (Button) findViewById(R.id.SharedPreferencesSave);
        SharedPreferencesRead = (Button) findViewById(R.id.SharedPreferencesRead);
        sqliteSave = (Button) findViewById(R.id.sqliteSave);
        sqliteRead = (Button) findViewById(R.id.sqliteRead);
        edittext = (EditText) findViewById(R.id.edittext);

        //只需要版本号传入一个比之前版本号大的一个数就可以执行onUpgrade
        db=new myDB(this,"bookstore.db",null,1);

        fileSave.setOnClickListener(this);
        fileRead.setOnClickListener(this);
        SharedPreferencesSave.setOnClickListener(this);
        SharedPreferencesRead.setOnClickListener(this);
        sqliteSave.setOnClickListener(this);
        sqliteRead.setOnClickListener(this);
    }

    public void fileSave() {
        try {
            out = openFileOutput("fileSave", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(edittext.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fileRead() {
        try {
            in = openFileInput("fileSave");
            reader = new BufferedReader(new InputStreamReader(in));
            String str = "";
            builder = new StringBuilder();
            while((str = reader.readLine()) != null) {
                edittext.setText(builder.append(str).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void SharedPreferencesSave() {
        SharedPreferences.Editor editor=getSharedPreferences("SharedPreferencesSave",MODE_PRIVATE).edit();
        editor.putString("name","lining");
        editor.putInt("age",11);
        editor.putBoolean("married",false);
        editor.commit();
    }

    public void SharedPreferencesRead() {
        SharedPreferences sharedPreferences=getSharedPreferences("SharedPreferencesSave",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","");
        int age=sharedPreferences.getInt("age",0);
        boolean married=sharedPreferences.getBoolean("married",false);
        edittext.setText("name:"+name+"age:"+age+"married:"+married);
    }

    public void sqliteSave() {
        db.getWritableDatabase();
    }

    public void sqliteRead() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fileSave:
                fileSave();
                break;
            case R.id.fileRead:
                fileRead();
                break;
            case R.id.SharedPreferencesSave:
                SharedPreferencesSave();
                break;
            case R.id.SharedPreferencesRead:
                SharedPreferencesRead();
                break;
            case R.id.sqliteSave:
                sqliteSave();
                break;
            case R.id.sqliteRead:
                sqliteRead();
                break;
        }
    }
}
