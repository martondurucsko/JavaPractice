module com.sysmon.hajogui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sysmon.hajogui to javafx.fxml;
    exports com.sysmon.hajogui;
}