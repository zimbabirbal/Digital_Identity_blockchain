package com.example.bjimba.digitalidentitycard;

/**
 * Created by taman on 2/22/2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Information.db";
    public static final String TABLE_NAME = "Identity";
    public static final String  COL_1= "ID" ;
    public static final String  COL_2="firstName" ;
    public static final String  COL_3="lastName" ;
    public static final String  COL_4="rollNo" ;
    public static final String  COL_5="address" ;
    public static final String  COL_6="idNum";
    public static final String  COL_7="dob" ;
    public static final String  COL_8="ctzNum";
    public static final String  COL_9="department";
    public static final String  COL_10="expiryDate" ;
    public static final String  COL_11="photo";
    public static final String  COL_12="password";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL ("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,firstName TEXT,lastName TEXT,rollNo TEXT,address TEXT,idNum TEXT,dob TEXT,ctzNum TEXT,department TEXT,expiryDate TEXT, photo BLOB, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public boolean insertData (String firstName, String lastName,String rollNo,String address,String idNum,String dob,String ctzNum,String department,String expiryDate,String password) //
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,firstName);
        contentValues.put(COL_3,lastName);
        contentValues.put(COL_4,rollNo);
        contentValues.put(COL_5,address);
        contentValues.put(COL_6,idNum);
        contentValues.put(COL_7,dob);
        contentValues.put(COL_8,ctzNum);
        contentValues.put(COL_9,department);
        contentValues.put(COL_10,expiryDate);
        //contentValues.put(COL_11,photo);
        contentValues.put(COL_12,password);
        long result =sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if(result ==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return  res;
    }
    public  boolean updateData(String id,String firstName, String lastName,String rollNo,String address,String idNum,String dob,String ctzNum,String department,String expiryDate,byte [] photo,String password)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,firstName);
        contentValues.put(COL_3,lastName);
        contentValues.put(COL_4,rollNo);
        contentValues.put(COL_5,address);
        contentValues.put(COL_6,idNum);
        contentValues.put(COL_7,dob);
        contentValues.put(COL_8,ctzNum);
        contentValues.put(COL_9,department);
        contentValues.put(COL_10,expiryDate);
        contentValues.put(COL_11,photo);
        contentValues.put(COL_11,password);
        sqLiteDatabase.update(TABLE_NAME,contentValues,"ID=?",new String [] {id});
        return true;
    }
    public Integer deleteData(String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return  sqLiteDatabase.delete(TABLE_NAME,"ID=?",new String[]{id});
    }
    public Cursor getSpecificData(Integer id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE ID ='"+id+"'" ,null);
        //Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " +TABLE_NAME+ " WHERE " + "ID=?", new String[] {id});

        return  res;
    }
}
