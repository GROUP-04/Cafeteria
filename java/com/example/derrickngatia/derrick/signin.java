package com.example.derrickngatia.derrick;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class signin extends android.support.v4.app.Fragment {
    private static final String url= "http://10.0.2.2/bookingsApp/login.php";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    EditText text1,text2;

    public signin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.signin,container,false);
        Button login=(Button)view.findViewById(R.id.student_login);
        text1=(EditText) view.findViewById(R.id.username);
        text2=(EditText) view.findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text1.getText().toString().isEmpty() || text2.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), "empty field found", Toast.LENGTH_SHORT).show();
                }
                   else if(text1.getText().length()<7){
                        Toast.makeText(v.getContext(),"too short",Toast.LENGTH_SHORT).show();
                        text1.setText("");
                    }
                   else if(text2.getText().length()<8){
                        Toast.makeText(v.getContext(),"too short",Toast.LENGTH_SHORT).show();
                    text2.setText("");

                } else{
                    successLogin(v);

                }
            }
        });

        return view;
    }
      public void successLogin(View view){
          final String user=text1.getText().toString();
          final String password2=text2.getText().toString();

          StringRequest stringRequest = new StringRequest(Request.Method.POST,url ,
                  new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {


                          if(response.equals("success")){
                              Intent intent=new Intent(getContext(),makeBookings.class);
                              startActivity(intent);

                          }
                          else if(response.equals("student")){
                              Intent intent=new Intent(getContext(),checkbalance.class);
                              startActivity(intent);
                          }
                          else{
                              Toast.makeText(getContext(),"wrong credentials",Toast.LENGTH_SHORT).show();
                          }
                      }
                  },
                  new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          Snackbar snackbar = Snackbar.make(getView(),"no internet  connection",Snackbar.LENGTH_SHORT);
                          snackbar.show();
                      }
                  }){
              @Override
              protected Map<String,String> getParams(){
                  Map<String,String> params = new HashMap<String, String>();
                  params.put(KEY_USERNAME,user);
                  params.put(KEY_PASSWORD,password2);
                  return params;
              }

          };
          RequestQueue requestQueue = Volley.newRequestQueue(getContext());
          requestQueue.add(stringRequest);

                  }

               }
