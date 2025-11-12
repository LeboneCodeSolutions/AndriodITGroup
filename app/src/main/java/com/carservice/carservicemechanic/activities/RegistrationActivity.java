package com.carservice.carservicemechanic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.carservice.carservicemechanic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private EditText regFirstName, regLastName, regEmailAddress, regPassword;
    private Button regButton;
    private TextView linkSignUp;
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

        linkSignUp = findViewById(R.id.linkSignUp);

        //link to the registration page
        linkSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerMechanic() {
        // hash password
        String firstName = regFirstName.getText().toString().trim();
        String lastName = regLastName.getText().toString().trim();
        String email = regEmailAddress.getText().toString().trim();
        String password = regPassword.getText().toString().trim();
        String regExLetters = "^[a-zA-Z]+$";
        String regExEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

        if (TextUtils.isEmpty(firstName) || !firstName.matches(regExLetters)) {
            regFirstName.setError("Required");
            return;
        }  if (TextUtils.isEmpty(lastName) || !lastName.matches(regExLetters)) {
            regLastName.setError("Required");
            return;
        }
         if(TextUtils.isEmpty(email) || !email.matches(regExEmail)) {
            regEmailAddress.setError("Required");
            return;
        }
         if (TextUtils.isEmpty(password) || !password.matches(passwordRegex)) {
            regPassword.setError("Required");
            return;
        }
            // validation to check if user exists
        {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(result -> {
                        String uid = result.getUser().getUid();

                        HashMap<String, Object> user = new HashMap<>();
                        user.put("uid", uid);
                        user.put("firstName", firstName);
                        user.put("lastName", lastName);
                        user.put("email", email);
                        user.put("role", "mechanic");

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
}
