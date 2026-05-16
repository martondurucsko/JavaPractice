module com.zh.restaurant {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.zh.restaurant to javafx.fxml;
    exports com.zh.restaurant;
}