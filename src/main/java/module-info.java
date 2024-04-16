module com.lebastudios.sealcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.fxmisc.richtext;
    requires reactfx;
    requires java.desktop;
    
    exports com.lebastudios.sealcode;
    opens com.lebastudios.sealcode to javafx.fxml;

    exports com.lebastudios.sealcode.applogic;
    
    opens com.lebastudios.sealcode.applogic.txtformatter to com.google.gson, javafx.fxml;
    
    exports com.lebastudios.sealcode.iobjects.fxextends;
    opens com.lebastudios.sealcode.iobjects.fxextends to javafx.fxml;

    exports com.lebastudios.sealcode.applogic.config;
    opens com.lebastudios.sealcode.applogic.config to com.google.gson, javafx.fxml;
    opens com.lebastudios.sealcode.applogic to javafx.fxml;
    exports com.lebastudios.sealcode.iobjects.fxextends.buttons;
    opens com.lebastudios.sealcode.iobjects.fxextends.buttons to javafx.fxml;
    exports com.lebastudios.sealcode.iobjects.stages.main;
    opens com.lebastudios.sealcode.iobjects.stages.main to javafx.fxml;
}