package net.guardiandev.terrabuild.gui;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;
import net.guardiandev.terrabuild.TerrabuildApplication;
import net.guardiandev.terrabuild.backend.api.content.item.Item;
import net.guardiandev.terrabuild.backend.api.content.Mod;
import net.guardiandev.terrabuild.backend.api.export.ModExporter;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Terrabuild {
    @Getter
    private static Terrabuild INSTANCE;

    private Timer statusTextTimer = new Timer();

    @FXML public Text versionText;

    // Buttons
    @FXML public MFXButton saveModBtn;
    @FXML public MFXButton exportModBtn;
    @FXML public MFXButton infoBtn;
    @FXML public MFXButton modInfoBtn;
    @FXML public MFXButton itemsBtn;

    @FXML public Text statusText;

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
            spriteView.setFitHeight(70);
            spriteView.setPreserveRatio(true);
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
        Stages.chooseItemType.show();
    }
    // END ITEMS

    public void showModButtons() {
        saveModBtn.setDisable(false);
        exportModBtn.setDisable(false);
        modInfoBtn.setVisible(true);
        itemsBtn.setVisible(true);
    }

    public void hideModButtons() {
        saveModBtn.setDisable(true);
        exportModBtn.setDisable(true);
        modInfoBtn.setVisible(false);
        itemsBtn.setVisible(false);
    }

     //           //
    // Listeners //
   //           //
    public void saveModReleased() {
        if(!TerrabuildApplication.modManager.saveLoadedMod()) {
            statusText.setText(String.format("Failed to save mod %s.", loadedMod.getName()));
            createStatusDeleteTask();
            return;
        }
        statusText.setText(String.format("Successfully saved %s", loadedMod.getName()));
        createStatusDeleteTask();
    }

    public void exportModReleased() {
        TerrabuildApplication.Logger.info(String.format("Exporting mod %s", loadedMod.getName()));
        try {
            ModExporter.export("./export", loadedMod);
            statusText.setText(String.format("Successfully exported %s.", loadedMod.getName()));
            createStatusDeleteTask();
        } catch(IOException e) {
            TerrabuildApplication.Logger.error("Export failed");
            e.printStackTrace();
            statusText.setText("Export failed.");
            createStatusDeleteTask();
        }
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




    public void createStatusDeleteTask() {
        statusTextTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                statusText.setText("");
                cancel();
            }
        }, 5000);
    }

    public void createStatusDeleteTask(int s) {
        statusTextTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                statusText.setText("");
                cancel();
            }
        }, 1000L * s);
    }

    enum Panes {
        Info,
        ModInfo,
        Items
    }
}
