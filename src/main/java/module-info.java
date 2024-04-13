module com.lebastudios.stexteditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.fxmisc.richtext;
    requires reactfx;

    opens com.lebastudios.stexteditor to javafx.fxml;
    exports com.lebastudios.stexteditor;

    exports com.lebastudios.stexteditor.iobjects;
    opens com.lebastudios.stexteditor.iobjects to javafx.fxml;

    exports com.lebastudios.stexteditor.applogic;
    opens com.lebastudios.stexteditor.applogic to javafx.fxml;
    
    opens com.lebastudios.stexteditor.applogic.txtformatter to com.google.gson, javafx.fxml;
    
    exports com.lebastudios.stexteditor.iobjects.nodes;
    opens com.lebastudios.stexteditor.iobjects.nodes to javafx.fxml;
    
    exports com.lebastudios.stexteditor.iobjects.managers;
    opens com.lebastudios.stexteditor.iobjects.managers to javafx.fxml;
    
    exports com.lebastudios.stexteditor.events;
    
    exports com.lebastudios.stexteditor.applogic.txtformatter;
    exports com.lebastudios.stexteditor.iobjects.fxextends;
    opens com.lebastudios.stexteditor.iobjects.fxextends to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers;
    opens com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.leftvbox;
    opens com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.leftvbox to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.rightvbox;
    opens com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.rightvbox to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.tabpane;
    opens com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.tabpane to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.treeview;
    opens com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.treeview to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.managers.nodemanagers.instanciablemanager;
    opens com.lebastudios.stexteditor.iobjects.managers.nodemanagers.instanciablemanager to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.managers.nodemanagers;
    opens com.lebastudios.stexteditor.iobjects.managers.nodemanagers to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.managers.objectmanagers;
    opens com.lebastudios.stexteditor.iobjects.managers.objectmanagers to javafx.fxml;
    
    exports com.lebastudios.stexteditor.applogic.config.proyect;
    opens com.lebastudios.stexteditor.applogic.config.proyect to com.google.gson, javafx.fxml;
    
    exports com.lebastudios.stexteditor.applogic.config.global;
    opens com.lebastudios.stexteditor.applogic.config.global to com.google.gson, javafx.fxml;
}