package com.example.android_1092_final;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import java.util.LinkedList;


import android.content.res.Resources;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private  final LinkedList<String> foods = new LinkedList<>();
    private  final LinkedList<String> desc = new LinkedList<>();
    public static final String EXTRA_MESSAGE =
            "com.example.android.android_1092_final.extra.MESSAGE";
    private RecyclerView mRecyclerView;
    private WordAdapter mAdapter;
    private String mOrderMessage;

    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Cart";
    private static SQLiteDatabase db;
    private DBHelper sqlDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        OrderActivity.class);
                intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
                startActivity(intent);
            }
        });

        sqlDataBaseHelper = new DBHelper(this,DataBaseName,null,DataBaseVersion);
        db = sqlDataBaseHelper.getWritableDatabase();

        Resources res = getResources();
        String[] food = res.getStringArray(R.array.foods);
        String[] price = res.getStringArray(R.array.price);

        final SharedPreferences remdname=getPreferences(Activity.MODE_PRIVATE);
        String allow=remdname.getString("allow", "0");
        if(allow.equals("0")){
            for (int i = 0; i < food.length; i++) {
                foods.add(food[i]);
                desc.add("$"+price[i]);
                long id;
                ContentValues contentValues = new ContentValues();
                contentValues.put("_ID",i);
                contentValues.put("_COUNT",0);
                contentValues.put("_PRICE",price[i]);
                id = db.insert(DataBaseTable,null,contentValues);
            }
            SharedPreferences.Editor edit=remdname.edit();
            edit.putString("allow", "1");
            edit.commit();
        }
        else
        {
            for (int i = 0; i < food.length; i++) {
                foods.add(food[i]);
                desc.add("$"+price[i]);
            }
        }

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new WordAdapter(this, foods,desc);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}