package com.carservice.carservicemechanic.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carservice.carservicemechanic.R;
import com.carservice.carservicemechanic.activities.MechanicJobDetailsActivity;
import com.carservice.carservicemechanic.models.ServiceRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MechanicJobAdapter extends RecyclerView.Adapter<MechanicJobAdapter.ViewHolder> {

    ArrayList<ServiceRequest> list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public MechanicJobAdapter(ArrayList<ServiceRequest> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mechanic_job, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceRequest s = list.get(position);

        holder.service.setText("Service: " + s.getServiceType());
        holder.vehicle.setText("Vehicle ID: " + s.getVehicleId());
        holder.status.setText("Status: " + s.getStatus());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), MechanicJobDetailsActivity.class);
            i.putExtra("job_data", s);
            v.getContext().startActivity(i);
        });

        if ("pending".equals(s.getStatus())) {
            holder.accept.setVisibility(View.VISIBLE);
            holder.accept.setOnClickListener(v -> {
                String mechanicId = auth.getCurrentUser().getUid();
                db.collection("serviceRequests").document(s.getId())
                        .update("status", "in-progress", "mechanicId", mechanicId)
                        .addOnSuccessListener(unused -> {
                            holder.status.setText("Status: in-progress");
                            holder.accept.setVisibility(View.GONE);
                        });
            });
        } else {
            holder.accept.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView service, vehicle, status;
        Button accept;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            service = itemView.findViewById(R.id.tvJobService);
            vehicle = itemView.findViewById(R.id.tvJobVehicle);
            status = itemView.findViewById(R.id.tvJobStatus);
            accept = itemView.findViewById(R.id.btnAcceptJob);
        }
    }
}
