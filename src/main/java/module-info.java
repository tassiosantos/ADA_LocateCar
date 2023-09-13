module com.example.ada_locatecar {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ada_locatecar to javafx.fxml;
    exports com.example.ada_locatecar;
}