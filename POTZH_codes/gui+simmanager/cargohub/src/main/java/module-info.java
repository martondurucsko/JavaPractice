module com.zh.cargohub {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.zh.cargohub to javafx.fxml;
    exports com.zh.cargohub;
}