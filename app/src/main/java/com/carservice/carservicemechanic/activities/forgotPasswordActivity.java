package com.carservice.carservicemechanic.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.carservice.carservicemechanic.R;

public class forgotPasswordActivity extends AppCompatActivity {

    private EditText forgotEmailAddress;
    private TextView textViewReturnHome;
    private Button resetButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);


        textViewReturnHome = findViewById(R.id.textViewReturnHome);
        textViewReturnHome.setOnClickListener(
                v ->{ startActivity(new Intent(forgotPasswordActivity.this, LoginActivity.class));
                }
        );

    }

}


// check if email is valid
// first name
// last name

    // if valid send email link -> to reset password