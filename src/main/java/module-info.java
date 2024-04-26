module com.lebastudios.sealcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.fxmisc.richtext;
    requires reactfx;
    requires com.github.javaparser.core;

    exports com.lebastudios.sealcode;
    opens com.lebastudios.sealcode to javafx.fxml;

    opens com.lebastudios.sealcode.core.frontend.fxextends to javafx.fxml;
    opens com.lebastudios.sealcode.core.frontend.stages to javafx.fxml;
    opens com.lebastudios.sealcode.controllers to javafx.fxml;
    opens com.lebastudios.sealcode.controllers.settingsPanels to javafx.fxml;

    opens com.lebastudios.sealcode.logic.styling to com.google.gson;
    opens com.lebastudios.sealcode.logic.formatting to com.google.gson;
    opens com.lebastudios.sealcode.config to com.google.gson;
    opens com.lebastudios.sealcode.util to com.google.gson;
}