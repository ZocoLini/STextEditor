module com.lebastudios.stexteditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.fxmisc.richtext;
    requires reactfx;
    
    exports com.lebastudios.stexteditor;
    opens com.lebastudios.stexteditor to javafx.fxml;
    
    opens com.lebastudios.stexteditor.applogic.txtformatter to com.google.gson, javafx.fxml;
    
    exports com.lebastudios.stexteditor.iobjects.fxextends;
    opens com.lebastudios.stexteditor.iobjects.fxextends to javafx.fxml;
    
    exports com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers;
    opens com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers to javafx.fxml;
    
    exports com.lebastudios.stexteditor.applogic.config.proyect;
    opens com.lebastudios.stexteditor.applogic.config.proyect to com.google.gson, javafx.fxml;
    
    exports com.lebastudios.stexteditor.applogic.config.global;
    opens com.lebastudios.stexteditor.applogic.config.global to com.google.gson, javafx.fxml;
}