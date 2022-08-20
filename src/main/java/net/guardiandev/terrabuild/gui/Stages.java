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
    public static final Stage chooseItemType;
    public static final Stage createBasicItem;
    public static final Stage createWeaponItem;
    //public static final Stage createAdvancedItem;

    static {
        try {
            terrabuild = makeStage("/fxml/Terrabuild.fxml", "Terrabuild", true);
            manageMods = makeStage("/fxml/mod/ManageMods.fxml", "Manage Mods", false);
            manageMods.initOwner(terrabuild);
            createMod = makeStage("/fxml/mod/CreateMod.fxml", "Create Mod", false);
            createMod.initOwner(manageMods);
            createMod.initStyle(StageStyle.UNDECORATED);
            deleteModDialog = makeStage("/fxml/dialog/DeleteMod.fxml", "Delete Mod", false);
            deleteModDialog.initOwner(manageMods);
            deleteModDialog.initStyle(StageStyle.UNDECORATED);
            chooseItemType = makeStage("/fxml/item/ChooseItemType.fxml", "Choose Item Type", false);
            chooseItemType.initOwner(terrabuild);
            createBasicItem = makeStage("/fxml/item/CreateItem.fxml", "Create Basic Item", false);
            createBasicItem.initOwner(terrabuild);
            createWeaponItem = makeStage("/fxml/item/CreateWeaponItem.fxml", "Create Weapon Item", false);
            createWeaponItem.initOwner(terrabuild);
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
