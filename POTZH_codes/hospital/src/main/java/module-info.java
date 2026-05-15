module com.zh.hospital {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.zh.hospital to javafx.fxml;
    exports com.zh.hospital;
}