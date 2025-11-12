package com.carservice.carservicemechanic.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.carservice.carservicemechanic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class editProfileActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private TextView fName;
    private TextView lName;
    private Button btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // ✅ Initialize Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // ✅ Initialize your views
        btnBackHome = findViewById(R.id.btnBackHome);
        fName = findViewById(R.id.profileFirstName);
        lName = findViewById(R.id.profileLastName);

        // ✅ Load mechanic name from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String firstName = prefs.getString("firstName", "Mechanic");
        String lastName = prefs.getString("lastName", "");

        // ✅ Display values in TextViews
        fName.setText(firstName);
        lName.setText(lastName);

        // ✅ Handle "Back Home" button
        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(editProfileActivity.this, MechanicDashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
