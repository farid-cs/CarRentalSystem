import java.util.*;

class CarRentalSystem {
    private Map<String, User> users;
    private List<Vehicle> vehicles;
    private Boolean isadmin = false;
    private String ANSI_RESET = "\u001B[0m";
    private String ANSI_RED = "\u001B[31m";
    private String ANSI_CYAN = "\u001B[36m";
    private String ANSI_YELLOW = "\u001B[33m";
    private String ANSI_GREEN = "\u001B[32m";
    private String ANSI_PURPLE = "\u001B[35m";
    public CarRentalSystem() {
        users = new HashMap<>();
        vehicles = new ArrayList<>();
        this.CreateMonkVehicles();
        this.CreateMonkUsers();
    }

    public boolean isAdmin() {
        return this.isadmin;
    }

    public void showProfileInfo(String id) {
        Scanner scanner = new Scanner(System.in);
        User user = users.get(id);
        if (user != null){

            System.out.print(ANSI_CYAN + "Enter your current password: " + ANSI_YELLOW);
            String currentPassword = scanner.nextLine();
    
            if (user.getPassword().equals(currentPassword)) {
                System.out.println(ANSI_CYAN + "Username: " + ANSI_YELLOW + user.getUsername());
                System.out.println(ANSI_CYAN +"Name: " + user.getName());
                System.out.println(ANSI_CYAN +"Surname: " + ANSI_YELLOW + user.getSurname());
                System.out.println(ANSI_CYAN +"Gender: " +ANSI_YELLOW +  user.getGender());
                System.out.println(ANSI_CYAN +"Age: " + ANSI_YELLOW + user.getAge());
                System.out.println(ANSI_CYAN +"Address: " + ANSI_YELLOW + user.getAddress());
                List<Vehicle> rentedVehicles = user.getRentedVehicles();
                System.out.print(ANSI_CYAN +"Rented Cars : " + ANSI_RESET);
                for (Vehicle vehicle : rentedVehicles) {
                    System.out.print("\t" + vehicle.toString());
                }
            }
        } else {
            System.out.println(ANSI_RED + "User not found." + ANSI_RESET);
        }
    }

    public void editProfile(String ID) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your current password: ");
        String currentPassword = scanner.nextLine();
        
        User user = users.get(ID);

