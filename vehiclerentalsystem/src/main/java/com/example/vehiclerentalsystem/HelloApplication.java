package com.example.vehiclerentalsystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.util.*;
import javafx.event.*;
import java.io.IOException;
import java.util.*;

import javafx.scene.control.ChoiceDialog;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class HelloApplication extends Application {

     private ArrayList<User> Userlist;
    private ArrayList<Vehicle> availableVehicles;
    private ArrayList<RentalRecord> rentalHistory;

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        availableVehicles = new ArrayList<>();
        availableVehicles.add(new Car("Car", "Model1", "Car1", 50.0, 4));
        availableVehicles.add(new Van("Van", "Model2", "Van1", 70.0, 10));
        availableVehicles.add(new Coasters("Coasters", "Model3", "Coasters1", 80.0, 20));
        rentalHistory=new ArrayList<>();
        primaryStage.setTitle("Vehicle Rental System - Login");
        primaryStage.setScene(createLoginScene(primaryStage));
        primaryStage.show();
    }


    private Scene createLoginScene(Stage primaryStage) {
        Userlist= new ArrayList<>();
        Userlist.add(new User("user1","pass1"));
        Userlist.add(new User("user2","pass2"));
        Userlist.add(new User("user3","pass3"));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        GridPane.setConstraints(usernameField, 1, 0);

        Label userLabel = new Label("Password:");
        GridPane.setConstraints(userLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        GridPane.setConstraints(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e -> {
            try {
                String username = usernameField.getText();
                String password = passwordField.getText();
                if (authenticateUser(passwordField.getText(),usernameField.getText()) )
                {
                    primaryStage.setScene(createMainScene());
                }

                else {
                    showAlert("Invalid username or password. Please try again.");
                }
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Error reading user data.");

                ex.printStackTrace();
            }
        });

        grid.getChildren().addAll(userLabel,usernameField,usernameLabel,passwordField, loginButton);
        return new Scene(grid, 300, 200);
    }
    private boolean authenticateUser(String password, String username) throws IOException, ClassNotFoundException {
//        return "username".equals(username) && "password".equals(password);
        for (User user:
             Userlist) {
            if (user.getPassword().equals(password) && user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }


    private Scene createMainScene() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Button returnvehicleButton = new Button("Return a vehicle");
        GridPane.setConstraints(returnvehicleButton, 0, 0);
        returnvehicleButton.setOnAction(e -> returnvehicle());

        Button rentVehicleButton = new Button("Rent a Vehicle");
        GridPane.setConstraints(rentVehicleButton, 0, 1);
        rentVehicleButton.setOnAction(e -> rentvehicle());

        Button addVehicleButton = new Button("Add a Vehicle");
        GridPane.setConstraints(addVehicleButton, 0, 2);
        addVehicleButton.setOnAction(e -> addvehicle());

        Button viewHistoryButton = new Button("View Rental History");
        GridPane.setConstraints(viewHistoryButton, 0, 3);
        viewHistoryButton.setOnAction(e -> viewRentalHistory());

        Button availableVehicleButton = new Button("View Available Vehicle");
        GridPane.setConstraints(availableVehicleButton, 0, 4);
        availableVehicleButton.setOnAction(e -> showAvailableVehicle());

        Button logoutButton = new Button("Logout");
        GridPane.setConstraints(logoutButton, 0, 5);
        Stage primaryStage = null;
        logoutButton.setOnAction(e -> logout(primaryStage)); // Call logout method

        grid.getChildren().addAll(returnvehicleButton, rentVehicleButton, addVehicleButton, viewHistoryButton, logoutButton, availableVehicleButton);
        return new Scene(grid, 300, 200);

    }

    private void showAvailableVehicle() {
        // Create a choice dialog for selecting vehicle type
        ChoiceDialog<String> typeDialog = new ChoiceDialog<>("Car", "Car", "Van", "Coasters");
        typeDialog.setTitle("Select Vehicle Type");
        typeDialog.setHeaderText("Choose the type of vehicle you want to view:");
        typeDialog.setContentText("Vehicle Type:");

        Optional<String> selectedType = typeDialog.showAndWait();

        selectedType.ifPresent(type -> {
            // Create a TableView to display available vehicles of the selected type
            TableView<Vehicle> tableView = new TableView<>();

            // Create TableColumn instances for common attributes
            TableColumn<Vehicle, String> typeColumn = new TableColumn<>("Type");
            TableColumn<Vehicle, String> modelColumn = new TableColumn<>("Model");
            TableColumn<Vehicle, String> nameColumn = new TableColumn<>("Name");
            TableColumn<Vehicle, Double> priceColumn = new TableColumn<>("Price");

            // Set cell value factories to map columns to attributes
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            // Add additional column based on the selected vehicle type
            TableColumn<Vehicle, ?> additionalColumn;
            if ("Car".equals(type)) {
                additionalColumn = new TableColumn<>("Number of Doors");
                ((TableColumn<Vehicle, String>) additionalColumn).setCellValueFactory(new PropertyValueFactory<>("numberOfDoors"));
            } else if ("Van".equals(type)) {
                additionalColumn = new TableColumn<>("Cargo Capacity");
                ((TableColumn<Vehicle, String>) additionalColumn).setCellValueFactory(new PropertyValueFactory<>("cargoCapacity"));
            } else if ("Coasters".equals(type)) {
                additionalColumn = new TableColumn<>("Seating Capacity");
                ((TableColumn<Vehicle, String>) additionalColumn).setCellValueFactory(new PropertyValueFactory<>("seatingCapacity"));
            } else {
                // Handle additional columns for other vehicle types
                additionalColumn = new TableColumn<>("Additional Parameter");
                // Set cell value factory based on the actual attribute name for other vehicle types
                ((TableColumn<Vehicle, String>) additionalColumn).setCellValueFactory(new PropertyValueFactory<>("additionalParameter"));
            }

            // Add columns to the TableView
            tableView.getColumns().addAll(typeColumn, modelColumn, nameColumn, priceColumn, additionalColumn);

            // Filter available vehicles based on the selected type
            List<Vehicle> filteredVehicles = availableVehicles.stream()
                    .filter(vehicle -> vehicle.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());

            // Populate the TableView with filtered available vehicles
            ObservableList<Vehicle> observableList = FXCollections.observableArrayList(filteredVehicles);
            tableView.setItems(observableList);

            // Create a new dialog for displaying the TableView
            javafx.scene.control.Dialog<Void> dialog = new javafx.scene.control.Dialog<>();
            dialog.setTitle("Available Vehicles");
            dialog.setHeaderText("List of Available Vehicles - " + type);

            // Set the button type (OK button)
            dialog.getDialogPane().getButtonTypes().add(javafx.scene.control.ButtonType.OK);

            // Set the TableView as the content of the dialog
            dialog.getDialogPane().setContent(tableView);

            // Show the dialog
            dialog.showAndWait();
        });
    }
//    private void showAvailableVehicle() {
//        StringBuilder message = new StringBuilder("Available Vehicles:\n");
//
//        for (Vehicle vehicle : availableVehicles) {
//            message.append(vehicle.toString()).append("\n");
//        }
//
//        showAlert(message.toString());
//    }



    private void logout(Stage primaryStage) {
//        try {
//            saveCarsToFile(FILE_NAME, availableCars);
//        } catch (IOException ex) {
//            showAlert("Error saving car data.");
//            ex.printStackTrace();
//        }
        showAlert("Logout successful.");
        //primaryStage.setScene(createLoginScene(primaryStage));
        Stage newPrimaryStage = new Stage();
        newPrimaryStage.setTitle("Vehicle Rental System - Login");
        newPrimaryStage.setScene(createLoginScene(newPrimaryStage));
        newPrimaryStage.show();
        // Close the current primaryStage
        primaryStage.close();

    }

    private void returnvehicle() {
        // Step 1: Choose vehicle type for return
        ChoiceDialog<String> typeDialog = new ChoiceDialog<>("Car", "Car", "Van", "Coasters");
        typeDialog.setTitle("Select Vehicle Type");
        typeDialog.setHeaderText("Choose the type of vehicle to return:");
        typeDialog.setContentText("Vehicle Type:");

        Optional<String> selectedType = typeDialog.showAndWait();

        selectedType.ifPresent(type -> {
            // Step 2: Provide return information using a form
            GridPane formGrid = new GridPane();
            formGrid.setHgap(10);
            formGrid.setVgap(10);

            TextField modelField = new TextField();
            modelField.setPromptText("Model");
            TextField nameField = new TextField();
            nameField.setPromptText("Name");
            // Add other fields as needed for the specific vehicle type

            formGrid.addRow(0, new Label("Model:"), modelField);
            formGrid.addRow(1, new Label("Name:"), nameField);
            // Add other rows for additional fields

            // Show the form dialog
            Dialog<String> formDialog = new Dialog<>();
            formDialog.setTitle("Enter Return Information");
            formDialog.getDialogPane().setContent(formGrid);

            ButtonType returnButton = new ButtonType("Return", ButtonBar.ButtonData.OK_DONE);
            formDialog.getDialogPane().getButtonTypes().addAll(returnButton, ButtonType.CANCEL);

            // Convert the result to a string when the Return button is clicked
            formDialog.setResultConverter(dialogButton -> {
                if (dialogButton == returnButton) {
                    return String.format("%s,%s,%s", type, modelField.getText(), nameField.getText());
                }
                return null;
            });

            Optional<String> returnInfo = formDialog.showAndWait();

            returnInfo.ifPresent(info -> {
                // Step 3: Process return and update data
                String[] parts = info.split(",");
                String returnType = parts[0].trim();
                String model = parts[1].trim();
                String name = parts[2].trim();

                // Locate the vehicle in rental history
                RentalRecord returnedRecord = null;
                for (RentalRecord record : rentalHistory) {
                    if (record.getVehicleType().equalsIgnoreCase(returnType)
                            && record.getModel().equalsIgnoreCase(model)
                            && record.getName().equalsIgnoreCase(name)) {
                        returnedRecord = record;
                        break;
                    }
                }

                if (returnedRecord != null) {
                    // Remove the vehicle from rental history
                    rentalHistory.remove(returnedRecord);

                    // Create a new available vehicle based on the returned information
                    Vehicle returnedVehicle = createVehicleInstance(returnType, model, name);

                    // Add the returned vehicle to available vehicles
                    if (returnedVehicle != null) {
                        availableVehicles.add(returnedVehicle);
                        showAlert("Vehicle returned successfully!");
                    } else {
                        showAlert("Error creating vehicle instance. Please check the return information.");
                    }
                } else {
                    showAlert("No matching vehicle found in rental history.");
                }
            });
        });
    }

    // Helper method to create a new vehicle instance based on type, model, and name
    private Vehicle createVehicleInstance(String type, String model, String name) {
        switch (type.toLowerCase()) {
            case "car":
                return new Car(type, model, name,0,4);
            case "van":
                return new Van(type, model, name,23,2300 /* add other parameters */);
            case "coasters":
                return new Coasters(type, model, name, 3000,40/* add other parameters */);
            // Add more cases for other vehicle types
            default:
                return null;
        }
    }


    private void viewRentalHistory() {
        // Step 1: Choose vehicle type for rental history
        ChoiceDialog<String> typeDialog = new ChoiceDialog<>("Car", "Car", "Van", "Coasters");
        typeDialog.setTitle("Select Vehicle Type");
        typeDialog.setHeaderText("Choose the type of vehicle for rental history:");
        typeDialog.setContentText("Vehicle Type:");

        Optional<String> selectedType = typeDialog.showAndWait();

        selectedType.ifPresent(type -> {
            // Step 2: Show rental history based on the selected type
            ArrayList<RentalRecord> typeRentalHistory = new ArrayList<>();
            for (RentalRecord record : rentalHistory) {
                if (type.equalsIgnoreCase(record.getVehicleType())) {
                    typeRentalHistory.add(record);
                }
            }

            // Step 3: Display rental history in a dialog
            StringBuilder historyMessage = new StringBuilder("Rental History for " + type + ":\n");
            for (RentalRecord record : typeRentalHistory) {
                historyMessage.append("Rented by: ").append(record.getRentedBy()).append("\tDate: ").append(record.getDateRented()).append("\n");
            }

            showAlert(historyMessage.toString());
        });
    }

    private void rentvehicle() {
        // Step 1: Choose vehicle type
        ChoiceDialog<String> typeDialog = new ChoiceDialog<>("Car", "Car", "Van", "Coasters");
        typeDialog.setTitle("Select Vehicle Type");
        typeDialog.setHeaderText("Choose the type of vehicle you want to rent:");
        typeDialog.setContentText("Vehicle Type:");

        Optional<String> selectedType = typeDialog.showAndWait();

        selectedType.ifPresent(type -> {
            // Filter available vehicles based on the selected type
            List<Vehicle> filteredVehicles = availableVehicles.stream()
                    .filter(vehicle -> type.equalsIgnoreCase(vehicle.getType()))
                    .collect(Collectors.toList());

            // Ensure there are available vehicles of the selected type
            if (!filteredVehicles.isEmpty()) {
                // Step 2: Choose a specific vehicle
                ChoiceDialog<Vehicle> vehicleDialog = new ChoiceDialog<>(filteredVehicles.get(0), filteredVehicles);
                vehicleDialog.setTitle("Select Vehicle");
                vehicleDialog.setHeaderText("Choose a vehicle from the available list:");
                vehicleDialog.setContentText("Available Vehicles:");

                Optional<Vehicle> selectedVehicle = vehicleDialog.showAndWait();

                selectedVehicle.ifPresent(vehicle -> {
                    // Step 3: Provide personal information using a form
                    Dialog<String> formDialog = new Dialog<>();
                    formDialog.setTitle("Enter Personal Information");
                    formDialog.setHeaderText("Enter your personal information:");

                    // Set the button types
                    ButtonType rentButton = new ButtonType("Rent", ButtonBar.ButtonData.OK_DONE);
                    formDialog.getDialogPane().getButtonTypes().addAll(rentButton, ButtonType.CANCEL);

                    // Create the grid pane and add text fields for personal information
                    GridPane formGrid = new GridPane();
                    formGrid.setHgap(10);
                    formGrid.setVgap(10);
                    formGrid.setPadding(new Insets(20, 150, 10, 10));

                    TextField nameField = new TextField();
                    nameField.setPromptText("Name");
                    TextField idCardField = new TextField();
                    idCardField.setPromptText("ID Card");
                    TextField addressField = new TextField();
                    addressField.setPromptText("Address");
                    TextField phoneField = new TextField();
                    phoneField.setPromptText("Phone No");
                    TextField paymentMethodField = new TextField();
                    paymentMethodField.setPromptText("Payment Method");

                    formGrid.addRow(0, new Label("Name:"), nameField);
                    formGrid.addRow(1, new Label("ID Card:"), idCardField);
                    formGrid.addRow(2, new Label("Address:"), addressField);
                    formGrid.addRow(3, new Label("Phone No:"), phoneField);
                    formGrid.addRow(4, new Label("Payment Method:"), paymentMethodField);

                    formDialog.getDialogPane().setContent(formGrid);

                    // Request focus on the name field by default
                    Platform.runLater(() -> nameField.requestFocus());

                    // Convert the result to a string when the Rent button is clicked
                    formDialog.setResultConverter(dialogButton -> {
                        if (dialogButton == rentButton) {
                            return String.format("%s,%s,%s,%s,%s,%s", nameField.getText(), idCardField.getText(), addressField.getText(), phoneField.getText(), paymentMethodField.getText(), vehicle.getName());
                        }
                        return null;
                    });

                    Optional<String> personalInfo = formDialog.showAndWait();

                    personalInfo.ifPresent(info -> {
                        // Parse the user and vehicle information
                        String[] parts = info.split(",");
                        String name = parts[0].trim();
                        String idCard = parts[1].trim();
                        String address = parts[2].trim();
                        String phone = parts[3].trim();
                        String paymentMethod = parts[4].trim();
                        String vehicleName = parts[5].trim();

                        RentalRecord rentalRecord = new RentalRecord(vehicleName, name, new Date());
                        rentalHistory.add(rentalRecord);

                        // Display success message with user's name
                        showAlert("Vehicle rented successfully!\n" +
                                "Vehicle Type: " + vehicle.getType() + "\n" +
                                "Price: " + vehicle.getPrice() + "\n" +
                                "Name: " + name);

                        availableVehicles.remove(vehicle);
                    });
                });
            } else {
                showAlert("No available vehicles of the selected type.");
            }
        });
    }


//    private void rentvehicle() {
//        // Step 1: Choose vehicle type
//        ChoiceDialog<String> typeDialog = new ChoiceDialog<>("Car", "Car", "Van", "Coasters");
//        typeDialog.setTitle("Select Vehicle Type");
//        typeDialog.setHeaderText("Choose the type of vehicle you want to rent:");
//        typeDialog.setContentText("Vehicle Type:");
//
//        Optional<String> selectedType = typeDialog.showAndWait();
//
//        selectedType.ifPresent(type -> {
//            // Filter available vehicles based on the selected type
//            ArrayList<Vehicle> filteredVehicles = new ArrayList<>();
//            for (Vehicle vehicle : availableVehicles) {
//                if (type.equalsIgnoreCase(vehicle.getType())) {
//                    filteredVehicles.add(vehicle);
//                }
//            }
//
//            // Ensure there are available vehicles of the selected type
//            if (!filteredVehicles.isEmpty()) {
//                // Step 2: Choose a specific vehicle
//                ChoiceDialog<Vehicle> vehicleDialog = new ChoiceDialog<>(filteredVehicles.get(0), filteredVehicles);
//                vehicleDialog.setTitle("Select Vehicle");
//                vehicleDialog.setHeaderText("Choose a vehicle from the available list:");
//                vehicleDialog.setContentText("Available Vehicles:");
//
//                Optional<Vehicle> selectedVehicle = vehicleDialog.showAndWait();
//
//                selectedVehicle.ifPresent(vehicle -> {
//                    // Step 3: Provide personal information using a form
//                    GridPane formGrid = new GridPane();
//                    formGrid.setHgap(10);
//                    formGrid.setVgap(10);
//
//                    TextField nameField = new TextField();
//                    nameField.setPromptText("Name");
//                    TextField idCardField = new TextField();
//                    idCardField.setPromptText("ID Card");
//                    TextField addressField = new TextField();
//                    addressField.setPromptText("Address");
//                    TextField phoneField = new TextField();
//                    phoneField.setPromptText("Phone No");
//                    TextField paymentMethodField = new TextField();
//                    paymentMethodField.setPromptText("Payment Method");
//
//                    formGrid.addRow(0, new Label("Name:"), nameField);
//                    formGrid.addRow(1, new Label("ID Card:"), idCardField);
//                    formGrid.addRow(2, new Label("Address:"), addressField);
//                    formGrid.addRow(3, new Label("Phone No:"), phoneField);
//                    formGrid.addRow(4, new Label("Payment Method:"), paymentMethodField);
//
//                    // Show the form dialog
//                    Dialog<String> formDialog = new Dialog<>();
//                    formDialog.setTitle("Enter Personal Information");
//                    formDialog.getDialogPane().setContent(formGrid);
//
//                    ButtonType rentButton = new ButtonType("Rent", ButtonBar.ButtonData.OK_DONE);
//                    formDialog.getDialogPane().getButtonTypes().addAll(rentButton, ButtonType.CANCEL);
//
//                    // Convert the result to a string when the Rent button is clicked
//                    formDialog.setResultConverter(dialogButton -> {
//                        if (dialogButton == rentButton) {
//                            return String.format("%s,%s,%s,%s,%s,%s", nameField.getText(), idCardField.getText(), addressField.getText(), phoneField.getText(), paymentMethodField.getText(), vehicle.getName());
//                        }
//                        return null;
//                    });
//
//                    Optional<String> personalInfo = formDialog.showAndWait();
//
//                    personalInfo.ifPresent(info -> {
//                        // Parse the user and vehicle information
//                        String[] parts = info.split(",");
//                        String name = parts[0].trim();
//                        String idCard = parts[1].trim();
//                        String address = parts[2].trim();
//                        String phone = parts[3].trim();
//                        String paymentMethod = parts[4].trim();
//                        String vehicleName = parts[5].trim();
//
//                        RentalRecord rentalRecord = new RentalRecord(vehicleName, name, new Date());
//                        rentalHistory.add(rentalRecord);
//
//                        // Display success message with user's name
//                        showAlert("Vehicle rented successfully!\n" +
//                                "Vehicle Type: " + vehicle.getType() + "\n" +
//                                "Price: " + vehicle.getPrice() + "\n" +
//                                "Name: " + name);
//
//                        availableVehicles.remove(vehicle);
//                    });
//                });
//            } else {
//                showAlert("No available vehicles of the selected type.");
//            }
//        });
//    }

    private void addvehicle() {
        // Create a choice dialog for selecting vehicle type
        ChoiceDialog<String> typeDialog = new ChoiceDialog<>("Car", "Car", "Van", "Coasters");
        typeDialog.setTitle("Select Vehicle Type");
        typeDialog.setHeaderText("Choose the type of vehicle you want to add:");
        typeDialog.setContentText("Vehicle Type:");

        Optional<String> selectedType = typeDialog.showAndWait();

        selectedType.ifPresent(type -> {
            // Create a dialog for entering vehicle details
            Dialog<Void> vehicleDialog = new Dialog<>();
            vehicleDialog.setTitle("Enter Vehicle Details");
            vehicleDialog.setHeaderText("Enter details for the selected vehicle type:");

            // Set the button types
            ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            vehicleDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

            // Create the grid pane and add text fields for vehicle details
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField modelField = new TextField();
            modelField.setPromptText("Model");
            TextField nameField = new TextField();
            nameField.setPromptText("Name");
            TextField priceField = new TextField();
            priceField.setPromptText("Price");

            // Additional fields based on vehicle type
            TextField additionalField = new TextField();
            if ("Car".equals(type)) {
                additionalField.setPromptText("Number of Doors");
                grid.add(new Label("Number of Doors:"), 0, 3);
                grid.add(additionalField, 1, 3);
            } else if ("Van".equals(type)) {
                additionalField.setPromptText("Cargo Capacity");
                grid.add(new Label("Cargo Capacity:"), 0, 3);
                grid.add(additionalField, 1, 3);
            } else if ("Coasters".equals(type)) {
                additionalField.setPromptText("Seating Capacity");
                grid.add(new Label("Seating Capacity:"), 0, 3);
                grid.add(additionalField, 1, 3);
            }

            grid.add(new Label("Model:"), 0, 0);
            grid.add(modelField, 1, 0);
            grid.add(new Label("Name:"), 0, 1);
            grid.add(nameField, 1, 1);
            grid.add(new Label("Price:"), 0, 2);
            grid.add(priceField, 1, 2);

            vehicleDialog.getDialogPane().setContent(grid);

            // Request focus on the model field by default
            Platform.runLater(() -> modelField.requestFocus());

            // Convert the result to a vehicle instance when the add button is clicked
            vehicleDialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    // Add the new vehicle directly to the available vehicles list
                    addVehicleToAvailableList(type, modelField.getText(), nameField.getText(), priceField.getText(), additionalField.getText());

                    // Show a success message
                    showAlert("Vehicle added successfully!");

                    // Print the available vehicles (for debugging)
                    System.out.println("Available Vehicles:");
                    for (Vehicle vehicle : availableVehicles) {
                        System.out.println(vehicle.toString());
                    }
                }
                return null;
            });

            vehicleDialog.showAndWait();
        });
    }

    // Helper method to add a new vehicle directly to the available vehicles list
    private void addVehicleToAvailableList(String type, String model, String name, String price, String additionalField) {
        switch (type.toLowerCase()) {
            case "car":
                availableVehicles.add(new Car(type, model, name, Double.parseDouble(price), Integer.parseInt(additionalField)));
                break;
            case "van":
                availableVehicles.add(new Van(type, model, name, Double.parseDouble(price), Integer.parseInt(additionalField)));
                break;
            case "coasters":
                availableVehicles.add(new Coasters(type, model, name, Double.parseDouble(price), Integer.parseInt(additionalField)));
                break;
            // Add more cases for other vehicle types
        }
    }


