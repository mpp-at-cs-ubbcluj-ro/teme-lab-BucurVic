module ubb.scs.map.cerinta {
    requires javafx.controls;
    requires javafx.fxml;


    opens ubb.scs.map.cerinta1 to javafx.fxml;
    exports ubb.scs.map.cerinta1;
}