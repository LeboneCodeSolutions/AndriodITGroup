package com.carservice.carservicemechanic.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carservice.carservicemechanic.R;

import java.util.ArrayList;
import java.util.List;

public class JobBoardActivity extends AppCompatActivity {

    RecyclerView rvJobList;
    JobAdapter adapter;
    List<CarJob> jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_board);

        rvJobList = findViewById(R.id.rvJobList);
        rvJobList.setLayoutManager(new LinearLayoutManager(this));

        // Sample data
        jobs = new ArrayList<>();
        jobs.add(new CarJob("John", "Doe", "john@example.com", "ABC123", "1HGCM82633A004352"));
        jobs.add(new CarJob("Jane", "Smith", "jane@example.com", "XYZ789", "1HGCM82633A004353"));
        // Add as many cars as you need

        adapter = new JobAdapter(jobs, new JobAdapter.OnAcceptClickListener() {
            @Override
            public void onAccept(CarJob job) {
                // Handle accept job click
                // e.g., update database or show a Toast
                // Toast.makeText(JobBoardActivity.this, "Accepted: " + job.getClientName(), Toast.LENGTH_SHORT).show();
            }
        });

        rvJobList.setAdapter(adapter);
    }
}
