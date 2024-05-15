import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CarRentalSystem carRentalSystem = new CarRentalSystem();
        int adminpassword = 0;
        final String uuid = UUID.randomUUID().toString();

        // ANSI escape codes
        String ANSI_RESET = "\u001B[0m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RED = "\u001B[31m";

        System.out.println(ANSI_CYAN + "Welcome to the Car Rental System!");
        System.out.println("Please sign up to continue.");
        System.out.println("Who are you");
        System.out.println("1. Admin");
        System.out.println("2. Customer");
        System.out.print("TYPE : " + ANSI_YELLOW);
        int type = scanner.nextInt();
        scanner.nextLine();

        if (type == 1) {
            System.out.print(ANSI_CYAN + "Enter admin password : " + ANSI_YELLOW);
            adminpassword = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.print(ANSI_CYAN + "Enter your username: " + ANSI_YELLOW);
        String username = scanner.nextLine();
        System.out.print(ANSI_CYAN + "Enter your password: " + ANSI_YELLOW);
        String password = scanner.nextLine();
        System.out.print(ANSI_CYAN + "Enter your name: " + ANSI_YELLOW);
        String name = scanner.nextLine();
        System.out.print(ANSI_CYAN + "Enter your surname: " + ANSI_YELLOW);
        String surname = scanner.nextLine();
        System.out.print(ANSI_CYAN + "Enter your gender: " + ANSI_YELLOW);
        String gender = scanner.nextLine();
        System.out.print(ANSI_CYAN + "Enter your age: " + ANSI_YELLOW);
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print(ANSI_CYAN + "Enter your address: " + ANSI_YELLOW);
        String address = scanner.nextLine();

        try {
            boolean result = carRentalSystem.signUp(username, password, name, surname, gender, age, address, adminpassword, type, uuid);
            if (result) {
                System.out.println(ANSI_GREEN + "Sign up successful!" + ANSI_RESET);
            } else {
                return;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(ANSI_RED + "Sign up failed: " + e.getMessage() + ANSI_RESET);
            return;
        }

        carRentalSystem.PrintAllMethods();

        // Handle user actions
        while (true) {
            System.out.print("\u001B[35m" + "\nEnter your choice: " + ANSI_RESET);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    carRentalSystem.listSedans();
                    break;
                case 2:
                    carRentalSystem.listSUVs();
                    break;
                case 3:
                    carRentalSystem.listTrucks();
                    break;
                case 4:
                    System.out.print(ANSI_RED + "Enter minimum price: ");
                    double minPrice = scanner.nextDouble();
                    System.out.print(ANSI_GREEN + "Enter maximum price: " + ANSI_RESET);
                    double maxPrice = scanner.nextDouble();
                    carRentalSystem.listCarsByPriceRange(minPrice, maxPrice);
                    break;
                case 5:
                    System.out.print(ANSI_CYAN + "Enter the registration number of the vehicle you want to rent: " + ANSI_YELLOW);
                    String registrationNumber = scanner.nextLine();
                    System.out.print(ANSI_CYAN +"Enter the number of days you want to rent: "  + ANSI_YELLOW);
                    int numberOfDays = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    try {
                        double bill = carRentalSystem.rentCar(uuid, registrationNumber, numberOfDays);
                        System.out.println(ANSI_CYAN + "Total bill: $" + ANSI_YELLOW + bill);
                    } catch (IllegalArgumentException e) {
                        System.out.println(ANSI_RED + "Renting failed: " + e.getMessage() + ANSI_RESET);
                    }
                    break;
                case 6:
                    carRentalSystem.showProfileInfo(uuid);
                    break;
                case 7:
                    System.out.println("Edit Profile : ");
                    carRentalSystem.editProfile(uuid);
                    break;
                case 8:
                    System.out.println(ANSI_CYAN + "Enter new vehicle information:");
                    System.out.print("Make: " + ANSI_YELLOW);
                    String make = scanner.nextLine();
                    System.out.print(ANSI_CYAN + "Model: " + ANSI_YELLOW);
                    String model = scanner.nextLine();
                    System.out.println(ANSI_CYAN + "Type: ");
                    System.out.println("1. SEDAN");
                    System.out.println("2. SUV");
                    System.out.println("3. TRUCK");
                    System.out.print("Enter option: " + ANSI_YELLOW);
                    int option = scanner.nextInt();
                    VehicleType typ = null;
                    switch (option) {
                        case 1:
                            typ = VehicleType.SEDAN;
                            break;
                        case 2:
                            typ = VehicleType.SUV;
                            break;
                        case 3:
                            typ = VehicleType.TRUCK;
                            break;
                        default:
                            System.out.println(ANSI_RED + "Invalid option. Vehicle adding failed." + ANSI_RESET);
                            return;
                    }
                    System.out.print(ANSI_CYAN + "Price: " + ANSI_YELLOW);
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print(ANSI_CYAN + "ID: " + ANSI_YELLOW);
                    String id = scanner.nextLine();
            
                    if (typ != null && !make.isEmpty() && !model.isEmpty() && price > 0 && !id.isEmpty()) {
                        carRentalSystem.addVehicle(new Vehicle(id, make, model, typ, price));
                        System.out.println(ANSI_GREEN + "Vehicle added successfully." + ANSI_RESET);
                    } else {
                        System.out.println(ANSI_RED + "Vehicle adding failed. Please provide valid information." + ANSI_RESET);
                    }

                    break;
                case 9: 
                    System.out.println(ANSI_CYAN + "Enter Registration number : " + ANSI_YELLOW);
                    String registrationnumber = scanner.nextLine();
                    carRentalSystem.removeVehicle(registrationnumber);
                    break;
                case 10: 
                    System.out.println(ANSI_GREEN + "All Users ...");
                    carRentalSystem.listAllUsers();
                    break;
                case 11:
                    System.out.println(ANSI_CYAN + "Enter new user information:" + ANSI_RESET);
                    carRentalSystem.addUser(carRentalSystem.TakeUserInfo(""));
                    break;
                case 12 :
                    System.out.print(ANSI_CYAN + "Enter User ID : " + ANSI_YELLOW);
                    String ID = scanner.nextLine();
                    carRentalSystem.removeUser(ID);
                    break;
                case 13 :
                    System.out.println(ANSI_GREEN + "Rented Vehicles : " + ANSI_RESET);
                    carRentalSystem.listAllRentedVehicles();
                    break;
                case 14 :
                    System.out.print(ANSI_CYAN + "Enter User ID : " + ANSI_YELLOW);
                    String userid = scanner.nextLine();
                    carRentalSystem.listUserInfo(userid);
                    break;
                case 15:
                    System.out.print(ANSI_CYAN + "Enter vehicle registration ID : " + ANSI_YELLOW);
                    String registrationID = scanner.nextLine();
                    carRentalSystem.listVehicleInfo(registrationID);
                    break;
                case 16 :
                    carRentalSystem.PrintAllMethods();
                    break;
                case 17:
                    System.out.println(ANSI_RED + "Exiting..." + ANSI_RESET);
                    return;
                default:
                    System.out.println(ANSI_RED + "Invalid choice! Please enter a valid option." + ANSI_RESET);
            }
            
        }

    }

}
