module com.sysmon._ndattemptcity {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sysmon._ndattemptcity to javafx.fxml;
    exports com.sysmon._ndattemptcity;
}