        if (user != null) {
            if (user.getPassword().equals(currentPassword)) {
                User newUserInfo = this.TakeUserInfo(ID);
                user.setPassword(newUserInfo.getPassword());
                user.setName(newUserInfo.getName());
                user.setSurname(newUserInfo.getSurname());
                user.setGender(newUserInfo.getGender());
                user.setAge(newUserInfo.getAge());
                user.setAddress(newUserInfo.getAddress());
                user.setUsername(newUserInfo.getUsername());
                System.out.println("Profile updated successfully.");
            } else {
                System.out.println("Invalid password. Profile update failed.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    

    public boolean signUp(String username, String password, String name, String surname, String gender, int age, String address, int adminpassword, int type, String id) {
        if (age < 18) {
            throw new IllegalArgumentException("User must be at least 18 years old.");
        }
        User newuser = new User(username, password, name, surname, gender, age, address, adminpassword, id);
        this.isadmin = newuser.isAdmin();
        if (type == 1 && !this.isAdmin()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(ANSI_RED + "Admin password incorrect");
            System.out.print(ANSI_CYAN + "Do you want to continue as customer (yes/no) : " + ANSI_YELLOW + ANSI_RESET);
            String answer = scanner.nextLine();
            if (answer == "yes") {
                users.put(newuser.GetUserID(), newuser);
                scanner.close();
                return true;
            }else {
                scanner.close();
                return false;
            }
        }else {
            users.put(newuser.GetUserID(), newuser);
        }
        return true;
    }

    public void listSedans() {
        System.out.println(ANSI_PURPLE + "Sedans:");
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getType() == VehicleType.SEDAN) {
                System.out.println(ANSI_GREEN + vehicle);
            }
        }
    }

    public void listSUVs() {
        System.out.println(ANSI_PURPLE + "SUVs:");
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getType() == VehicleType.SUV) {
                System.out.println(ANSI_CYAN + vehicle + ANSI_RESET);
            }
        }
    }

    public void listTrucks() {
        System.out.println(ANSI_PURPLE + "Trucks:");
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getType() == VehicleType.TRUCK) {
                System.out.println(ANSI_YELLOW + vehicle+ ANSI_RESET);
            }
        }
    }

    public void listCarsByPriceRange(double minPrice, double maxPrice) {
        System.out.println("Cars within price range $" + minPrice + " - $" + maxPrice + ":");
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getRentPrice() >= minPrice && vehicle.getRentPrice() <= maxPrice) {
                System.out.println(ANSI_GREEN + vehicle + ANSI_RESET);
            }
        }
    }

    public double rentCar(String uuid, String registrationNumber, int numberOfDays) {
        User user = users.get(uuid);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        Vehicle vehicleToRent = null;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getRegistrationNumber().equals(registrationNumber)) {
                vehicleToRent = vehicle;
                break;
            }
        }

        if (vehicleToRent == null) {
            throw new IllegalArgumentException("Vehicle not found.");
        }

        double bill = vehicleToRent.getRentPrice() * numberOfDays;
        this.vehicles.add(vehicleToRent);
        user.addRentedVehicle(vehicleToRent);
        return bill;
    }

    public void removeVehicle(String registrationNumber) {
        if (!this.isAdmin()) {
            System.out.println(ANSI_RED + "Access denied. Admin privileges required." + ANSI_RESET);
            return;
        }
        Iterator<Vehicle> iterator = vehicles.iterator();
        while (iterator.hasNext()) {
            Vehicle vehicle = iterator.next();
            if (vehicle.getRegistrationNumber().equals(registrationNumber)) {
                System.out.println(vehicle.toString() + ANSI_GREEN + "removed successfully." + ANSI_RESET);
                iterator.remove();
                return;
            }
        }
        System.out.println(ANSI_RED + "Vehicle not found." + ANSI_RESET);
    }

    public void listAllRentedVehicles() {
        if (!this.isAdmin()) {
            System.out.println(ANSI_RED + "Access denied. Admin privileges required." + ANSI_RESET);
            return;
        }
    
        for (User user : users.values()) {
            System.out.println(ANSI_CYAN + "User: " + user.getName() + " " + user.getSurname() + ANSI_RESET);
            List<Vehicle> rentedVehicles = user.getRentedVehicles();
            if (rentedVehicles.isEmpty()) {
                System.out.println(ANSI_RED + "No rented vehicles for this user." + ANSI_RESET);
            } else {
                for (Vehicle vehicle : rentedVehicles) {
                    System.out.println(ANSI_GREEN + vehicle.toString() + "\n");
                }
            }
        }
    }
    
    public void listAllUsers() {
        if (!this.isAdmin()) {
            System.out.println(ANSI_RED + "Access denied. Admin privileges required." + ANSI_RESET);
            return;
        }
        for (User user : users.values()) {
            System.out.println(ANSI_YELLOW + user.Printuser() + "\n" + ANSI_RESET);
        }
    }

    public void addVehicle(Vehicle vehicle) {
        if (!this.isAdmin()) {
            System.out.println(ANSI_RED + "Access denied. Admin privileges required." + ANSI_RESET);
            return;
        }
        vehicles.add(vehicle);
    }

    public void addUser(User user) {
        if (!this.isAdmin()) {
            System.out.println(ANSI_RED + "Access denied. Admin privileges required." + ANSI_RESET);
            return;
        } else {
            users.put(user.GetUserID(), user);
            System.out.println(ANSI_GREEN + "User Successfully Added" + ANSI_RESET);
        }
    }

    public void removeUser(String ID) {
    
        if (!this.isAdmin()) {
            System.out.println(ANSI_RED + "Access denied. Admin privileges required." + ANSI_RESET);
            return;
        }
    
        User userToRemove = null;
    
        for (User user : users.values()) {
            if (user.GetUserID().equals(ID)) {
                userToRemove = user;
                break;
            }
        }
    
        if (userToRemove == null) {
            System.out.println(ANSI_RED + "User with ID " + ID + " not found." + ANSI_RESET);
            return;
        }
    
        users.remove(ID);
        System.out.println(ANSI_CYAN + "User " + userToRemove.getName() + " " + userToRemove.getSurname() + " removed successfully." + ANSI_RESET);
    }

    public void listUserInfo(String userID) {
    
        User user = users.get(userID);
        if (user != null) {
            System.out.println(ANSI_CYAN + "User Information:" + ANSI_RESET);
            System.out.println("Name: " + user.getName());
            System.out.println("Surname: " + user.getSurname());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Gender: " + user.getGender());
            System.out.println("Age: " + user.getAge());
            System.out.println("Address: " + user.getAddress());
        } else {
            System.out.println(ANSI_RED + "Can't find user with ID: " + userID + ANSI_RESET);
        }
    }
    
    
    public void listVehicleInfo(String registrationNumber) {
    
        if (!this.isAdmin()) {
            System.out.println(ANSI_RED + "Access denied. Admin privileges required." + ANSI_RESET);
            return;
        }
    
        Vehicle vehicle = null;
        for (Vehicle v : vehicles) {
            if (v.getRegistrationNumber().equals(registrationNumber)) {
                vehicle = v;
                break;
            }
        }
    
        if (vehicle != null) {
            System.out.println(ANSI_CYAN + "Vehicle Information:" + ANSI_RESET);
            System.out.println("Registration Number: " + vehicle.getRegistrationNumber());
            System.out.println("Make: " + vehicle.getMake());
            System.out.println("Model: " + vehicle.getModel());
            System.out.println("Type: " + vehicle.getType());
            System.out.println("Rent Price: $" + vehicle.getRentPrice());
        } else {
            System.out.println(ANSI_RED + "Can't find vehicle with registration number: " + registrationNumber + ANSI_RESET);
        }
    }
    

    public User TakeUserInfo(String ID) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(ANSI_YELLOW + "Name: " + ANSI_CYAN);
        String name = scanner.nextLine();
        System.out.print(ANSI_YELLOW + "Surname: " + ANSI_CYAN);
        String surname = scanner.nextLine();
        System.out.print(ANSI_YELLOW + "Username: " + ANSI_CYAN);
        String username = scanner.nextLine();
        System.out.print(ANSI_YELLOW + "Gender: " + ANSI_CYAN);
        String gender = scanner.nextLine();
        System.out.print(ANSI_YELLOW + "Age: " + ANSI_CYAN);
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print(ANSI_YELLOW + "Address: " + ANSI_CYAN);
        String address = scanner.nextLine();
        System.out.print(ANSI_YELLOW + "Password: " + ANSI_CYAN);
        String password = scanner.nextLine();
        return new User(username, password, name, surname, gender, age, address, age, ID);
    }
    
   
    public void PrintAllMethods() {
    
        System.out.println("\nAvailable Actions:");
        System.out.println(ANSI_YELLOW + "1. List all available sedans" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "2. List all available SUVs" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "3. List all available trucks" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "4. List cars by price range" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "5. Rent a car" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "6. Show profile information" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "7. Edit profile" + ANSI_RESET);
        
        if (this.isAdmin()) {
            System.out.println(ANSI_CYAN + "Admin Methods : " + ANSI_RESET);
            System.out.println(ANSI_CYAN + "8.  Add a Vehicle" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "9.  Remove a Vehicle" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "10. List all users" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "11. Add a User" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "12. Remove a User" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "13. List all rented Vehicles" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "14. List info of specific User" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "15. List info of specific Vehicle \n" + ANSI_RESET);
        }
        
        System.err.println(ANSI_YELLOW + "16. List all methods " + ANSI_RESET);
        System.out.println(ANSI_RED + "17. Exit" + ANSI_RESET);
    }
    
    
    
    public void CreateMonkVehicles() {
        vehicles.add(new Vehicle("ABC123", "Toyota", "Camry", VehicleType.SEDAN, 50.0));
        vehicles.add(new Vehicle("XYZ456", "Honda", "Accord", VehicleType.SEDAN, 60.0));
        vehicles.add(new Vehicle("DEF789", "Ford", "Explorer", VehicleType.SUV, 80.0));
        vehicles.add(new Vehicle("GHI101", "Chevrolet", "Cruze", VehicleType.SEDAN, 55.0));
        vehicles.add(new Vehicle("JKL202", "Nissan", "Altima", VehicleType.SEDAN, 52.0));
        vehicles.add(new Vehicle("MNO303", "Toyota", "RAV4", VehicleType.SUV, 75.0));
        vehicles.add(new Vehicle("PQR404", "Hyundai", "Sonata", VehicleType.SEDAN, 58.0));
        vehicles.add(new Vehicle("STU505", "Jeep", "Wrangler", VehicleType.SUV, 90.0));
        vehicles.add(new Vehicle("VWX606", "Kia", "Sportage", VehicleType.SUV, 70.0));
        vehicles.add(new Vehicle("YZA707", "Subaru", "Outback", VehicleType.SUV, 85.0));
        vehicles.add(new Vehicle("BCB808", "Mazda", "CX-5", VehicleType.SUV, 78.0));
        vehicles.add(new Vehicle("CDE909", "Volkswagen", "Jetta", VehicleType.SEDAN, 57.0));
        vehicles.add(new Vehicle("FGF010", "Ford", "F-150", VehicleType.TRUCK, 100.0));
        vehicles.add(new Vehicle("HIH111", "Toyota", "Tacoma", VehicleType.TRUCK, 95.0));
        vehicles.add(new Vehicle("IJI212", "Chevrolet", "Silverado", VehicleType.TRUCK, 105.0));
        vehicles.add(new Vehicle("JKJ313", "Ram", "1500", VehicleType.TRUCK, 98.0));
        vehicles.add(new Vehicle("KAK414", "GMC", "Sierra", VehicleType.TRUCK, 102.0));
        vehicles.add(new Vehicle("LML515", "Ford", "Escape", VehicleType.SUV, 73.0));
        vehicles.add(new Vehicle("MNM616", "Honda", "Pilot", VehicleType.SUV, 82.0));
        vehicles.add(new Vehicle("NON717", "Nissan", "Rogue", VehicleType.SUV, 68.0));
    }   

    public void CreateMonkUsers() {
        signUp("user1", "password1", "John", "Doe", "Male", 25, "Address 1", 0000, 2, "");
        signUp("user2", "password2", "Alice", "Smith", "Female", 30, "Address 2", 0000, 2, "");
        signUp("user3", "password3", "Bob", "Johnson", "Male", 35, "Address 3", 0000, 2, "");
        signUp("user4", "password4", "Emily", "Brown", "Female", 40, "Address 4", 0000, 2, "");
        signUp("user5", "password5", "Michael", "Davis", "Male", 45, "Address 5", 0000, 2, "");
        signUp("user6", "password6", "Jessica", "Wilson", "Female", 50, "Address 6", 0000, 2, "");
        signUp("user7", "password7", "David", "Martinez", "Male", 55, "Address 7", 0000, 2, "");
        signUp("user8", "password8", "Sarah", "Lopez", "Female", 60, "Address 8", 0000, 2, "");
        signUp("user9", "password9", "James", "Gonzalez", "Male", 65, "Address 9", 0000, 2, "");
        signUp("user10", "password10", "Emma", "Rodriguez", "Female", 70, "Address 10", 0000, 2, "");
    }
}