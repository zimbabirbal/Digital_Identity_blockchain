package com.example.bjimba.digitalidentitycard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    FloatingActionButton fBar1,fBar2;
    TextView nameTv,rollNo,address1,dob1,ctzNum1,department1,expiryDate1;
    ImageView iv,barcode1;
    RequestQueue requestQueue;
    String hash_Value="";
    String image_url = "http://ma-gar.com/img_blockchain/";
    String url = "http://ma-gar.com/blockchain/viewData.php";
    String hash_url ="http://ma-gar.com/blockchain/getData.php";
    String rollNumber,passwordN,chkBlockchain,g_hash,b_address;
    public final static int barCodeWidth = 700 ;
    Bitmap bitmap ;
    Button checkBtn,checkBtn1;
    Context context;
    //String showUrl = "https://192.168.100.106/apple/viewData.php";
    //String showUrl = "https://api.myjson.com/bins/kp9wz";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        //getSupportActionBar().setTitle("Birbal Zimba");

        Bundle extras = getIntent().getExtras();
        rollNumber = extras.getString("key1");
        passwordN = extras.getString("key2");

        checkBtn = (Button) findViewById(R.id.checkbutton);
        checkBtn1 = (Button) findViewById(R.id.checkbutton1);
        iv = (ImageView) findViewById(R.id.viewPhoto);
        nameTv = (TextView) findViewById(R.id.nameTV);
        rollNo = (TextView) findViewById(R.id.rollNumTV);
        address1 = (TextView) findViewById(R.id.addressTV);
        expiryDate1 = (TextView) findViewById(R.id.expiryDateTV);
        dob1 = (TextView) findViewById(R.id.dob);
        ctzNum1 = (TextView) findViewById(R.id.ctz);
        department1 = (TextView) findViewById(R.id.department);
        barcode1 = (ImageView) findViewById(R.id.barcode);
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        // Toast.makeText(getApplicationContext(), "sdfsdfsfsdff ", Toast.LENGTH_SHORT).show();

        fBar2 = (FloatingActionButton) findViewById(R.id.floatingActionButton1);


        fBar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent i = new android.content.Intent(getApplicationContext(), Main3Activity.class);
                i.putExtra("hash_Value", hash_Value);

                startActivity(i);
            }
        });

        //String url = "https://api.myjson.com/bins/kp9wz";
        checkBtn1.setVisibility(View.GONE);
        checkBtn.setVisibility(View.GONE);


        // System.out.println("ww");
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ID is on Blockchain", Toast.LENGTH_SHORT).show();

            }
        });
        checkBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ID is currently not in Blockchain", Toast.LENGTH_SHORT).show();
            }
        });

        simpleFetch();


    }



    private void simpleFetch() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();

                try {

                    JSONObject jsonObject = new JSONObject(new String(response));
                    JSONArray jsonArray = jsonObject.getJSONArray("students");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);

                        String firstName = employee.getString("firstname");
                        String lastname = employee.getString("lastname");
                        String address = employee.getString("address");
                        String dob = employee.getString("dob");
                        String ctzNum = employee.getString("ctzNum");
                        String department = employee.getString("department");
                        String expiryDate = employee.getString("expiryDate");
                        String password = employee.getString("password");
                        String cblockchain = employee.getString("blockchain");

                        nameTv.setText(firstName + " " + lastname );
                        rollNo.setText("Roll No: " + rollNumber);
                        address1.setText("Address: " + address);

                        expiryDate1.setText("Expiry Date: " + expiryDate);
                        dob1.setText("DOB: " + dob);
                        ctzNum1.setText("Citzenship No: " + ctzNum);
                        department1.setText("" + department);
                        checkBlockchain(cblockchain);
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Please enter the valid Roll number or password " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                // hashMap.put("email",email.getText().toString());
                // hashMap.put("password",password.getText().toString());


                hashMap.put("rollNo", rollNumber);
                hashMap.put("password", passwordN);
                return hashMap;
            }
        };

        requestQueue.add(request);

        ImageFetch();
        BarCodeRead();
        HashCodeFetch();

    }

    private void HashCodeFetch() {
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


                        g_hash = employee.getString("g_hash");
                        b_address = employee.getString("b_address");
                        hash_Value=b_address.concat("/").concat(g_hash);
                        //Toast.makeText(getApplicationContext(),g_hash+b_address,Toast.LENGTH_SHORT).show();

                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),  e.toString(), Toast.LENGTH_SHORT).show();
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


                hashMap.put("rollNo", rollNumber);

                return hashMap;
            }
        };

        requestQueue.add(request);

    }

    private void checkBlockchain(String cblockchain) {
        if(cblockchain.equals("0")){

            checkBtn1.setVisibility(View.VISIBLE);
            checkBtn.setVisibility(View.GONE);
        }
        else
        {
            checkBtn1.setVisibility(View.GONE);
            checkBtn.setVisibility(View.VISIBLE);
        }
    }

    public  void ImageFetch(){
        ImageRequest imageRequest = new ImageRequest(image_url+rollNumber+".jpg", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iv.setImageBitmap(response);
            }
        }, 100, 100, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "" +
                        "Please enter your valid id or password",Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getMySingleton(getApplicationContext()).addTpRequestQue(imageRequest);
    }
    public void BarCodeRead()
    {

        try {
            bitmap = TextToImageEncode(rollNumber);

            barcode1.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.CODE_128,
                    barCodeWidth, barCodeWidth-585, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 700, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}