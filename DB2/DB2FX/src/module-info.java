module DB2FX
{
    requires DB2;
    requires javafx.fxml;
    requires javafx.controls;
    opens DB2FX to  javafx.fxml;
    exports DB2FX;
}