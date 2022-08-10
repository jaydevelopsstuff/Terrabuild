package net.guardiandev.terrabuild.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import lombok.Getter;
import net.guardiandev.terrabuild.TerrabuildApplication;
import net.guardiandev.terrabuild.backend.api.content.Mod;

import java.util.List;

public class ManageMods {
    @Getter
    private static ManageMods INSTANCE;

    @FXML public Pane pane;
    @FXML public MFXListView<Mod> modsList;
    @FXML public MFXButton deleteConfirmBtn;

    public void initialize() {
        INSTANCE = this;
        modsList.getSelectionModel().setAllowsMultipleSelection(false);
        refresh();
    }

    public void refresh() {
        modsList.getItems().clear();
        modsList.getItems().addAll(TerrabuildApplication.modManager.getSavedMods());
    }

    public void loadModReleased() {
        List<Mod> mods = modsList.getSelectionModel().getSelectedValues();
        if(mods.isEmpty()) return;
        TerrabuildApplication.Logger.info(String.format("Loading mod %s", mods.get(0).getName()));
        TerrabuildApplication.modManager.loadMod(mods.get(0).getName());
        Terrabuild.getINSTANCE().refreshLoadedMod(true);
        Stages.manageMods.hide();
    }

    public void createModReleased() {
        Stages.createMod.show();
    }

    public void deleteModReleased() {
        if(modsList.getSelectionModel().getSelectedValues().isEmpty()) return;
        Mod mod = modsList.getSelectionModel().getSelectedValues().get(0);
        Stages.deleteModDialog.show();
    }
}