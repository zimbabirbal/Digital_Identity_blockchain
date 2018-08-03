package com.example.bjimba.digitalidentitycard;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.io.ByteArrayInputStream;

public class GlobalClass extends Activity {
public Context context;
    public String getRollnum;
    public String getPassword;
    public String firstName;
    public String lastName;
    public String rollNum;
    public String address;
    public String idNum;
    public String dob;
    public String ctzNum;
    public String department;
    public String expiryDate;
    public byte [] photo;
    public String password;

    DatabaseHelper DHelper;

    public void checkDatabase() {
        this.rollNum = "dds";
        DHelper = new DatabaseHelper(context);

        DHelper.insertData("a","a","a","a","a","a","a","a","a","a");
        //


        //Cursor c = DHelper.getSpecificData(1);
       // if (c.moveToFirst()) {

            //firstName=c.getString(1);
           // lastName =c.getString(2);
            //rollNum = "dds";
            //rollNum = c.getString(4);
           // password =c.getString(12);
           // address =c.getString(4);
           // idNum=c.getString(5);
           // expiryDate=c.getString(6);
           // dob=c.getString(7);
           // ctzNum=c.getString(8);
           // department =c.getString(9);
           // photo = c.getBlob(c.getColumnIndex("photo"));
           // ByteArrayInputStream inputStream = new ByteArrayInputStream(photo);
          // Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
           // photo.setImageBitmap(bitmap);
            //Toast.makeText(getActivity(), "welldone zimba", Toast.LENGTH_SHORT).show();
       // } else {
         //   Toast.makeText(this, "Please Enter the INFORMATION FIRST", Toast.LENGTH_SHORT).show();
      // }


    }
}
