package com.carservice.carservicemechanic.activities;

public class CarJob {
    private String clientName;
    private String lastName;
    private String clientEmail;
    private String vehicleReg;
    private String vinNumber;

    public CarJob(String clientName, String lastName, String clientEmail, String vehicleReg, String vinNumber) {
        this.clientName = clientName;
        this.lastName = lastName;
        this.clientEmail = clientEmail;
        this.vehicleReg = vehicleReg;
        this.vinNumber = vinNumber;
    }

    public String getClientName() { return clientName; }
    public String getLastName() { return lastName; }
    public String getClientEmail() { return clientEmail; }
    public String getVehicleReg() { return vehicleReg; }
    public String getVinNumber() { return vinNumber; }
}
