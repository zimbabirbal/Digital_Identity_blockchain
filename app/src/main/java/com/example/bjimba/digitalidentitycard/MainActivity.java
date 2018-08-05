package com.example.bjimba.digitalidentitycard;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText rollNum,password;
    Button btnSign;
    String url = "http://ma-gar.com/blockchain/viewData.php";
    RequestQueue requestQueue;
    String globalPassword="";

    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rollNum = (EditText)findViewById(R.id.rollET);
        password =(EditText)findViewById(R.id.passwordET);
        btnSign =(Button)findViewById(R.id.btnSign);



        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(rollNum.getText().toString().equals("")||password.getText().toString().equals("")||(rollNum.getText().toString().equals("")&&password.getText().toString().equals("")))
                {

                    Toast.makeText(getApplicationContext(), "Please fill the form", Toast.LENGTH_SHORT).show();
                }
                else
                {


                      android.content.Intent i = new android.content.Intent(getApplicationContext(),Main2Activity.class);
                      i.putExtra("key1", rollNum.getText().toString());
                      i.putExtra("key2", passwordEncryption(password.getText().toString()));
                      startActivity(i);




                }

            }
        });

    }
    private String passwordEncryption(String password)
    {


        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] encodedhash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(encodedhash);

    }
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public void onClick(View v) {
        android.content.Intent i = new android.content.Intent(getApplicationContext(),Main4Activity.class);
        //i.putExtra("hash_Value", hash_Value);

        startActivity(i);

    }



}
