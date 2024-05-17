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
    opens com.lebastudios.sealcode.core.logic to com.google.gson;
    opens com.lebastudios.sealcode.global to com.google.gson;
    
    /* Custom */
    requires com.github.javaparser.core;
    requires bcrypt;
    requires mysql.connector.j;
    requires reactfx;
    
    opens com.lebastudios.sealcode.custom.controllers to javafx.fxml;
    opens com.lebastudios.sealcode.custom.controllers.settingsPanels to javafx.fxml;
    
    opens com.lebastudios.sealcode.custom.logic.styling to com.google.gson;
    opens com.lebastudios.sealcode.custom.logic to com.google.gson;
    opens com.lebastudios.sealcode.core.logic.defaultcompletations to com.google.gson;
}