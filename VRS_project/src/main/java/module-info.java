module com.example.vrs_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.vrs_project to javafx.fxml;
    exports com.example.vrs_project;
}