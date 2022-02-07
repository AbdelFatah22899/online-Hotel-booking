package com.example.a2020_android_training;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    EditText gmail, password, repassword;
    Button add;
    customer_database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        gmail=(EditText)findViewById(R.id.registration_text_gmail);
        password=(EditText)findViewById(R.id.registration_text_password);
        repassword=(EditText)findViewById(R.id.registration_text_repassword);
        add=(Button)findViewById(R.id.registration_button_add);
        db=new customer_database(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gmail.getText().toString().equals("")||password.getText().toString().equals("")||repassword.getText().toString().equals(""))
                    Toast.makeText(Registration.this, "Enter your gmail, password, and re_enter password", Toast.LENGTH_SHORT).show();
                else if(!gmail.getText().toString().contains("@gmail.com"))
                    Toast.makeText(Registration.this, "gmail must contain\"@gmail.com\"", Toast.LENGTH_SHORT).show();
                else if(!repassword.getText().toString().equals(password.getText().toString()))
                    Toast.makeText(Registration.this, "password must match with re_password", Toast.LENGTH_SHORT).show();
                else if(db.search_found(gmail.getText().toString(), password.getText().toString()))
                   Toast.makeText(Registration.this, "email already exist", Toast.LENGTH_SHORT).show();
                else {
                    db.add(gmail.getText().toString(), password.getText().toString());
                    Toast.makeText(Registration.this, "email added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}