//    private void addvehicle() {
//        // Create a choice dialog for selecting vehicle type
//        ChoiceDialog<String> typeDialog = new ChoiceDialog<>("Car", "Car", "Van", "Coasters");
//        typeDialog.setTitle("Select Vehicle Type");
//        typeDialog.setHeaderText("Choose the type of vehicle you want to add:");
//        typeDialog.setContentText("Vehicle Type:");
//
//        Optional<String> selectedType = typeDialog.showAndWait();
//
//        selectedType.ifPresent(type -> {
//            // Create a dialog for entering vehicle details
//            Dialog<String> vehicleDialog = new Dialog<>();
//            vehicleDialog.setTitle("Enter Vehicle Details");
//            vehicleDialog.setHeaderText("Enter details for the selected vehicle type:");
//
//            // Set the button types
//            ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
//            vehicleDialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
//
//            // Create the grid pane and add text fields for vehicle details
//            GridPane grid = new GridPane();
//            grid.setHgap(10);
//            grid.setVgap(10);
//            grid.setPadding(new Insets(20, 150, 10, 10));
//
//            TextField modelField = new TextField();
//            modelField.setPromptText("Model");
//            TextField nameField = new TextField();
//            nameField.setPromptText("Name");
//            TextField priceField = new TextField();
//            priceField.setPromptText("Price");
//
//            // Additional fields based on vehicle type
//            if ("Car".equals(type)) {
//                TextField doorsField = new TextField();
//                doorsField.setPromptText("Number of Doors");
//                grid.add(new Label("Number of Doors:"), 0, 3);
//                grid.add(doorsField, 1, 3);
//            } else if ("Van".equals(type)) {
//                TextField cargoField = new TextField();
//                cargoField.setPromptText("Cargo Capacity");
//                grid.add(new Label("Cargo Capacity:"), 0, 3);
//                grid.add(cargoField, 1, 3);
//            } else if ("Coasters".equals(type)) {
//                TextField seatsField = new TextField();
//                seatsField.setPromptText("Seating Capacity");
//                grid.add(new Label("Seating Capacity:"), 0, 3);
//                grid.add(seatsField, 1, 3);
//            }
//
//            grid.add(new Label("Model:"), 0, 0);
//            grid.add(modelField, 1, 0);
//            grid.add(new Label("Name:"), 0, 1);
//            grid.add(nameField, 1, 1);
//            grid.add(new Label("Price:"), 0, 2);
//            grid.add(priceField, 1, 2);
//
//            vehicleDialog.getDialogPane().setContent(grid);
//
//            // Request focus on the model field by default
//            Platform.runLater(() -> modelField.requestFocus());
//
//            // Convert the result to a vehicle string when the add button is clicked
//            vehicleDialog.setResultConverter(dialogButton -> {
//                if (dialogButton == addButtonType) {
//                    return String.format("%s,%s,%s,%s", type, modelField.getText(), nameField.getText(), priceField.getText());
//                }
//                return null;
//            });
//
//            Optional<String> result = vehicleDialog.showAndWait();
//
//            result.ifPresent(vehicleInfo -> {
//                // Parse the vehicle information
//                String[] parts = vehicleInfo.split(",");
//                String vehicleType = parts[0];
//                String model = parts[1];
//                String name = parts[2];
//                double price = Double.parseDouble(parts[3]);
//
//                // Create the appropriate vehicle object based on the type
//                Vehicle newVehicle;
//                if ("Car".equals(vehicleType)) {
//                    int doors = Integer.parseInt(parts[4]);
//                    newVehicle = new Car(vehicleType, model, name, price, doors);
//                } else if ("Van".equals(vehicleType)) {
//                    int cargoCapacity = Integer.parseInt(parts[4]);
//                    newVehicle = new Van(vehicleType, model, name, price, cargoCapacity);
//                } else {
//                    int seatingCapacity = Integer.parseInt(parts[4]);
//                    newVehicle = new Coasters(vehicleType, model, name, price, seatingCapacity);
//                }
//
//                // Add the new vehicle to the available vehicles list
//                availableVehicles.add(newVehicle);
//
//                // Show a success message
//                showAlert("Vehicle added successfully!");
//
//                // Print the available vehicles (for debugging)
//                System.out.println("Available Vehicles:");
//                for (Vehicle vehicle : availableVehicles) {
//                    System.out.println(vehicle.toString());
//                }
//            });
//        });
//    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public static void main(String[] args) {
        launch();
    }
}

