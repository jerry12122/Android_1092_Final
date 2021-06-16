package com.example.android_1092_final;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class recipe extends AppCompatActivity {
    private TextView count;

    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Cart";
    private static SQLiteDatabase db;
    private DBHelper sqlDataBaseHelper;
    private int id,prices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        count = findViewById(R.id.recipe_count);
        Intent intent = getIntent();
        Resources res = getResources();
        id = intent.getIntExtra(WordAdapter.EXTRA_MESSAGE, 0);

        String[] food = res.getStringArray(R.array.foods);
        String[] desc = res.getStringArray(R.array.desc);
        String[] price_str = res.getStringArray(R.array.price);

        LinkedList<String> name = new LinkedList<>();
        LinkedList<String> description = new LinkedList<>();
        LinkedList<String> price_linklist = new LinkedList<>();

        for(int i=0;i<food.length;i++)
        {
            name.add(food[i]);
        }
        for(int i=0;i<desc.length;i++)
        {
            description.add(desc[i]);
        }
        for(int i=0;i<price_str.length;i++)
        {
            price_linklist.add(price_str[i]);
        }
        int[] imgList = new int[] {R.drawable.chocolate_mint_bar,
                R.drawable.blueberry_cupcakes,
                R.drawable.fudge_brownies,
                R.drawable.lemon_cake,
                R.drawable.cobbler,
                R.drawable.sheet_cake,
                R.drawable.espresso_crinkles,
                R.drawable.chocolate_cherry_cookies,
                R.drawable.tiramisu,
                R.drawable.carrot_cake,
                R.drawable.blueberry_ice_cream};

        TextView title = findViewById(R.id.recipe_title);
        TextView step = findViewById(R.id.recipe_step);
        ImageView image = findViewById(R.id.recipe_image);
        TextView price = findViewById(R.id.recipe_price);
        prices=Integer.parseInt(price_linklist.get(id));
        price.setText("$"+price_linklist.get(id));
        title.setText(name.get(id));
        step.setText(description.get(id));
        image.setImageResource(imgList[id]);

        sqlDataBaseHelper = new DBHelper(this,DataBaseName,null,DataBaseVersion);
        db = sqlDataBaseHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ DataBaseTable  ,null);
        c.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            if(Integer.parseInt(c.getString(0)) == id)
            {
                count.setText(c.getString(1));
            }
            c.moveToNext();
        }
    }
    public void countPlus(View view){
        String value= count.getText().toString();
        int finalValue=Integer.parseInt(value);
        finalValue++;
        count.setText(String.valueOf(finalValue));
    }
    public void countMinus(View view){
        String value= count.getText().toString();
        int finalValue=Integer.parseInt(value);
        if(finalValue>0)
        {
            finalValue--;
        }
        count.setText(String.valueOf(finalValue));
    }
    public void addToCart(View view){
        String value= count.getText().toString();
        int finalValue=Integer.parseInt(value);
        int count;
        ContentValues contentValues = new ContentValues();
        contentValues.put("_COUNT",finalValue);
        count = db.update(DataBaseTable,contentValues,"_ID="+id,null);
        finish();
    }
}