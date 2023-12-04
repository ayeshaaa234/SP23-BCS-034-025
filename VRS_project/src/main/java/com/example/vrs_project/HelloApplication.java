package com.example.vrs_project;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import java.util.ArrayList;
import java.util.Optional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
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

import java.util.*;

import javafx.scene.control.ChoiceDialog;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.io.File;

public class HelloApplication extends Application {
    private File availableVehiclesFile;
    private File rentalVehicleFile;


private List<User> Userlist;
    private List<Vehicle> availableVehicles;

    private List<RentalRecord> rentalHistory;
    private Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        this.primaryStage = primaryStage;
        this.iniatilizeFiles();
        this.loadDataFromCSV();
        this.loadRentalDataFromCSV();
        //availableVehicles = new ArrayList<>();
        availableVehicles.add(new Car("Car", "Model1", "Car1", 50.0, 4));
        availableVehicles.add(new Van("Van", "Model2", "Van1", 70.0, 10));
        availableVehicles.add(new Coasters("Coasters", "Model3", "Coasters1", 80.0, 20));
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
          //AVAILABLE VEHICLES
    private void showAvailableVehicle() {

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


            List<Vehicle> filteredVehicles = availableVehicles.stream()
                    .filter(vehicle -> vehicle.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());


            ObservableList<Vehicle> observableList = FXCollections.observableArrayList(filteredVehicles);
            tableView.setItems(observableList);


            javafx.scene.control.Dialog<Void> dialog = new javafx.scene.control.Dialog<>();
            dialog.setTitle("Available Vehicles");
            dialog.setHeaderText("List of Available Vehicles - " + type);


            dialog.getDialogPane().getButtonTypes().add(javafx.scene.control.ButtonType.OK);


            dialog.getDialogPane().setContent(tableView);


            dialog.showAndWait();
        });
    }

          //LOGOUT
    private void logout(Stage primaryStage) {

        showAlert("Logout successful.");

        //primaryStage.setScene(createLoginScene(primaryStage));
        Stage newPrimaryStage = new Stage();
        newPrimaryStage.setTitle("Vehicle Rental System - Login");
        newPrimaryStage.setScene(createLoginScene(newPrimaryStage));
        newPrimaryStage.show();
        // Close the current primaryStage
        if (primaryStage != null) {
            primaryStage.close();
        }
        //primaryStage.close();

    }

              //RETURN VEHICLE
        private void returnvehicle() {
        ChoiceDialog<String> typeDialog = new ChoiceDialog<>("Car", "Car", "Van", "Coasters");
        typeDialog.setTitle("Select Vehicle Type");
        typeDialog.setHeaderText("Choose the type of vehicle to return:");
        typeDialog.setContentText("Vehicle Type:");

        Optional<String> selectedType = typeDialog.showAndWait();

        selectedType.ifPresent(type -> {
            // Provide return information using a form
            GridPane formGrid = new GridPane();
            formGrid.setHgap(10);
            formGrid.setVgap(10);

            TextField modelField = new TextField();
            modelField.setPromptText("Model");
            TextField nameField = new TextField();
            nameField.setPromptText("Name");
            TextField priceField = new TextField();
            priceField.setPromptText("Price");


            TextField additionalField;
            if ("Car".equals(type)) {
                additionalField = new TextField();
                additionalField.setPromptText("Number of Doors");
                formGrid.addRow(3, new Label("Number of Doors:"), additionalField);
            } else if ("Van".equals(type)) {
                additionalField = new TextField();
                additionalField.setPromptText("Cargo Capacity");
                formGrid.addRow(3, new Label("Cargo Capacity:"), additionalField);
            } else if ("Coasters".equals(type)) {
                additionalField = new TextField();
                additionalField.setPromptText("Seating Capacity");
                formGrid.addRow(3, new Label("Seating Capacity:"), additionalField);
            } else {
                additionalField = null;
            }

            formGrid.addRow(0, new Label("Model:"), modelField);
            formGrid.addRow(1, new Label("Name:"), nameField);
            formGrid.addRow(2, new Label("Price:"), priceField);


            Dialog<String> formDialog = new Dialog<>();
            formDialog.setTitle("Enter Return Information");
            formDialog.getDialogPane().setContent(formGrid);

            ButtonType returnButton = new ButtonType("Return", ButtonBar.ButtonData.OK_DONE);
            formDialog.getDialogPane().getButtonTypes().addAll(returnButton, ButtonType.CANCEL);

            // Enable the Return button only when all fields are non-empty
            Node returnButtonNode = formDialog.getDialogPane().lookupButton(returnButton);
            ((Node) returnButtonNode).setDisable(true);
            modelField.textProperty().addListener((observable, oldValue, newValue) -> {
                returnButtonNode.setDisable(newValue.trim().isEmpty());
            });
            nameField.textProperty().addListener((observable, oldValue, newValue) -> {
                returnButtonNode.setDisable(newValue.trim().isEmpty());
            });
            priceField.textProperty().addListener((observable, oldValue, newValue) -> {
                returnButtonNode.setDisable(newValue.trim().isEmpty());
            });
            if (additionalField != null) {
                additionalField.textProperty().addListener((observable, oldValue, newValue) -> {
                    returnButtonNode.setDisable(newValue.trim().isEmpty());
                });
            }


            formDialog.setResultConverter(dialogButton -> {
                if (dialogButton == returnButton) {
                    return String.format("%s,%s,%s,%s,%s", type, modelField.getText(), nameField.getText(), priceField.getText(), additionalField.getText());
                }
                return null;
            });

            Optional<String> returnInfo = formDialog.showAndWait();

            returnInfo.ifPresent(info -> {
                // Process return and update data
                String[] parts = info.split(",");
                String returnType = parts[0].trim();
                String model = parts[1].trim();
                String name = parts[2].trim();
                double price = Double.parseDouble(parts[3].trim());


                String additionalInfo = parts[4].trim();

                showAlert("Vehicle returned successfully!");


                Vehicle returnedVehicle = createVehicleInstance(returnType, model, name, price, additionalInfo);
                if (returnedVehicle != null) {
                    availableVehicles.add(returnedVehicle);
                    showAlert("Vehicle added back to available vehicles.");


                    Iterator<RentalRecord> iterator = rentalHistory.iterator();
                    while (iterator.hasNext()) {
                        RentalRecord record = iterator.next();
                        if (record.getVehicle().getType().equalsIgnoreCase(returnedVehicle.getType())
                                && record.getVehicle().getModel().equalsIgnoreCase(returnedVehicle.getModel())
                                && record.getVehicle().getName().equalsIgnoreCase(returnedVehicle.getName())
                                && record.getVehicle().getPrice() == returnedVehicle.getPrice()
                                && Objects.equals(record.getVehicle().getDateReturned(), returnedVehicle.getDateReturned())) {
                            iterator.remove();
                            break;
                        }
                    }

                } else {
                    showAlert("Error creating vehicle instance. Please check the return information.");
                }
            });
        });
    }



