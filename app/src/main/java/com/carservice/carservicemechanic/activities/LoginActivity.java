package com.carservice.carservicemechanic.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carservice.carservicemechanic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView linkRegister;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // ✅ Initialize views properly
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        linkRegister = findViewById(R.id.linkRegister);

        // ✅ Link to registration activity
        linkRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });

        // ✅ Login button click
        btnLogin.setOnClickListener(v -> loginMechanic());

    }

    private void loginMechanic() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "User email and password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user == null) return;

                    String uid = user.getUid();

                    db.collection("users").document(uid).get()
                            .addOnSuccessListener(doc -> {
                                if (doc.exists() && "mechanic".equals(doc.getString("role"))) {
                                    startActivity(new Intent(LoginActivity.this, MechanicDashboardActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(this, "Sorry you are not a mechanic !!!", Toast.LENGTH_LONG).show();
                                    auth.signOut();
                                }
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "Error fetching user data: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                            );
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to Login!!: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
