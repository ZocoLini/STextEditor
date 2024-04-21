module com.lebastudios.sealcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.fxmisc.richtext;
    requires reactfx;
    requires jdk.compiler;

    exports com.lebastudios.sealcode;
    opens com.lebastudios.sealcode to javafx.fxml;
    
    opens com.lebastudios.sealcode.frontend.fxextends to javafx.fxml;
    
    opens com.lebastudios.sealcode.frontend.fxextends.treeviews to javafx.fxml;
    
    opens com.lebastudios.sealcode.frontend.stages to javafx.fxml;

    opens com.lebastudios.sealcode.controllers to javafx.fxml;
    
    opens com.lebastudios.sealcode.controllers.settingsPanels to javafx.fxml;

    opens com.lebastudios.sealcode.applogic.txtformatter to com.google.gson;

    opens com.lebastudios.sealcode.applogic.config to com.google.gson;
    opens com.lebastudios.sealcode.applogic to com.google.gson;
    opens com.lebastudios.sealcode.applogic.completations to com.google.gson;
    opens com.lebastudios.sealcode.applogic.txtmod to com.google.gson;
}