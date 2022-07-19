package net.guardiandev.terrabuild.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import net.guardiandev.terrabuild.TerrabuildApplication;
import net.guardiandev.terrabuild.backend.api.Item;
import net.guardiandev.terrabuild.backend.api.Mod;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Terrabuild {
    @Getter
    private static Terrabuild INSTANCE;
    @FXML public Text versionText;

    // Buttons
    @FXML public MFXButton infoBtn;
    @FXML public MFXButton modInfoBtn;
    @FXML public MFXButton itemsBtn;

    // Panes
    @FXML public VBox infoPane;
    @FXML public AnchorPane modInfoPane;
    @FXML public AnchorPane itemsPane;

    private Mod loadedMod;
    private Panes currentPane = Panes.Info;

    // START MOD INFO
    @FXML public Label modInfoLabel;

    @FXML public TextField name;
    @FXML public TextField displayName;
    @FXML public TextField author;
    @FXML public TextField version;
    @FXML public TextArea description;
    // END MOD INFO

    // START ITEMS
    @FXML public MFXListView<HBox> itemsList;
    // END ITEMS

    public void initialize() {
        INSTANCE = this;
        versionText.setText(String.format("Version %s", TerrabuildApplication.Version));
    }

    public void refreshLoadedMod(boolean switchToModInfo) {
        loadedMod = TerrabuildApplication.modManager.getLoadedMod();

        if(loadedMod == null) {
            hideModButtons();
            switchTo(Panes.Info);
            return;
        }

        showModButtons();
        if(switchToModInfo) switchTo(Panes.ModInfo);
        else switchTo(currentPane);
    }

    public void switchTo(Panes pane) {
        switch(pane) {
            case Info:
                hideCurrentPane();
                infoPane.setVisible(true);
                currentPane = Panes.Info;
                break;
            case ModInfo:
                hideCurrentPane();
                onLoadModInfo();
                modInfoPane.setVisible(true);
                currentPane = Panes.ModInfo;
                break;
            case Items:
                hideCurrentPane();
                onLoadItems();
                itemsPane.setVisible(true);
                currentPane = Panes.Items;
                break;
        }
    }

    public void hideCurrentPane() {
        switch(currentPane) {
            case Info:
                infoPane.setVisible(false);
                break;
            case ModInfo:
                modInfoPane.setVisible(false);
            case Items:
                itemsPane.setVisible(false);
                break;
        }
    }

    // START MOD INFO
    public void onLoadModInfo() {
        modInfoLabel.setText(loadedMod.getDisplayName());
        name.setText(loadedMod.getName());
        displayName.setText(loadedMod.getDisplayName());
        author.setText(loadedMod.getAuthor());
        version.setText(loadedMod.getVersion());
        description.setText(loadedMod.getDescription());
    }

    public void onModInfoSaveReleased() {
        String sName = name.getText().trim();
        String sDisplayName = displayName.getText();
        String sAuthor = author.getText().trim();
        String sVersion = version.getText().trim();
        String sDescription = description.getText().trim();

        if(!sName.isEmpty()) loadedMod.setName(sName);
        if(!sDisplayName.isEmpty()) loadedMod.setDisplayName(sDisplayName);
        if(!sAuthor.isEmpty()) loadedMod.setAuthor(sAuthor);
        if(!sVersion.isEmpty()) loadedMod.setVersion(sVersion);
        if(!sDescription.isEmpty()) loadedMod.setDescription(sDescription);

        refreshLoadedMod(false);
        TerrabuildApplication.modManager.saveLoadedMod();
    }
    // END MOD INFO

    // START ITEMS
    public void onLoadItems() {
        itemsList.getItems().clear();
        itemsList.setCellFactory((item) -> {
            MFXListCell<HBox> cell = new MFXListCell<>(itemsList, item);
            cell.setPrefHeight(80);
            return cell;
        });
        for(Item item : loadedMod.getContent().getItems()) {
            HBox node = new HBox();
            node.setAlignment(Pos.CENTER_LEFT);
            node.setSpacing(10);
            ImageView spriteView = new ImageView(SwingFXUtils.toFXImage(item.getSprite(), null));
            Label label = new Label(item.getName());
            label.getStyleClass().add("text");
            label.setFont(Font.font(15));
            MFXButton editButton = new MFXButton("Edit");
            editButton.getStyleClass().add("highlight-button");
            node.getChildren().addAll(spriteView, label, editButton);

            itemsList.getItems().add(node);
        }
    }

    public void createNewItemReleased() {
        Stages.createItem.show();
    }
    // END ITEMS

    public void showModButtons() {
        modInfoBtn.setVisible(true);
        itemsBtn.setVisible(true);
    }

    public void hideModButtons() {
        modInfoBtn.setVisible(false);
        itemsBtn.setVisible(false);
    }

     //           //
    // Listeners //
   //           //
    public void exportModReleased() {

    }

    public void manageModsReleased() {
        Stages.manageMods.show();
    }

    public void settingsReleased() {
        // Stages.settings.show();
    }

    public void infoReleased() {
        switchTo(Panes.Info);
    }

    public void modInfoReleased() {
        switchTo(Panes.ModInfo);
    }

    public void itemsReleased() {
        switchTo(Panes.Items);
    }

    enum Panes {
        Info,
        ModInfo,
        Items
    }
}
