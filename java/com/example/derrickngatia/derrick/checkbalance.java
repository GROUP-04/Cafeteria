package com.example.derrickngatia.derrick;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class checkbalance extends ActionBarActivity{
    final String KEY_REGNO="registrationNo";
    final String url="http://10.0.2.2/bookingsApp/checkbalance.php";
    public static final String CREATE_DB="CREATE TABLE IF NOT EXISTS balance(id AUTOINCREMENT,message TEXT)";
    public static final String DROP_TABLE="DROP TABLE balance";
    EditText text;
    Button btn;
    TextView textView;
    String Response;
    dbhelper dbhelper;
    private Context mcontext;
    private SQLiteDatabase database;
    SQLiteOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_balance);
        text=(EditText)findViewById(R.id.regNo);
        btn=(Button)findViewById(R.id.Money);
        textView=(TextView)findViewById(R.id.check_balance);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moneyInAccount();
            }
        });
    }

    private String moneyInAccount() {
        final String regno=text.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                       // dbhelper.insertData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar snackbar = Snackbar.make(null,"username does not exists",Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_REGNO,regno);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return Response;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checkbalance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
