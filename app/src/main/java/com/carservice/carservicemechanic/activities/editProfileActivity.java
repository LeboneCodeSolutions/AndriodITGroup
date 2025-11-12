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
    private TextView fName; // ✅ Use tvFullName instead of tvMechanicName
    private TextView lName;
    private Button btnBackHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        btnBackHome.setOnClickListener(v ->
                startActivity(new Intent(editProfileActivity.this, MechanicDashboardActivity.class))
        );

    }


}
