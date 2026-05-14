module com.sysmon.airport {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sysmon.airport to javafx.fxml;
    exports com.sysmon.airport;
}