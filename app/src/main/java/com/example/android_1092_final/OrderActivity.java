package com.example.android_1092_final;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import java.util.LinkedList;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private EditText name,phone,address,note;
    private TextView total;

    private  final LinkedList<String> ids = new LinkedList<>();
    private  final LinkedList<String> foods = new LinkedList<>();
    private  final LinkedList<String> count = new LinkedList<>();
    private  final LinkedList<String> price = new LinkedList<>();


    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Cart";
    private static SQLiteDatabase db;
    private DBHelper sqlDataBaseHelper;

    private int mode = 0;
    private int total_int = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.order_textview);
        name = findViewById(R.id.name_text);
        phone = findViewById(R.id.phone_text);
        address = findViewById(R.id.address_text);
        note = findViewById(R.id.note_text);
        total = findViewById(R.id.order_total);

        textView.setText(message);

        Resources res = getResources();
        String[] food_str = res.getStringArray(R.array.foods);

        sqlDataBaseHelper = new DBHelper(this,DataBaseName,null,DataBaseVersion);
        db = sqlDataBaseHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable,null);
        c.moveToFirst();

        for(int i=0;i<c.getCount();i++){
            if(c.getString(1).equals("0"))
            {

            }
            else
            {
                ids.add(c.getString(0));
                foods.add(food_str[Integer.parseInt(c.getString(0))]);
                count.add(c.getString(1));
                price.add(c.getString(2));
                Log.i("test",food_str[Integer.parseInt(c.getString(0))]+" "+c.getString(1)+" "+c.getString(2));
            }
            c.moveToNext();

        }
        for(int i =0 ;i<foods.size();i++)
        {
            int co,p;
            co = Integer.parseInt(count.get(i));
            p = Integer.parseInt(price.get(i));
            total_int = total_int +  co * p;
        }
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new CartAdapter(this, foods,count,price);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        total.setText("Total: $"+ String.valueOf(total_int));

    }

    /**
     * Checks which radio button was clicked and displays a toast message to
     * show the choice.
     *
     * @param view The radio button view.
     */

    public void onClickClear(View view){
        int aa;
        ContentValues contentValues = new ContentValues();
        for(int i=0;i<12;i++)
        {
            contentValues.put("_COUNT","0");
            aa = db.update(DataBaseTable,contentValues,"_ID="+i,null);
        }
        foods.clear();
        count.clear();
        price.clear();
        total_int = 0;
        total.setText("Total: $0");
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new CartAdapter(this, foods,count,price);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked.
        switch (view.getId()) {
            case R.id.sameday:
                if (checked) {
                    displayToast(
                            getString(R.string.same_day_messenger_service));
                    mode = 0;
                }
                break;
            case R.id.nextday:
                if (checked)
                {
                    displayToast(getString(R.string.next_day_ground_delivery));
                    mode = 1;
                }
                break;
            case R.id.pickup:
                if (checked) {
                    displayToast(getString(R.string.pick_up));
                    mode = 2;
                }
                break;
            default:
                // Do nothing.
                break;
        }
    }
    public void onClickOrder(View view){
        if(TextUtils.isEmpty(name.getText())){
            name.setError( "Name is required!" );
        }
        if(TextUtils.isEmpty(address.getText())){
            address.setError( "Address is required!" );
        }
        if(TextUtils.isEmpty(phone.getText())){
            phone.setError( "Phone is required!" );
        }
        if(TextUtils.isEmpty(name.getText())||TextUtils.isEmpty(phone.getText())||TextUtils.isEmpty(address.getText())){

        }
        else {
            RequestQueue requestQueue;
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
            Log.i("test", String.valueOf(foods.size()));
            for (int i = 0; i < foods.size(); i++) {
                String url;
                if(TextUtils.isEmpty(note.getText()))
                {
                    url = getResources().getString(R.string.server_api) + ids.get(i) + "&count=" + count.get(i) + "&price=" + price.get(i) + "&name=" + name.getText().toString() + "&address="
                            + address.getText().toString() + "&phone=" + phone.getText().toString() + "&note=0&mode=" + mode;
                }
                else
                {
                    url = getResources().getString(R.string.server_api)+ "?id=" + ids.get(i) + "&count=" + count.get(i) + "&price=" + price.get(i) + "&name=" + name.getText().toString() + "&address="
                            + address.getText().toString() + "&phone=" + phone.getText().toString() + "&note=" + note.getText().toString() + "&mode=" + mode;
                }

                // 設置 request 及處理response.
                Log.i("test", url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == "1") {
                            displayToast(response);
                        } else {
                            displayToast(response);
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("error", error.toString());
                            }
                        });
                requestQueue.add(stringRequest);
            }
        }
        Button clear = findViewById(R.id.order_clear);
        onClickClear(clear);
        finish();
    }
    /**
     * Displays the actual message in a toast message.
     *
     * @param message Message to display.
     */
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}