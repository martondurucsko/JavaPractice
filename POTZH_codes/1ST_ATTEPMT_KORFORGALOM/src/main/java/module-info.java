module com.sysmon._st_attepmt_korforgalom {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sysmon._st_attepmt_korforgalom to javafx.fxml;
    exports com.sysmon._st_attepmt_korforgalom;
}