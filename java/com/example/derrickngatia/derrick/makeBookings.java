package com.example.derrickngatia.derrick;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by DERRICK NGATIA on 6/7/2017.
 */
public class makeBookings extends FragmentActivity {
    public static final String KEY_PHONE = "phone";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_DATE = "date";
    public static final String KEY_BRIEF = "brief";
    public static final String KEY_ATTENDANTS = "attendants";
    public static final String KEY_TYPE="type";
    EditText text1,text2,text3,text4,text5;
    Button button;
    String Name;
    Spinner spinner;
    EditText date1;
    Calendar calendar;
    String type;
    TextView textView;
    String[] data={"WEDDING","BURIAL","SCHOOL","OUTSIDE CATERING","OTHER"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookings);
        spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data);
        spinner.setAdapter(adapter);
        button=(Button)findViewById(R.id.go);
        text1=(EditText)findViewById(R.id.phoneno);
        text2=(EditText)findViewById(R.id.date);
        text3=(EditText)findViewById(R.id.briefInfo);
        text4=(EditText)findViewById(R.id.noOfAttendants);
        text5=(EditText)findViewById(R.id.location);
        textView=(TextView)findViewById(R.id.welcome);
        type=String.valueOf(spinner.getTop());
        date1=(EditText)findViewById(R.id.date);
        calendar=Calendar.getInstance();
        textView.setText("welcome ,please fill in your request for processing\n");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text1.getText().toString().isEmpty() || text2.getText().toString().isEmpty() || text3.getText().toString().isEmpty() || text4.getText().toString().isEmpty() || text4.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "empty fields found", Toast.LENGTH_SHORT).show();
                } else if (text1.getText().length() < 10) {
                    text1.setText("too short!");
                } else {
                    registerUser();
                   setContentView(R.layout.fragment_make_bookings);
                }
            }
        });
        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(makeBookings.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private void updateLabel() {
        String format="MM/dd/yyyy";
        SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.US);
        date1.setText(sdf.format(calendar.getTime()));
    }
    private void registerUser(){
        final String phonenumber=text1.getText().toString();
        final String location=text5.getText().toString();
        final String no_of=text4.getText().toString();
        final String briefInfo=text3.getText().toString();
        final String date=text2.getText().toString();


        String urly="http://10.0.2.2/bookingsApp/BookEvent.php";
        final View view = null;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,urly,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.replace),"Request received",Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.replace),"no internet  connection",Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_PHONE,phonenumber);
                params.put(KEY_LOCATION,location);
                params.put(KEY_DATE,date);
                params.put(KEY_BRIEF,briefInfo);
                params.put(KEY_ATTENDANTS,no_of);
                params.put(KEY_TYPE,type);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void user(String user) {
        Name=user;

    }
}
