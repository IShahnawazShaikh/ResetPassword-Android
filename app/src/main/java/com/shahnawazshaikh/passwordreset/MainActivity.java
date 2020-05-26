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

public class MainActivity extends AppCompatActivity {
    EditText EmailID;
    private static final String Check_URL = "http://digital.store/api/checkforuser.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          EmailID=findViewById(R.id.Email);
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(EmailID.getText().toString().isEmpty()))
                    checUser();
                else
                    Toast.makeText(MainActivity.this,"Email cannot be empty",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void checUser() {

        StringRequest request = new StringRequest(Request.Method.POST, Check_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //progressBar.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("status").equals("1")){
                                if(jsonObject.getString("message").equals("1")){
                                    Intent intent=new Intent(MainActivity.this,ResetPassword.class);
                                    intent.putExtra("email",EmailID.getText().toString());

                                    startActivity(intent);
                                }
                                else{
                                     Toast.makeText(MainActivity.this,""+EmailID.getText()+" Not Registered",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                 Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
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
                params.put("email",EmailID.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
