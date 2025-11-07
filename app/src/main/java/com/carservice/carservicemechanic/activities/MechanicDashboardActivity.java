package com.carservice.carservicemechanic.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.carservice.carservicemechanic.R;
import com.google.firebase.auth.FirebaseAuth;

public class MechanicDashboardActivity extends AppCompatActivity {

    Button btnViewJobs, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_dashboard);

        btnViewJobs = findViewById(R.id.btnViewJobs);
        btnLogout = findViewById(R.id.btnLogout);

        btnViewJobs.setOnClickListener(v ->
                startActivity(new Intent(MechanicDashboardActivity.this, MechanicJobsActivity.class))
        );

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MechanicDashboardActivity.this, LoginActivity.class));
            finish();
        });
    }
}
