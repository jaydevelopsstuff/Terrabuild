package net.guardiandev.terrabuild.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.guardiandev.terrabuild.TerrabuildApplication;

import java.io.IOException;

public class Stages {
    public static final Stage terrabuild;
    public static final Stage manageMods;
    public static final Stage createMod;
    public static final Stage deleteModDialog;
    public static final Stage createItem;

    static {
        try {
            terrabuild = makeStage("/fxml/Terrabuild.fxml", "Terrabuild", true);
            manageMods = makeStage("/fxml/ManageMods.fxml", "Manage Mods", false);
            manageMods.initOwner(terrabuild);
            createMod = makeStage("/fxml/CreateMod.fxml", "Create Mod", false);
            createMod.initOwner(manageMods);
            createMod.initStyle(StageStyle.UNDECORATED);
            deleteModDialog = makeStage("/fxml/dialog/DeleteMod.fxml", "Delete Mod", false);
            deleteModDialog.initOwner(manageMods);
            deleteModDialog.initStyle(StageStyle.UNDECORATED);
            createItem = makeStage("/fxml/CreateItem.fxml", "Create Item", false);
            createItem.initOwner(terrabuild);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stage makeStage(String fxmlPath, String title, boolean resizable) throws IOException {
        Parent root = FXMLLoader.load(TerrabuildApplication.getInstance().getResource(fxmlPath));
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.getIcons().add(TerrabuildApplication.Icon);
        stage.setResizable(resizable);
        stage.setScene(new Scene(root));
        return stage;
    }
}
