package com.carservice.carservicemechanic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import com.carservice.carservicemechanic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MechanicDashboardActivity extends AppCompatActivity {

    private Button btnViewJobs, btnLogout, btnCreateInvoice;
    private TextView tvWelcome;
    private ImageView editProfile;

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
       // btnViewJobs = findViewById(R.id.btnViewJobs);
        btnLogout = findViewById(R.id.btnLogout);
        btnCreateInvoice = findViewById(R.id.btnCreateInvoice);
        tvWelcome = findViewById(R.id.tvWelcome); // add this TextView in XML
        editProfile = findViewById(R.id.editProfile);
        // Load mechanic name
        loadMechanicName();

    editProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MechanicDashboardActivity.this, editProfileActivity.class);
            startActivity(intent);
        });

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
                            // ✅ Save name for other pages
                            SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("firstName", firstName);
                            editor.putString("lastName", lastName);
                            editor.apply();
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
