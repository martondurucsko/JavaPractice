module com.zh.mentoallomas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.zh.mentoallomas to javafx.fxml;
    exports com.zh.mentoallomas;
}