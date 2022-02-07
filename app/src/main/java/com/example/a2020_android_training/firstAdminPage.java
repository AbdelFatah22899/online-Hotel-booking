package com.example.a2020_android_training;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class firstAdminPage extends AppCompatActivity {

    public hotel_database hotelDb = new hotel_database(this);
    public Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_admin_page);

        final EditText singleCap = (EditText)findViewById(R.id.singleRmCap);
        final EditText doubleCap = (EditText)findViewById(R.id.doubleRmCap);
        final EditText singlePrice = (EditText)findViewById(R.id.singleRmPrice);
        final EditText doublePrice = (EditText)findViewById(R.id.doubleRmPrice);
        final EditText quality = (EditText)findViewById(R.id.quality);
        final Button update = (Button)findViewById(R.id.updateBtn);
        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String hotelName = spinner.getSelectedItem().toString();
                cursor = hotelDb.get_hotel(hotelName);
                singleCap.setText(cursor.getString(1));
                doubleCap.setText(cursor.getString(2));
                singlePrice.setText(cursor.getString(3));
                doublePrice.setText(cursor.getString(4));
                quality.setText(cursor.getString(5));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotelDb.update_Hotel(cursor.getString(0),cursor.getString(0),Integer.parseInt(singleCap.getText().toString()),
                        Integer.parseInt(doubleCap.getText().toString()),
                        Integer.parseInt(singlePrice.getText().toString()),Integer.parseInt(doublePrice.getText().toString()),
                        Integer.parseInt(quality.getText().toString())
                        ,Float.parseFloat(cursor.getString(6)),cursor.getString(7)
                        ,Integer.parseInt(cursor.getString(8)));
                Toast.makeText(firstAdminPage.this,"Hotel Updated",Toast.LENGTH_SHORT).show();
            }
        });

    }
}