module com.lebastudios.sealcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.fxmisc.richtext;

    /* Core */
    exports com.lebastudios.sealcode;
    
    opens com.lebastudios.sealcode to javafx.fxml;
    opens com.lebastudios.sealcode.core.frontend.fxextends to javafx.fxml;
    opens com.lebastudios.sealcode.core.frontend.stages to javafx.fxml;
    opens com.lebastudios.sealcode.core.controllers to javafx.fxml;
    opens com.lebastudios.sealcode.core.controllers.settingsPanels to javafx.fxml;

    opens com.lebastudios.sealcode.core.logic.config to com.google.gson;
    opens com.lebastudios.sealcode.core.logic.completations to com.google.gson;
    opens com.lebastudios.sealcode.core.logic.fileobj to com.google.gson;
    
    /* Custom */
    requires com.github.javaparser.core;
    requires bcrypt;
    requires mysql.connector.j;
    requires mongo.java.driver;
    requires reactfx;
    requires com.google.j2objc.annotations;

    opens com.lebastudios.sealcode.custom.controllers to javafx.fxml;
    opens com.lebastudios.sealcode.custom.controllers.settingsPanels to javafx.fxml;
    
    opens com.lebastudios.sealcode.custom.logic.styling to com.google.gson;
}