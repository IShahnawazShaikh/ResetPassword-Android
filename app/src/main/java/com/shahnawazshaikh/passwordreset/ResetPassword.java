package com.shahnawazshaikh.passwordreset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity {
    String email;
    EditText passID,cpassID;
    private static final String RESET_URL = "http://digital.store/api/resetpassword.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Bundle bundle=getIntent().getExtras();
        email=bundle.getString("email");
        passID=findViewById(R.id.password);
        cpassID=findViewById(R.id.cpassword);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(!(passID.getText().toString().isEmpty() && cpassID.getText().toString().isEmpty())){
                       if(passID.getText().toString().equals(cpassID.getText().toString())){
                             reset();
                       }
                       else{
                           Toast.makeText(ResetPassword.this,"Password Not Matched",Toast.LENGTH_LONG).show();
                       }
                 }
                 else{
                     Toast.makeText(ResetPassword.this,"Must Fill the fields",Toast.LENGTH_LONG).show();
                 }
            }
        });



    }

    private void reset() {

        StringRequest request = new StringRequest(Request.Method.POST, RESET_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //progressBar.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(ResetPassword.this,""+jsonObject,Toast.LENGTH_LONG).show();
                            if(jsonObject.getString("status").equals("1")){
                                Toast.makeText(ResetPassword.this,"Reset Successfully",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(ResetPassword.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error: "+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Response errorr"+error.toString(),Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("password",passID.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }
}
