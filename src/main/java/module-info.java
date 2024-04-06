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
    
    exports com.lebastudios.stexteditor.iobjects.imanagers;
    opens com.lebastudios.stexteditor.iobjects.imanagers to javafx.fxml;
    
    exports com.lebastudios.stexteditor.applogic.txtformatter;
    exports com.lebastudios.stexteditor.iobjects.fxextends;
    opens com.lebastudios.stexteditor.iobjects.fxextends to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers;
    opens com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.leftvbox;
    opens com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.leftvbox to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.rightvbox;
    opens com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.rightvbox to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.tabpane;
    opens com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.tabpane to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.treeview;
    opens com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.treeview to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.imanagers.instanciablemanager;
    opens com.lebastudios.stexteditor.iobjects.imanagers.instanciablemanager to javafx.fxml;
    exports com.lebastudios.stexteditor.iobjects.icontrollers;
    opens com.lebastudios.stexteditor.iobjects.icontrollers to javafx.fxml;
}