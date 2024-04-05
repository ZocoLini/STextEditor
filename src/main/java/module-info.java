module com.lebastudios.stexteditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires com.google.gson;
    requires org.fxmisc.richtext;
    requires org.fxmisc.flowless;
    requires reactfx;

    opens com.lebastudios.stexteditor to javafx.fxml;
    exports com.lebastudios.stexteditor;

    exports com.lebastudios.stexteditor.interfacecontrollers;
    opens com.lebastudios.stexteditor.interfacecontrollers to javafx.fxml;

    exports com.lebastudios.stexteditor.app;
    opens com.lebastudios.stexteditor.app to javafx.fxml;

    exports com.lebastudios.stexteditor.app.config;
    opens com.lebastudios.stexteditor.app.config to com.google.gson, javafx.fxml;

    exports com.lebastudios.stexteditor.nodes.formateableText;
    opens com.lebastudios.stexteditor.nodes.formateableText to javafx.fxml;
    
    opens com.lebastudios.stexteditor.app.txtformatter to com.google.gson, javafx.fxml;
    exports com.lebastudios.stexteditor.interfacecontrollers.proyecttreeview;
    opens com.lebastudios.stexteditor.interfacecontrollers.proyecttreeview to javafx.fxml;
}