package com.example.a2020_android_training;

import android.app.VoiceInteractor;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

abstract public class Database_add_retrieve extends SQLiteOpenHelper {
    SQLiteDatabase sqldp;
    String customer_table="customers", hotel_table="Hotel", data_table="hotels_Requests";
    public Database_add_retrieve(Context context) {
        super(context, "sqldp", null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table "+customer_table+" (gmail text ,password text)");
        db.execSQL("create table "+hotel_table+"(name text primary key, single_capacity integer, double_capacity integer, singleRoom_Price integer,doubleRoom_price integer, quality integer, review float, address text, image_num integer)");
       //                                        0                      1                        2                        3                        4                         5                6             7             8
        db.execSQL("create table "+data_table+"(Hotel_name text,gmail text , type text,start_date text, end_date text, rooms_number integer, review float)");
    //                                          0               1            2          3               4              5                     6
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ customer_table);
        db.execSQL("drop table if exists "+ hotel_table);
        db.execSQL("drop table if exists "+ data_table);
        onCreate(db);
    }

}

class customer_database extends Database_add_retrieve
{

    public customer_database(Context context) {
        super(context);
    }
    public void add(String gmail, String password)
    {
        ContentValues cv=new ContentValues();
        cv.put("gmail", gmail);
        cv.put("password", password);
        sqldp=getWritableDatabase();
        sqldp.insert(customer_table,null,cv);
        sqldp.close();
    }
    public Cursor retrieve_all_data()
    {
        sqldp=getReadableDatabase();
        String[] row={"gmail", "password"};
        Cursor c=sqldp.query(customer_table,row, null, null, null, null, null);
        if(c!=null)
            c.moveToFirst();
        sqldp.close();
        return c;
    }
    public boolean search_found(String gmail, String password)
    {

        sqldp=getReadableDatabase();
        Cursor c= sqldp.rawQuery("SELECT *FROM "+customer_table+" WHERE gmail=? AND password=?",new String[]{gmail, password});
        if (c.getCount() > 0) {
            sqldp.close();
            c.close();
            return true;
        }
        sqldp.close();
        c.close();
        return false;
    }
}
class hotel_database extends Database_add_retrieve
{

    public hotel_database(Context context) {
        super(context);
    }
    public void add()
    {
        if(retrieve_all_data("single_capacity").getCount()<1) {
            sqldp = getWritableDatabase();
            ContentValues row = new ContentValues();
            row.put("name", "crown_hotel");
            row.put("single_capacity", 80);
            row.put("double_capacity", 50);
            row.put("singleRoom_price", 800);
            row.put("doubleRoom_price", 1250);
            row.put("quality", 5);
            row.put("review", 0.0);
            row.put("address", "Tahrir Square, Cairo");
            row.put("image_num", R.drawable.crown_hotel);
            sqldp.insert(hotel_table, null, row);


            row = new ContentValues();
            row.put("name", "hilton_resort");
            row.put("single_capacity", 100);
            row.put("double_capacity", 60);
            row.put("singleRoom_price", 650);
            row.put("doubleRoom_price", 1100);
            row.put("quality", 4);
            row.put("review", 0.0);
            row.put("address", "Luxor, Khaled Bin Al Waleed St");
            row.put("image_num", R.drawable.hilton_resort);
            sqldp.insert(hotel_table, null, row);

            row = new ContentValues();
            row.put("name", "steigenberger_cecil");
            row.put("single_capacity", 40);
            row.put("double_capacity", 100);
            row.put("singleRoom_price", 300);
            row.put("doubleRoom_price", 400);
            row.put("quality", 2);
            row.put("review", 0.0);
            row.put("address", "Raml Station, Alexandria");
            row.put("image_num", R.drawable.steigenberger_cecil);
            sqldp.insert(hotel_table, null, row);

            sqldp.close();
        }
    }
    public Cursor retrieve_all_data(String order_by)
    {
        sqldp=getReadableDatabase();
        String[] row={"name", "single_capacity", "double_capacity", "singleRoom_Price", "doubleRoom_price", "quality", "review", "address", "image_num"};
        Cursor c=sqldp.query(hotel_table,row, null, null, null, null, order_by);
        if(c!=null)
            c.moveToFirst();
        sqldp.close();
        return c;
    }
    public Cursor get_hotel(int img_num)
    {
        sqldp=getReadableDatabase();
        Cursor c= sqldp.rawQuery("SELECT *FROM "+hotel_table+" WHERE image_num=? ",new String[]{String.valueOf(img_num)});
        if(c!=null)
            c.moveToFirst();
        sqldp.close();
        return c;
    }
    public Cursor get_hotel(String hotel_name)
    {
        sqldp=getReadableDatabase();
        Cursor c= sqldp.rawQuery("SELECT *FROM "+hotel_table+" WHERE name=? ",new String[]{String.valueOf(hotel_name)});
        if(c!=null)
            c.moveToFirst();
        sqldp.close();
        return c;
    }
    //                       0                1                2                    3                    4                     5                     6            7             8               9
    public void update_Hotel(String old_name, String new_name, int single_capacity, int double_capacity, int singleRoom_price, int doubleRoom_price, int quality, float review, String address, int image_num)
    {
        sqldp=getWritableDatabase();
        ContentValues row= new ContentValues();
        row.put("name", new_name);
        row.put("single_capacity", single_capacity);
        row.put("double_capacity", double_capacity);
        row.put("singleRoom_price",singleRoom_price);
        row.put("doubleRoom_price",doubleRoom_price);
        row.put("quality",quality);
        row.put("review", review);
        row.put("address",address);
        row.put("image_num", image_num);
        sqldp.update(hotel_table,row,"name like ?",new String[]{old_name});
        sqldp.close();
    }

}
class hotel_requests extends Database_add_retrieve
{

