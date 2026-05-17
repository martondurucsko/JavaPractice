module com.zh.kikoto {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.zh.kikoto to javafx.fxml;
    exports com.zh.kikoto;
}