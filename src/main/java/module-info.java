module com.example.ada_locatecar {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.h2database;


    opens com.example.ada_locatecar to javafx.fxml;
    opens com.example.ada_locatecar.H2DataBase to javafx.fxml;
    opens com.example.ada_locatecar.Controllers to javafx.fxml;
    opens com.example.ada_locatecar.Entities.Abstracts to javafx.base;
    opens com.example.ada_locatecar.Entities to javafx.base;


    exports com.example.ada_locatecar;
    exports com.example.ada_locatecar.H2DataBase;
    exports com.example.ada_locatecar.Controllers;
    exports com.example.ada_locatecar.Entities.Abstracts;
    exports com.example.ada_locatecar.Entities;
}