    public hotel_requests(Context context) {
        super(context);
    }
    public void add(String Hotel_name, String gmail, String type, String start_date, String end_date, int rooms_number, float review)
    {
        ContentValues cv=new ContentValues();
        cv.put("Hotel_name", Hotel_name);
        cv.put("gmail", gmail);
        cv.put("type", type);
        cv.put("start_date", start_date);
        cv.put("end_date", end_date);
        cv.put("rooms_number", rooms_number);
        cv.put("review", review);
        sqldp=getWritableDatabase();
        sqldp.insert(data_table,null,cv);
        sqldp.close();
    }
    public Cursor retrieve_all_data(String hotel_name)
    {
        sqldp=getReadableDatabase();
        Cursor c= sqldp.rawQuery("SELECT *FROM "+data_table+" WHERE Hotel_name=? ",new String[]{hotel_name});
        if(c!=null)
            c.moveToFirst();
        sqldp.close();
        return c;
    }
    public Cursor get_gmail_requests(String gmail)
    {
        sqldp=getReadableDatabase();
        Cursor c= sqldp.rawQuery("SELECT *FROM "+data_table+" WHERE gmail=? ",new String[]{gmail});
        if(c!=null)
            c.moveToFirst();
        sqldp.close();
        c.close();
        return c;
    }
    public int reviews_number(String hotel_name)
    {
        int num=0;
        Cursor c=retrieve_all_data(hotel_name);
        for(int i=0; i<c.getCount(); i++)
        {
            if(Float.parseFloat(c.getString(6))>0)
                num++;
            c.moveToNext();
        }
        return num;
    }
    public Cursor retrieve_gmail_hotel(String gmail, String Hotel_name)
    {
        sqldp=getReadableDatabase();
        Cursor c= sqldp.rawQuery("SELECT *FROM "+data_table+" WHERE Hotel_name=? and gmail=? and review=0",new String[]{Hotel_name, gmail});
        if(c!=null)
            c.moveToFirst();
        sqldp.close();
        return c;
    }
    public void update_reservation(String Hotel_name, String gmail, String type, String start_date, String end_date, int rooms_number, float review)
    {
        sqldp=getWritableDatabase();
        ContentValues row= new ContentValues();
        row.put("Hotel_name", Hotel_name);
        row.put("gmail", gmail);
        row.put("type", type);
        row.put("start_date",start_date);
        row.put("end_date",end_date);
        row.put("rooms_number",rooms_number);
        row.put("review", review);

        sqldp.update(data_table,row,"type like ? and start_date like ? and end_date like ?",new String[]{type, start_date, end_date});
        sqldp.close();
    }
}