module com.lebastudios.ud07javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.lebastudios.stexteditor to javafx.fxml;
    exports com.lebastudios.stexteditor;
    
    exports com.lebastudios.stexteditor.controllers;
    opens com.lebastudios.stexteditor.controllers to javafx.fxml;
    
    exports com.lebastudios.stexteditor.app;
    opens com.lebastudios.stexteditor.app to javafx.fxml;
}