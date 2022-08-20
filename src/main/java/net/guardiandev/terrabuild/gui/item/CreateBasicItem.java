package net.guardiandev.terrabuild.gui.item;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import net.guardiandev.terrabuild.TerrabuildApplication;
import net.guardiandev.terrabuild.backend.api.content.item.Item;
import net.guardiandev.terrabuild.gui.Stages;
import net.guardiandev.terrabuild.gui.Terrabuild;
import net.guardiandev.terrabuild.utils.TerrariaUtil;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreateBasicItem {
    @FXML public ImageView spriteImage;
    @FXML public BufferedImage sprite;
    @FXML public Text spritesheetLabel;
    @FXML public TextField width;
    @FXML public TextField height;
    @FXML public TextField name;
    @FXML public TextField displayName;
    @FXML public TextField maxStack;
    @FXML public TextField platinum, gold, silver, copper;
    @FXML public MFXComboBox<String> rarityPicker;

    public void initialize() {
        rarityPicker.getItems().clear();
        for(Item.Rarity rarity : Item.Rarity.values()) {
            rarityPicker.getItems().add(rarity.name());
        }
        rarityPicker.selectItem(Item.Rarity.White.name());
    }

    public void chooseSpritesheetReleased() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Spritesheet");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG File", "*.png"));

        File file = chooser.showOpenDialog(Stages.createBasicItem);

        spritesheetLabel.setText(file.getName());
        try {
            sprite = ImageIO.read(file);
            spriteImage.setImage(SwingFXUtils.toFXImage(sprite, null));
        } catch(IOException e) {
            TerrabuildApplication.Logger.error("Failed to read image file (corrupted?).");
            throw new RuntimeException(e);
        }
    }

    public void createReleased() {
        String sWidth = width.getText().trim();
        String sHeight = height.getText().trim();
        String sName = name.getText().trim();
        String sDisplayName = displayName.getText().trim();
        String sMaxStack = maxStack.getText().trim();
        String sPlatinum = platinum.getText().trim();
        String sGold = gold.getText().trim();
        String sSilver = silver.getText().trim();
        String sCopper = copper.getText().trim();
        Item.Rarity rarity = Item.Rarity.fromName(rarityPicker.getSelectedItem());

        if(sWidth.isEmpty() || sHeight.isEmpty() || sName.isEmpty() || sDisplayName.isEmpty()) return;

        if(sPlatinum.isEmpty()) sPlatinum = "0";
        if(sGold.isEmpty()) sGold = "0";
        if(sSilver.isEmpty()) sSilver = "0";
        if(sCopper.isEmpty()) sCopper = "0";
        int iPlatinum = Integer.parseInt(sPlatinum);
        int iGold = Integer.parseInt(sGold);
        int iSilver = Integer.parseInt(sSilver);
        int iCopper = Integer.parseInt(sCopper);

        Item item = new Item();
        item.setSprite(sprite);
        item.setWidth(Integer.parseInt(sWidth));
        item.setHeight(Integer.parseInt(sHeight));
        item.setName(sName);
        item.setDisplayName(sDisplayName);
        item.setMaxStack(Integer.parseInt(sMaxStack));
        item.setValue(TerrariaUtil.toValue(iPlatinum, iGold, iSilver, iCopper));
        item.setRarity(rarity);

        TerrabuildApplication.modManager.getLoadedMod().getContent().addItem(item);
        Terrabuild.getINSTANCE().refreshLoadedMod(false);


        // Reset fields
        width.setText("");
        height.setText("");
        name.setText("");
        displayName.setText("");
        maxStack.setText("1");
        platinum.setText("0");
        gold.setText("0");
        silver.setText("0");
        copper.setText("1");
        rarityPicker.selectItem("White");

        Stages.createBasicItem.hide();
    }
}
