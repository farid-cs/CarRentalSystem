import java.util.*;

public class User {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String gender;
    private int age;
    private String address;
    private List<Vehicle> rentedVehicles;
    private final int adminpassword = 9988;
    private Boolean isAdmin = false;
    private String ID;

    public User(String username, String password, String name, String surname, String gender, int age, String address, int adminpassword, String id) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        if (age >= 18) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("User must be at least 18 years old.");
        }
        this.address = address;
        this.rentedVehicles = new ArrayList<>();
        this.setAdmin(adminpassword);
        if (id == "") {
            this.ID = UUID.randomUUID().toString();
        }else {
            this.ID = id;
        }
    }

    private void setAdmin(int adminpassword) {
        if(adminpassword == this.adminpassword) {
            this.isAdmin = true;
        }
    }

    public User(String username, String password, List<Vehicle> rentedVehicles) {
        this.username = username;
        this.password = password;
        this.rentedVehicles = rentedVehicles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public String getuserID() {
        return ID;
    }

    public void setAge(int age) {
        if (age >= 18) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("User must be at least 18 years old.");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Vehicle> getRentedVehicles() {
        return rentedVehicles;
    }

    public void setRentedVehicles(List<Vehicle> rentedVehicles) {
        this.rentedVehicles = rentedVehicles;
    }

    public void addRentedVehicle(Vehicle vehicle) {
        rentedVehicles.add(vehicle);
    }

    public void removeRentedVehicle(Vehicle vehicle) {
        rentedVehicles.remove(vehicle);
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public String GetUserID() {
        return this.ID;
    }

    public String Printuser() {
        StringBuilder sb = new StringBuilder();
        sb.append("Username: ").append(username).append("\n");
        sb.append("Password: ").append(password).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Surname: ").append(surname).append("\n");
        sb.append("Gender: ").append(gender).append("\n");
        sb.append("Age: ").append(age).append("\n");
        sb.append("Address: ").append(address).append("\n");
        sb.append("Admin: ").append(isAdmin ? "Yes" : "No").append("\n");
        sb.append("ID : ").append(this.GetUserID()).append("\n");
        sb.append("Rented Vehicles: ");
        if (rentedVehicles.isEmpty()) {
            sb.append("None");
        } else {
            for (Vehicle vehicle : rentedVehicles) {
                sb.append(vehicle.getRegistrationNumber()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()); 
        }
        return sb.toString();
    }
}
