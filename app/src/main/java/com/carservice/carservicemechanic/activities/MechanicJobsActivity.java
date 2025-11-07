package com.carservice.carservicemechanic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.carservice.carservicemechanic.R;
import com.carservice.carservicemechanic.adapters.MechanicJobAdapter;
import com.carservice.carservicemechanic.models.ServiceRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MechanicJobsActivity extends AppCompatActivity {

    RecyclerView rvJobs;
    ArrayList<ServiceRequest> jobsList = new ArrayList<>();
    MechanicJobAdapter adapter;
    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_jobs);

        rvJobs = findViewById(R.id.rvMechanicJobs);
        rvJobs.setLayoutManager(new LinearLayoutManager(this));

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        adapter = new MechanicJobAdapter(jobsList);
        rvJobs.setAdapter(adapter);

        loadJobs();
    }

    private void loadJobs() {
        db.collection("serviceRequests")
                .whereEqualTo("status", "pending")
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;

                    jobsList.clear();
                    for (QueryDocumentSnapshot doc : value) {
                        ServiceRequest s = doc.toObject(ServiceRequest.class);
                        s.setId(doc.getId());
                        jobsList.add(s);
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}
