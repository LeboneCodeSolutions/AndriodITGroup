package com.carservice.carservicemechanic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.carservice.carservicemechanic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private EditText regFirstName, regLastName, regEmailAddress, regPassword;
    private Button regButton;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        regFirstName = findViewById(R.id.regFirstName);
        regLastName = findViewById(R.id.regLastName);
        regEmailAddress = findViewById(R.id.regEmailAddress);
        regPassword = findViewById(R.id.regPassword);
        regButton = findViewById(R.id.regButton);

        regButton.setOnClickListener(v -> registerMechanic());
    }

    private void registerMechanic() {
        String firstName = regFirstName.getText().toString().trim();
        String lastName = regLastName.getText().toString().trim();
        String email = regEmailAddress.getText().toString().trim();
        String password = regPassword.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            regFirstName.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            regLastName.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            regEmailAddress.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            regPassword.setError("Required");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(result -> {
                    String uid = result.getUser().getUid();

                    HashMap<String, Object> user = new HashMap<>();
                    user.put("uid", uid);
                    user.put("firstName", firstName);
                    user.put("lastName", lastName);
                    user.put("email", email);
                    user.put("role", "mechanic"); // changed from "client" to "mechanic"

                    db.collection("users").document(uid)
                            .set(user)
                            .addOnSuccessListener(r -> {
                                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this, MechanicDashboardActivity.class));
                                finish();
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "Firestore Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                            );

                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Authentication Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
