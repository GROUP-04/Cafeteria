package com.example.derrickngatia.derrick;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class signup extends android.support.v4.app.Fragment {
    private static final String url= "http://10.0.2.2/bookingsApp/Register.php";
    EditText email,username,password,password1;
    Button register;
    String PassMatch;
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public signup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.sign_up,container,false);
        password1=(EditText)view.findViewById(R.id.password1);
        password=(EditText)view.findViewById(R.id.password);
        username=(EditText)view.findViewById(R.id.username);
        email=(EditText)view.findViewById(R.id.email);
        register=(Button)view.findViewById(R.id.regster);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 if (password1.getText().toString().isEmpty() || password.getText().toString().isEmpty() || username.getText().toString().isEmpty() | email.getText().toString().isEmpty()) {
 }
  else if(username.getText().length()<5){
         username.setText("!too short");
     }
     else if(email.getText().length()<7) {
     email.setText("!too short");
 }
   else if(password.getText().length()<8){
         password.setText("");
     }
      else if (password1.getText().length() < 8) {
             password1.setText("");
         }
           else if (password.getText().equals(password1.getText())) {
                 password.setText("");
                 password1.setText("");
             }
         else{
                        registerUser();


                }

            }
        });
        return  view;
    }
public void starr(View view){

    signin signin=new signin();
    getFragmentManager().beginTransaction().replace(R.id.pager,signin).commit();
}
    private void registerUser(){
        final String user=username.getText().toString();
        final String emailUser=email.getText().toString();
        final String password2=password1.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                        email.setText("");
                        username.setText("");
                        password.setText("");
                        password1.setText("");
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
                params.put(KEY_EMAIL, password2);
                params.put(KEY_PASSWORD,emailUser);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
