package com.carservice.carservicemechanic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.carservice.carservicemechanic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MechanicDashboardActivity extends AppCompatActivity {

    private Button btnViewJobs, btnLogout, btnCreateInvoice;
    private TextView tvWelcome;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_dashboard);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        btnViewJobs = findViewById(R.id.btnViewJobs);
        btnLogout = findViewById(R.id.btnLogout);
        btnCreateInvoice = findViewById(R.id.btnCreateInvoice);
        tvWelcome = findViewById(R.id.tvWelcome); // add this TextView in XML

        // Load mechanic name
        loadMechanicName();

        // Navigation buttons
        btnViewJobs.setOnClickListener(v ->
                startActivity(new Intent(MechanicDashboardActivity.this, MechanicJobsActivity.class))
        );

        btnCreateInvoice.setOnClickListener(v ->
                startActivity(new Intent(MechanicDashboardActivity.this, InvoiceCreateActivity.class))
        );

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MechanicDashboardActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void loadMechanicName() {
        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "No user logged in!", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = auth.getCurrentUser().getUid();

        db.collection("users").document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String firstName = documentSnapshot.getString("firstName");
                        String lastName = documentSnapshot.getString("lastName");

                        if (firstName != null && lastName != null) {
                            tvWelcome.setText("Welcome, " + firstName + " " + lastName + "!");
                        } else {
                            tvWelcome.setText("Welcome, Mechanic!");
                        }
                    } else {
                        tvWelcome.setText("Welcome, Mechanic!");
                        Toast.makeText(this, "User profile not found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error fetching name: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
