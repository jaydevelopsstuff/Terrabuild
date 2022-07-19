package net.guardiandev.terrabuild.gui;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import net.guardiandev.terrabuild.TerrabuildApplication;
import net.guardiandev.terrabuild.backend.api.Mod;

public class CreateMod {
    @FXML public TextField name;
    @FXML public TextField displayName;
    @FXML public TextField author;
    @FXML public TextField version;
    @FXML public TextArea description;

    public void initialize() {
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.contains(" ")) ((StringProperty)observable).setValue(oldValue);
        });

        version.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("^[\\.0-9]*$")) ((StringProperty)observable).setValue(oldValue);
        });
    }

    public void createModReleased() {
        String sName = name.getText().trim();
        String sDisplayName = displayName.getText();
        String sAuthor = author.getText().trim();
        String sVersion = version.getText().trim();
        String sDescription = description.getText().trim();

        if(sName.isEmpty() || sDisplayName.isEmpty() || sAuthor.isEmpty() || sVersion.isEmpty()) return;

        Mod mod = TerrabuildApplication.modManager.createNewMod();
        mod.setName(sName);
        mod.setDisplayName(sDisplayName);
        mod.setAuthor(sAuthor);
        mod.setVersion(sVersion);
        mod.setDescription(sDescription);
        TerrabuildApplication.modManager.saveLoadedMod();
        ManageMods.getINSTANCE().refresh();
        Stages.createMod.hide();
    }

    public void cancelReleased() {
        Stages.createMod.hide();
    }
}
