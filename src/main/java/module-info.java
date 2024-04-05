module com.lebastudios.stexteditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.fxmisc.richtext;
    requires org.fxmisc.flowless;
    requires reactfx;

    opens com.lebastudios.stexteditor to javafx.fxml;
    exports com.lebastudios.stexteditor;

    exports com.lebastudios.stexteditor.iobjects;
    opens com.lebastudios.stexteditor.iobjects to javafx.fxml;

    exports com.lebastudios.stexteditor.applogic;
    opens com.lebastudios.stexteditor.applogic to javafx.fxml;

    exports com.lebastudios.stexteditor.applogic.config;
    opens com.lebastudios.stexteditor.applogic.config to com.google.gson, javafx.fxml;
    
    opens com.lebastudios.stexteditor.applogic.txtformatter to com.google.gson, javafx.fxml;
    
    exports com.lebastudios.stexteditor.iobjects.nodes;
    opens com.lebastudios.stexteditor.iobjects.nodes to javafx.fxml;
    
    exports com.lebastudios.stexteditor.iobjects.controllers;
    opens com.lebastudios.stexteditor.iobjects.controllers to javafx.fxml;
    
    exports com.lebastudios.stexteditor.applogic.txtformatter;
    exports com.lebastudios.stexteditor.iobjects.fxextends;
    opens com.lebastudios.stexteditor.iobjects.fxextends to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.managers;
    opens com.lebastudios.stexteditor.iobjects.managers to javafx.fxml;
}