//    private void updateDateReturnedInHistory(String returnType, String model, String name, double price) {
//        // Get the current date
//        LocalDate dateReturned = LocalDate.now();
//
//        // Update the dateReturned in the rental history directly
//        for (RentalRecord record : rentalHistory) {
//            Vehicle rentalVehicle = record.getVehicle();
//            if (rentalVehicle.getType().equalsIgnoreCase(returnType)
//                    && rentalVehicle.getModel().equalsIgnoreCase(model)
//                    && rentalVehicle.getName().equalsIgnoreCase(name)
//                    && rentalVehicle.getPrice() == price) {
//                record.setDateReturned(dateReturned);
//                break;  // Break after updating the first matching record
//            }
//        }
//    }


    private Vehicle createVehicleInstance(String type, String model, String name, double price, String additionalInfo) {
        switch (type.toLowerCase()) {
            case "car":
                int doors = Integer.parseInt(additionalInfo);
                return new Car(type, model, name, price, doors);
            case "van":
                int cargoCapacity = Integer.parseInt(additionalInfo);
                return new Van(type, model, name, price, cargoCapacity);
            case "coasters":
                int seatingCapacity = Integer.parseInt(additionalInfo);
                return new Coasters(type, model, name, price, seatingCapacity);

            default:
                return null;
        }



    }

              // RENTAL HISTORY
    private void viewRentalHistory() {

        ChoiceDialog<String> typeDialog = new ChoiceDialog<>("Car", "Car", "Van", "Coasters");
        typeDialog.setTitle("Select Vehicle Type");
        typeDialog.setHeaderText("Choose the type of vehicle for rental history:");
        typeDialog.setContentText("Vehicle Type:");

        Optional<String> selectedType = typeDialog.showAndWait();

        selectedType.ifPresent(type -> {

            List<RentalRecord> typeRentalHistory = new ArrayList<>();
            for (RentalRecord record : rentalHistory) {
                if (type.equalsIgnoreCase(record.getVehicleType())) {
                    typeRentalHistory.add(record);
                }
            }


            RentalHistoryWindow.displayRentalHistory(typeRentalHistory);
        });
    }

                //RENT VEHICLE
    private void rentvehicle() {

        ChoiceDialog<String> typeDialog = new ChoiceDialog<>("Car", "Car", "Van", "Coasters");
        typeDialog.setTitle("Select Vehicle Type");
        typeDialog.setHeaderText("Choose the type of vehicle you want to rent:");
        typeDialog.setContentText("Vehicle Type:");

        Optional<String> selectedType = typeDialog.showAndWait();

        selectedType.ifPresent(type -> {

            List<Vehicle> filteredVehicles = availableVehicles.stream()
                    .filter(vehicle -> type.equalsIgnoreCase(vehicle.getType()))
                    .collect(Collectors.toList());


            if (!filteredVehicles.isEmpty()) {

                ChoiceDialog<Vehicle> vehicleDialog = new ChoiceDialog<>(filteredVehicles.get(0), filteredVehicles);
                vehicleDialog.setTitle("Select Vehicle");
                vehicleDialog.setHeaderText("Choose a vehicle from the available list:");
                vehicleDialog.setContentText("Available Vehicles:");

                Optional<Vehicle> selectedVehicle = vehicleDialog.showAndWait();

                selectedVehicle.ifPresent(vehicle -> {

                    Dialog<String> formDialog = new Dialog<>();
                    formDialog.setTitle("Enter Personal Information");
                    formDialog.setHeaderText("Enter your personal information and payment method:");

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
                    ToggleGroup paymentMethodToggleGroup = new ToggleGroup();
                    RadioButton cashRadioButton = new RadioButton("Cash on Delivery");
                    RadioButton onlineRadioButton = new RadioButton("Online Payment");
                    cashRadioButton.setToggleGroup(paymentMethodToggleGroup);
                    onlineRadioButton.setToggleGroup(paymentMethodToggleGroup);


                    GridPane onlinePaymentGrid = new GridPane();
                    onlinePaymentGrid.setHgap(10);
                    onlinePaymentGrid.setVgap(10);

                    TextField debitCardNumberField = new TextField();
                    debitCardNumberField.setPromptText("Debit Card Number");
                    PasswordField securityCodeField = new PasswordField();
                    securityCodeField.setPromptText("Security Code");

                    onlinePaymentGrid.addRow(0, new Label("Debit Card Number:"), debitCardNumberField);
                    onlinePaymentGrid.addRow(1, new Label("Security Code:"), securityCodeField);


                    formGrid.addRow(0, new Label("Name:"), nameField);
                    formGrid.addRow(1, new Label("ID Card:"), idCardField);
                    formGrid.addRow(2, new Label("Address:"), addressField);
                    formGrid.addRow(3, new Label("Phone No:"), phoneField);
                    formGrid.addRow(4, new Label("Payment Method:"), cashRadioButton, onlineRadioButton);
                    formGrid.addRow(5, onlinePaymentGrid);

                    // Show the online payment fields only when online payment is selected
                    onlinePaymentGrid.visibleProperty().bind(onlineRadioButton.selectedProperty());

                    formDialog.getDialogPane().setContent(formGrid);

                    // Request focus on the name field by default
                    Platform.runLater(() -> nameField.requestFocus());

                    // Convert the result to a string when the Rent button is clicked
                    formDialog.setResultConverter(dialogButton -> {
                        if (dialogButton == rentButton) {
                            if (cashRadioButton.isSelected()) {
                                return String.format("Cash,%s,%s,%s,%s,%s,%s", nameField.getText(), idCardField.getText(), addressField.getText(), phoneField.getText(), "N/A", vehicle.getName());
                            } else if (onlineRadioButton.isSelected()) {
                                return String.format("Online,%s,%s,%s,%s,%s,%s,%s", nameField.getText(), idCardField.getText(), addressField.getText(), phoneField.getText(), "Online", vehicle.getName(), debitCardNumberField.getText());
                            }
                        }
                        return null;
                    });

                    Optional<String> personalInfo = formDialog.showAndWait();

                    personalInfo.ifPresent(info -> {

                        String[] parts = info.split(",");
                        String paymentMethod = parts[0].trim();
                        String name = parts[1].trim();
                      //  String idCard = parts[2].trim();
                     //   String address = parts[3].trim();
                      //  String phone = parts[4].trim();
                        String onlinePaymentInfo = parts[5].trim();
                        String vehicleName = parts[6].trim();

                        RentalRecord rentalRecord = new RentalRecord(vehicle.getType(), name, new Date(), null);
                        rentalRecord.setModel(vehicle.getModel());
                        rentalRecord.setName(vehicleName);
                        rentalHistory.add(rentalRecord);
                        this.addRentalRecordToFile(rentalRecord);

                        if ("Online".equals(paymentMethod)) {
                            // Additional steps for online payment
                            showPaymentSlip(name, vehicle.getType(), vehicle.getModel(), vehicleName, onlinePaymentInfo);
                        }

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

    private void addRentalRecordToFile(RentalRecord rentalRecord){
        try{
            FileWriter fileWriter = new FileWriter(rentalVehicleFile,true);
            fileWriter.write(rentalRecord.toCSV()+ "\n");
            fileWriter.close();
        }
        catch(IOException exception){
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }
    private void showPaymentSlip(String name, String vehicleType, String model, String vehicleName, String debitCardNumber) {
        StringBuilder paymentSlip = new StringBuilder("Payment Slip:\n");
        paymentSlip.append("Name: ").append(name).append("\n");
        paymentSlip.append("Vehicle Type: ").append(vehicleType).append("\n");
        paymentSlip.append("Model: ").append(model).append("\n");
        paymentSlip.append("Vehicle Name: ").append(vehicleName).append("\n");
        paymentSlip.append("Debit Card Number: ").append(debitCardNumber).append("\n");


        showAlert(paymentSlip.toString());
    }



    private void addvehicle() {

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


            Platform.runLater(() -> modelField.requestFocus());

            vehicleDialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {

                    addVehicleToAvailableList(type, modelField.getText(), nameField.getText(), priceField.getText(), additionalField.getText());

                    showAlert("Vehicle added successfully!");

                    // debugging
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

        }
        this.addVehicleToFile(availableVehicles.get(availableVehicles.size()-1));
    }
    private void addVehicleToFile(Vehicle vehicle){
       try{
           FileWriter fileWriter = new FileWriter(availableVehiclesFile,true);
           fileWriter.write(vehicle.toCSV()+ "\n");
           fileWriter.close();
       }
       catch(IOException exception){
           System.out.println(exception.getMessage());
           exception.printStackTrace();
        }
    }





    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

private void loadDataFromCSV() {
       try{
           availableVehicles=new ArrayList<>();
        Scanner sc= new Scanner(availableVehiclesFile);

        while(sc.hasNextLine()){
            String vehicleString= sc.nextLine();
            List<String> vehicleStringArrayList= Arrays.asList(vehicleString.split(","));
            Vehicle vehicle = null;
            switch (vehicleStringArrayList.get(0)){
                case "car":
                    vehicle= new Car(vehicleStringArrayList.get(0),vehicleStringArrayList.get(1),
                            vehicleStringArrayList.get(2),Double.parseDouble(vehicleStringArrayList.get(3)),
                            Integer.parseInt(vehicleStringArrayList.get(4)));
                    break;
                case "van":
                    vehicle = new Van(vehicleStringArrayList.get(0),vehicleStringArrayList.get(1),
                            vehicleStringArrayList.get(2),Double.parseDouble(vehicleStringArrayList.get(3)),
                            Integer.parseInt(vehicleStringArrayList.get(4)));
                    break;
                case "coasters":
                    vehicle = new Coasters(vehicleStringArrayList.get(0),vehicleStringArrayList.get(1),
                            vehicleStringArrayList.get(2),Double.parseDouble(vehicleStringArrayList.get(3)),
                            Integer.parseInt(vehicleStringArrayList.get(4)));
                    break;
            }
            availableVehicles.add(vehicle);
        }

       }
       catch(IOException exception){
           System.out.println(exception.getMessage());
           exception.printStackTrace();
       }
}
    private void loadRentalDataFromCSV() {
        try{
            rentalHistory=new ArrayList<>();
            Scanner sc= new Scanner(rentalVehicleFile);
            while(sc.hasNextLine()){
                String rentalvehicleString= sc.nextLine();
                List<String> rentalVehicleStringArraylist= Arrays.asList(rentalvehicleString.split(","));
                RentalRecord rentalRecord= null;
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                Date dateRented = dateFormat.parse(rentalVehicleStringArraylist.get(4));
                Date dateReturned = null;

                try {
                    if(rentalVehicleStringArraylist.size()>=5){
                    dateReturned = dateFormat.parse(rentalVehicleStringArraylist.get(5));
                    }
                }
                catch(ParseException exception){
                    System.out.println("Returned date was empty");
                }
                rentalRecord = new RentalRecord(rentalVehicleStringArraylist.get(0),rentalVehicleStringArraylist.get(1),
                        rentalVehicleStringArraylist.get(2),rentalVehicleStringArraylist.get(3),
                        dateRented,dateReturned);
                rentalHistory.add(rentalRecord);
            }

        }
        catch( Exception exception){
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }

    }
private void iniatilizeFiles(){
    try{
        availableVehiclesFile=new File(Constants.AVAILABLE_VEHICLE_FILENAME);
        rentalVehicleFile=new File(Constants.RENTAL_VEHICLE_FILENAME);
        if(!availableVehiclesFile.exists()) {
            availableVehiclesFile.createNewFile();
        }
        if(!rentalVehicleFile.exists()){
            rentalVehicleFile.createNewFile();
        }
    }

    catch(IOException exception){
        System.out.println(exception.getMessage());
        exception.printStackTrace();
    }
}






    public static void main(String[] args) {
        launch();

    }
}

