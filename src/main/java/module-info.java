module com.lebastudios.sealcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.fxmisc.richtext;
    requires reactfx;

    exports com.lebastudios.sealcode;
    opens com.lebastudios.sealcode to javafx.fxml;
    
    opens com.lebastudios.sealcode.frontend.fxextends to javafx.fxml;
    
    opens com.lebastudios.sealcode.frontend.fxextends.buttons to javafx.fxml;
    
    opens com.lebastudios.sealcode.frontend.stages.main to javafx.fxml;
    opens com.lebastudios.sealcode.frontend.stages.settings to javafx.fxml;
    
    opens com.lebastudios.sealcode.applogic.txtformatter to com.google.gson;

    opens com.lebastudios.sealcode.applogic.config to com.google.gson;
}