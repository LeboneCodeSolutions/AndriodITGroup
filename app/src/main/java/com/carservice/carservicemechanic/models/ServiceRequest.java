package com.carservice.carservicemechanic.models;

import java.io.Serializable;

public class ServiceRequest implements Serializable {
    private String id;
    private String serviceType;
    private String vehicleId;
    private String status;
    private String description;
    private String mechanicId;

    public ServiceRequest() {
        // Default constructor required for calls to DataSnapshot.getValue(ServiceRequest.class)
    }

    public ServiceRequest(String id, String serviceType, String vehicleId, String status, String description, String mechanicId) {
        this.id = id;
        this.serviceType = serviceType;
        this.vehicleId = vehicleId;
        this.status = status;
        this.description = description;
        this.mechanicId = mechanicId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(String mechanicId) {
        this.mechanicId = mechanicId;
    }
}
