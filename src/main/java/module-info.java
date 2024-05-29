import com.lebastudios.sealcode.IPlugin;

module com.lebastudios.sealcode {
    uses IPlugin;
    
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.fxmisc.richtext;
    requires reactfx;

    exports com.lebastudios.sealcode;
    exports com.lebastudios.sealcode.config;
    exports com.lebastudios.sealcode.events;
    exports com.lebastudios.sealcode.frontend;
    exports com.lebastudios.sealcode.controllers;
    exports com.lebastudios.sealcode.fileobj;
    exports com.lebastudios.sealcode.completations;

    opens com.lebastudios.sealcode to javafx.fxml;
    opens com.lebastudios.sealcode.frontend to javafx.fxml;
    opens com.lebastudios.sealcode.frontend.stages to javafx.fxml;
    opens com.lebastudios.sealcode.controllers to javafx.fxml;
    opens com.lebastudios.sealcode.controllers.settingsPanels to javafx.fxml;

    opens com.lebastudios.sealcode.config to com.google.gson;
    opens com.lebastudios.sealcode.completations to com.google.gson;
    opens com.lebastudios.sealcode.fileobj to com.google.gson;
}