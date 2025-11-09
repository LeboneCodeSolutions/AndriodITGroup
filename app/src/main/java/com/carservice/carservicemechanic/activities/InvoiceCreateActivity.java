package com.carservice.carservicemechanic.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carservice.carservicemechanic.R;
import com.carservice.carservicemechanic.utils.FirebaseUtils;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InvoiceCreateActivity extends AppCompatActivity {

    EditText etServiceId, etWorkDone, etPartsUsed, etCost, etClientId;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_create);

        etServiceId = findViewById(R.id.etServiceId);
        etWorkDone = findViewById(R.id.etWorkDone);
        etPartsUsed = findViewById(R.id.etPartsUsed);
        etCost = findViewById(R.id.etCost);
        etClientId = findViewById(R.id.etClientId);
        btnSubmit = findViewById(R.id.btnSubmitInvoice);

        btnSubmit.setOnClickListener(v -> saveInvoice());
    }

    private void saveInvoice() {
        String serviceId = etServiceId.getText().toString();
        String workDone = etWorkDone.getText().toString();
        String partsUsed = etPartsUsed.getText().toString();
        String costText = etCost.getText().toString();
        String userId = etClientId.getText().toString(); // Client UID from service request

        if (serviceId.isEmpty() || workDone.isEmpty() || costText.isEmpty() || userId.isEmpty()) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalCost = Double.parseDouble(costText);
        String mechanicId = FirebaseUtils.getUid(); //note

        Map<String, Object> invoice = new HashMap<>();
        invoice.put("serviceId", serviceId);
        invoice.put("userId", userId);
        invoice.put("mechanicName", "Mechanic"); // later change to fetch mechanic name
        invoice.put("workDone", workDone);
        invoice.put("partsUsed", partsUsed);
        invoice.put("totalCost", totalCost);
        invoice.put("timestamp", System.currentTimeMillis());
        invoice.put("paid", false);

        FirebaseFirestore.getInstance().collection("invoices")
                .add(invoice)
                .addOnSuccessListener(doc -> {
                    Toast.makeText(this, "Invoice Successfully Created ", Toast.LENGTH_LONG).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}
