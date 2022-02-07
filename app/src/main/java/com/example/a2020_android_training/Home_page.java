package com.example.a2020_android_training;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Vector;

public class Home_page extends AppCompatActivity {

    ImageButton[] hotel=new ImageButton[3];
    TextView[] text_hotel=new TextView[3];
    hotel_database Hotel_db;
    Cursor c;
    Vector<Integer> img_num=new Vector<>();
    String gmail;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        gmail=getIntent().getExtras().getString("gmail");
        hotel[0]=(ImageButton)findViewById(R.id.hotel1);
        hotel[1]=(ImageButton)findViewById(R.id.hotel2);
        hotel[2]=(ImageButton)findViewById(R.id.hotel3);

        text_hotel[0]=(TextView)findViewById(R.id.text_hotel1);
        text_hotel[1]=(TextView)findViewById(R.id.text_hotel2);
        text_hotel[2]=(TextView)findViewById(R.id.text_hotel3);

        spinner=(Spinner)findViewById(R.id.order_by);

        Hotel_db=new hotel_database(this);
        Hotel_db.add();
        c= Hotel_db.retrieve_all_data(spinner.getSelectedItem().toString());
        draw();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                c= Hotel_db.retrieve_all_data(spinner.getSelectedItem().toString());
                img_num.clear();
                draw();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        hotel[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               Intent n = new Intent(Home_page.this, show_hotel.class);
               Bundle b = new Bundle();
               b.putString("img_num",String.valueOf(img_num.elementAt(0)));
               b.putString("gmail", gmail);
               n.putExtras(b);
               startActivity(n);
               }
        });
        hotel[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Home_page.this, show_hotel.class);
                Bundle b = new Bundle();
                b.putString("img_num",String.valueOf(img_num.elementAt(1)));
                b.putString("gmail", gmail);
                n.putExtras(b);
                startActivity(n);
            }
        });
        hotel[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Home_page.this, show_hotel.class);
                Bundle b = new Bundle();
                b.putString("img_num",String.valueOf(img_num.elementAt(2)));
                b.putString("gmail",gmail);
                n.putExtras(b);
                startActivity(n);
            }
        });

    }
    public void draw()
    {
        for(int i=0; i<c.getCount(); i++)
        {
            hotel[i].setImageResource(Integer.parseInt(c.getString(8)));
            img_num.add(Integer.parseInt(c.getString(8)));
            text_hotel[i].setText("Name: "+c.getString(0)+"\naddress= "+c.getString(7));
            c.moveToNext();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout_review_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.review)
        {
            Intent intent=new Intent(this, review.class);
            Bundle bundle=new Bundle();
            bundle.putString("gmail",gmail);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return true;
    }
}