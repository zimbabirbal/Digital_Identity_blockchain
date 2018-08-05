package com.example.bjimba.digitalidentitycard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main4Activity extends AppCompatActivity {
    public ImageView img;
    public String message,qrMessage,addressMessage,hashMessage;
    public Button buttonScan,buttonVerify;
    public TextView tv2,tv3,tv4;
    public IntentIntegrator qrScan;
    RequestQueue requestQueue;
    String initialHash="";
    String hash_url ="http://ma-gar.com/blockchain/hash.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        buttonScan = (Button) findViewById(R.id.scanButton);
        buttonVerify = (Button) findViewById(R.id.VerifyButton);
        img = (ImageView) findViewById(R.id.imageView4);
        tv2 = (TextView) findViewById(R.id.t2);
        tv3 = (TextView) findViewById(R.id.t3);
        tv4 = (TextView) findViewById(R.id.t4);
        buttonScan.setVisibility(View.VISIBLE);
        buttonVerify.setVisibility(View.GONE);

        //Bundle bundle = getIntent().getExtras();
        //message = bundle.getString("hash_Value");
        message= "fsfd";

        tv2.setText("Please scan the QR Code to valid the ID.");

        qrScan = new IntentIntegrator(this);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        });
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidate();
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //textViewName.setText(result.getContents());

                    //String a = result.getContents();
                    //converting the data to json
                    //JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    //textViewName.setText(obj.getString("name"));
                    qrMessage=result.getContents();


                    gotoMessageVerify(qrMessage);
                    //Toast.makeText(this,"happy/" + result.getContents(), Toast.LENGTH_LONG).show();
                    //textViewAddress.setText(result.getContents());
                } catch (Exception e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast


                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void gotoMessageVerify(String qrMessage) {

        img.setVisibility(View.GONE);
        buttonScan.setVisibility(View.GONE);
        buttonVerify.setVisibility(View.VISIBLE);
        tv2.setText("");
        tv4.setText("Ethereum Blockchain Address Found");
        findAddressAndHash(qrMessage);




    }

    private void findAddressAndHash(String qrMessage) {
        String localHash="";
        String localAddress="";
        int slashPositions=0;

        for (int i = 0 ; i<qrMessage.length() ; i++) {
            if (qrMessage.charAt(i) == '/') {
                slashPositions = i;
            }
        }
        for (int j= 0; j< slashPositions;j++)
        {
            localAddress += qrMessage.charAt(j);
        }
        for (int k= slashPositions+1; k< qrMessage.length();k++)
        {
            localHash += qrMessage.charAt(k);
        }
        addressMessage= localAddress;
        hashMessage=localHash;

        tv2.setText(hashMessage);
        tv3.setText(addressMessage);




    }

    private void checkValidate() {

        Toast.makeText(getApplicationContext(),checkBlockchain(),Toast.LENGTH_SHORT).show();
        if(hashMessage.equals(checkBlockchain()))
        {
            Toast.makeText(getApplicationContext(),"ID is valid",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Id is not valid",Toast.LENGTH_SHORT).show();
        }
    }

    private String checkBlockchain() {
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, hash_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response));
                    JSONArray jsonArray = jsonObject.getJSONArray("users");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);


                        initialHash = employee.getString("ib_hash");

                        Toast.makeText(getApplicationContext(),initialHash,Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Please enter your valid id or password" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                // hashMap.put("email",email.getText().toString());
                // hashMap.put("password",password.getText().toString());


                hashMap.put("b_address", addressMessage);

                return hashMap;
            }
        };

        requestQueue.add(request);
        return initialHash;

    }


}
