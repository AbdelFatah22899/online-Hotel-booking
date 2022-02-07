package com.example.a2020_android_training;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.TypeConverter;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class show_hotel extends AppCompatActivity {

    hotel_database hotel_db=new hotel_database(this);
    hotel_requests requests_db=new hotel_requests(this);
    Cursor hotel;
    EditText start_date, last_date, rooms_number;
    Spinner type;
    TextView total_price_value, total_price_text;
    String gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hotel);
        hotel= hotel_db.get_hotel(Integer.parseInt(getIntent().getExtras().getString("img_num")));
        gmail=getIntent().getExtras().getString("gmail");
        total_price_text=(TextView)findViewById(R.id.show_total_price_text);
        total_price_value=(TextView)findViewById(R.id.show_total_price);
        total_price_value.setText("");  total_price_text.setText("");

        start_date=(EditText)findViewById(R.id.show_start_date_value);
        last_date=(EditText)findViewById(R.id.show_last_date_value);
        rooms_number=(EditText)findViewById(R.id.show_rooms_num_value);
        type=(Spinner) findViewById(R.id.show_room_type);
        draw();

        rooms_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!rooms_number.getText().toString().equals("")) {
                    try {
                        int total;
                        if (type.getSelectedItem().equals("single_room")) {
                            total = Integer.parseInt(hotel.getString(3)) * Integer.parseInt(rooms_number.getText().toString());
                        } else {
                            total = Integer.parseInt(hotel.getString(4)) * Integer.parseInt(rooms_number.getText().toString());
                        }
                        total_price_text.setText("total price: ");
                        total_price_value.setText(String.valueOf(total));
                    }
                    catch(Exception e){
                        rooms_number.setText(""); total_price_text.setText("");    total_price_value.setText("");
                        Toast.makeText(show_hotel.this,"Number of rooms must be integers",Toast.LENGTH_SHORT).show();}
                }
            }
        });
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    int total;
                    if (type.getSelectedItem().equals("single_room")) {
                        total = Integer.parseInt(hotel.getString(3)) * Integer.parseInt(rooms_number.getText().toString());
                    } else {
                        total = Integer.parseInt(hotel.getString(4)) * Integer.parseInt(rooms_number.getText().toString());
                    }
                    total_price_text.setText("total price: ");
                    total_price_value.setText(String.valueOf(total));
                }
                catch(Exception e){}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button submit=(Button) findViewById(R.id.show_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (start_date.getText().toString().equals("") || last_date.getText().toString().equals("") || rooms_number.getText().toString().equals(""))
                    Toast.makeText(show_hotel.this, "you should enter start_date, last date, and rooms_number", Toast.LENGTH_SHORT).show();
                else if(search_rooms_available())
                {
                    requests_db.add(hotel.getString(0), gmail, type.getSelectedItem().toString(), start_date.getText().toString(), last_date.getText().toString(), Integer.parseInt(rooms_number.getText().toString()), 0);
                    start_date.setText("");
                    last_date.setText("");
                    rooms_number.setText("");
                    total_price_text.setText("");
                    total_price_value.setText("");
                    Toast.makeText(show_hotel.this, "added successfully", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void draw()
    {
        ImageView image= (ImageView) findViewById(R.id.logo);
        image.setImageResource(Integer.parseInt(hotel.getString(8)));

        TextView name=(TextView) findViewById(R.id.Show_hotel_name);
        name.setText(hotel.getString(0));

        TextView description_name =(TextView) findViewById(R.id.attributs_name);
        description_name.setText("single_price: \nDouble_price: \n\nquality: \nreview: \n\nAddress: ");

        TextView description_values=(TextView) findViewById(R.id.attributs_value);
        description_values.setText(hotel.getString(3)+"\n"+hotel.getString(4)+"\n\n"+hotel.getString(5)+"\n"+hotel.getString(6)+"\n\n"+hotel.getString(7));

    }
    Date startdate, lastdate, reserved_start_date, reserved_last_date;
    public boolean search_rooms_available()
    {
        Cursor c= requests_db.retrieve_all_data(hotel.getString(0));
        int rooms_occupied=0;
        try {
            startdate=new SimpleDateFormat("dd/MM/yyyy").parse(start_date.getText().toString());
            lastdate=new SimpleDateFormat("dd/MM/yyyy").parse(last_date.getText().toString());
            if(startdate.compareTo(lastdate)>0) {
                Toast.makeText(this, "Start date can't be more than last date", Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
                for (int i = 0; i < c.getCount(); i++) {
                    reserved_start_date = new SimpleDateFormat("dd/MM/yyyy").parse(c.getString(3));
                    reserved_last_date = new SimpleDateFormat("dd/MM/yyyy").parse(c.getString(4));
                    if (c.getString(2).equals(type.getSelectedItem().toString()))
                        if (!(reserved_last_date.compareTo(startdate) < 0 || reserved_start_date.compareTo(lastdate) > 0)) {
                            rooms_occupied += Integer.parseInt(c.getString(5));
                        }
                    c.moveToNext();
                }
                int rooms_available = 0;
                if (type.getSelectedItem().toString().equals("single_room"))
                    rooms_available = Integer.parseInt(hotel.getString(1)) - rooms_occupied;
                else
                    rooms_available = Integer.parseInt(hotel.getString(2)) - rooms_occupied;
                if (rooms_available < Integer.parseInt(rooms_number.getText().toString())) {
                    Toast.makeText(show_hotel.this, "sorry, you can't. rooms available with that type: " + rooms_available, Toast.LENGTH_SHORT).show();
                    return false;
                }
                else
                    return true;
            }
        }
        catch (Exception e){
            Toast.makeText(show_hotel.this,"Date must be like format \"dd/MM/yyyy\"",Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}