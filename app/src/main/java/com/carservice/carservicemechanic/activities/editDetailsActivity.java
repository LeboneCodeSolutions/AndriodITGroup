package com.carservice.carservicemechanic.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.carservice.carservicemechanic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class editDetailsActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private EditText fName, lName, email;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Link views
        fName = findViewById(R.id.editFirstName);
        lName = findViewById(R.id.editLastName);
        email = findViewById(R.id.editEmailAddress);
        btnSave = findViewById(R.id.btnSaveDetails);

        // Load existing data from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String firstName = prefs.getString("firstName", "Mechanic");
        String lastName = prefs.getString("lastName", "");
        String userEmail = prefs.getString("email", auth.getCurrentUser() != null ? auth.getCurrentUser().getEmail() : "");

        fName.setText(firstName);
        lName.setText(lastName);
        email.setText(userEmail);

        // Save button click listener
        btnSave.setOnClickListener(v -> saveProfileChanges());
    }

    private void saveProfileChanges() {
        String updatedFirstName = fName.getText().toString().trim();
        String updatedLastName = lName.getText().toString().trim();
        String updatedEmail = email.getText().toString().trim();
        String regExLetters = "^[a-zA-Z]+$";
        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "No logged-in user", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!updatedFirstName.matches(regExLetters)){
            Toast.makeText(this, "Enter letters", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!updatedLastName.matches(regExLetters)){
            Toast.makeText(this, "Enter letters", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = auth.getCurrentUser().getUid();

        // Create a map of updated fields
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstName", updatedFirstName);
        updates.put("lastName", updatedLastName);
        // Usually, email is not updated in Firestore manually; skip if needed
        // updates.put("email", updatedEmail);

        // Update Firestore
        db.collection("users").document(uid)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    // Update SharedPreferences as well
                    SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("firstName", updatedFirstName);
                    editor.putString("lastName", updatedLastName);
                    editor.apply();

                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error updating profile: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
