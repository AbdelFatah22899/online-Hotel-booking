package com.example.a2020_android_training;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLClientInfoException;

public class MainActivity extends AppCompatActivity {

    Button add, regist,go;
    EditText gmail, password;
    customer_database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new customer_database(this);
        gmail=(EditText)findViewById(R.id.login_gmail);
        password=(EditText)findViewById(R.id.login_password);

        add_data(this);
        regist=(Button)findViewById(R.id.registration_goto);
        regist.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registration.class));
            }
        });

    }

    public void add_data(final Context c)
    {
        Button add=(Button)findViewById(R.id.login_button_search);
        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(gmail.getText().toString().equals("") || password.getText().toString().equals(""))
                    Toast.makeText(c, "Enter your gmail and password", Toast.LENGTH_SHORT).show();
                else if(!gmail.getText().toString().contains("@gmail.com"))
                    Toast.makeText(c, "gmail must contain \"@gmail.com\"", Toast.LENGTH_SHORT).show();
                else if(!db.search_found(gmail.getText().toString(), password.getText().toString()))
                    Toast.makeText(c, "wrong account", Toast.LENGTH_SHORT).show();
                else if(gmail.getText().toString().equals("Kazem@gmail.com") && password.getText().toString().equals("1234"))
                    startActivity(new Intent(MainActivity.this, firstAdminPage.class));
                else
                {
                    Intent intent = new Intent(MainActivity.this, Home_page.class);
                    intent.putExtra("gmail", gmail.getText().toString());
                    startActivity(intent);
                }

            }
        });

    }

}
