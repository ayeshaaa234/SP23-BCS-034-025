package com.example.vrs_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.List;

public class RentalHistoryWindow {
    public static void displayRentalHistory(List<RentalRecord> rentalHistory) {
        Stage stage = new Stage();
        stage.setTitle("Rental History");

        TableView<RentalHistoryItem> tableView = new TableView<>();
        TableColumn<RentalHistoryItem, String> rentedByColumn = new TableColumn<>("Rented By");
        TableColumn<RentalHistoryItem, String> vehicleTypeColumn = new TableColumn<>("Vehicle Type");
        TableColumn<RentalHistoryItem, String> modelColumn = new TableColumn<>("Model");
        TableColumn<RentalHistoryItem, String> nameColumn = new TableColumn<>("Name");
        TableColumn<RentalHistoryItem, String> dateRentedColumn = new TableColumn<>("Date Rented");
        TableColumn<RentalHistoryItem, String> dateReturnedColumn = new TableColumn<>("Date Returned");  // New column

        rentedByColumn.setCellValueFactory(new PropertyValueFactory<>("rentedBy"));
        vehicleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateRentedColumn.setCellValueFactory(new PropertyValueFactory<>("dateRented"));
        dateReturnedColumn.setCellValueFactory(new PropertyValueFactory<>("dateReturned"));  // Associate with the new attribute

        tableView.getColumns().addAll(rentedByColumn, vehicleTypeColumn, modelColumn, nameColumn, dateRentedColumn, dateReturnedColumn);  // Include the new column

        ObservableList<RentalHistoryItem> data = FXCollections.observableArrayList();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (RentalRecord record : rentalHistory) {
            RentalHistoryItem historyItem = new RentalHistoryItem(
                    record.getRentedBy(),
                    record.getVehicleType(),
                    record.getModel(),
                    record.getName(),
                    record.getDateRented(),
                    record.getDateReturned()
            );
            data.add(historyItem);
        }

        tableView.setItems(data);

        Scene scene = new Scene(tableView, 800, 400);  // Adjusted width
        stage.setScene(scene);
        stage.show();
    }


//        public static void displayRentalHistory(List<RentalRecord> rentalHistory) {
//            Stage stage = new Stage();
//            stage.setTitle("Rental History");
//
//            TableView<RentalHistoryItem> tableView = new TableView<>();
//            TableColumn<RentalHistoryItem, String> rentedByColumn = new TableColumn<>("Rented By");
//            TableColumn<RentalHistoryItem, String> vehicleTypeColumn = new TableColumn<>("Vehicle Type");
//            TableColumn<RentalHistoryItem, String> modelColumn = new TableColumn<>("Model");
//            TableColumn<RentalHistoryItem, String> nameColumn = new TableColumn<>("Name");
//            TableColumn<RentalHistoryItem, String> dateRentedColumn = new TableColumn<>("Date Rented");
//
//            rentedByColumn.setCellValueFactory(new PropertyValueFactory<>("rentedBy"));
//            vehicleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
//            modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
//            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//            dateRentedColumn.setCellValueFactory(new PropertyValueFactory<>("dateRented"));
//
//            tableView.getColumns().addAll(rentedByColumn, vehicleTypeColumn, modelColumn, nameColumn, dateRentedColumn);
//
//            ObservableList<RentalHistoryItem> data = FXCollections.observableArrayList();
//
//            for (RentalRecord record : rentalHistory) {
//                RentalHistoryItem historyItem = new RentalHistoryItem(
//                        record.getRentedBy(),
//                        record.getVehicleType(),
//                        record.getModel(),
//                        record.getName(),
//                        record.getDateRented()
//                );
//                data.add(historyItem);
//            }
//
//            tableView.setItems(data);
//
//            Scene scene = new Scene(tableView, 600, 400);
//            stage.setScene(scene);
//            stage.show();
//        }
}