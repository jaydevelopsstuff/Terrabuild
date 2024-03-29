module net.guardiandev.terrabuild {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires org.apache.commons.lang3;
    requires org.slf4j;
    requires static lombok;
    requires VirtualizedFX;

    opens net.guardiandev.terrabuild to javafx.fxml;

    exports net.guardiandev.terrabuild;
    exports net.guardiandev.terrabuild.gui;
    exports net.guardiandev.terrabuild.backend;
    exports net.guardiandev.terrabuild.backend.api;
    exports net.guardiandev.terrabuild.backend.api.export;
    exports net.guardiandev.terrabuild.backend.api.content;
    exports net.guardiandev.terrabuild.backend.api.content.item;
    exports net.guardiandev.terrabuild.gui.item;
    exports net.guardiandev.terrabuild.gui.dialog;
    exports net.guardiandev.terrabuild.gui.mod;
}