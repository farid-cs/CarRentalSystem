enum VehicleType {
    SEDAN,
    SUV,
    TRUCK
}

public class Vehicle {
    private String registrationNumber;
    private String make;
    private String model;
    private VehicleType type;
    private double rentPrice;

    public Vehicle(String registrationNumber, String make, String model, VehicleType type, double rentPrice) {
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.type = type;
        this.rentPrice = rentPrice;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String toString() {
        return make + " " + model + " (" + registrationNumber + ") - Rent Price: $" + rentPrice;
    }
}


