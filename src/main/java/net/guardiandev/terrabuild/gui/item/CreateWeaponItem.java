package net.guardiandev.terrabuild.gui.item;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import net.guardiandev.terrabuild.TerrabuildApplication;
import net.guardiandev.terrabuild.backend.api.content.item.Item;
import net.guardiandev.terrabuild.backend.api.content.item.MeleeItem;
import net.guardiandev.terrabuild.gui.Stages;
import net.guardiandev.terrabuild.gui.Terrabuild;
import net.guardiandev.terrabuild.utils.TerrariaUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreateWeaponItem {
    // Basic
    @FXML
    public ImageView spriteImage;
    @FXML public BufferedImage sprite;
    @FXML public Text spritesheetLabel;
    @FXML public TextField width;
    @FXML public TextField height;
    @FXML public TextField name;
    @FXML public TextField displayName;
    @FXML public TextField maxStack;
    @FXML public TextField platinum, gold, silver, copper;
    @FXML public MFXComboBox<String> rarityPicker;
    @FXML public MFXCheckbox expertExclusive;


    // Use
    @FXML public MFXComboBox<String> useStylePicker;
    @FXML public TextField useTime;
    @FXML public MFXCheckbox autoReuse;


    // Stats
    @FXML public TextField damage;
    @FXML public TextField knockback;
    @FXML public TextField critChance;

    public void initialize() {
        rarityPicker.getItems().clear();
        for(Item.Rarity rarity : Item.Rarity.values()) {
            rarityPicker.getItems().add(rarity.name());
        }
        rarityPicker.selectItem(Item.Rarity.White.name());

        useStylePicker.getItems().clear();
        for(Item.UseStyle useStyle : Item.UseStyle.values()) {
            useStylePicker.getItems().add(useStyle.name());
        }
        useStylePicker.selectItem(Item.UseStyle.Swinging.name());
    }

    public void chooseSpritesheetReleased() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Spritesheet");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG File", "*.png"));

        File file = chooser.showOpenDialog(Stages.createBasicItem);

        if(file == null) return;

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

        Item.UseStyle useStyle = Item.UseStyle.fromName(useStylePicker.getSelectedItem());
        String sUseTime = useTime.getText();

        String sDamage = damage.getText();
        String sKnockback = knockback.getText();
        String sCritChance = critChance.getText();


        if(sWidth.isEmpty() || sHeight.isEmpty() || sName.isEmpty() || sDisplayName.isEmpty() || sUseTime.isEmpty() || sDamage.isEmpty() || sKnockback.isEmpty() || sCritChance.isEmpty()) return;

        if(sPlatinum.isEmpty()) sPlatinum = "0";
        if(sGold.isEmpty()) sGold = "0";
        if(sSilver.isEmpty()) sSilver = "0";
        if(sCopper.isEmpty()) sCopper = "1";
        int iPlatinum, iGold, iSilver, iCopper;
        int iUseTime;
        int iDamage;
        float fKnockback;
        int iCritChance;
        try {
            iPlatinum = Integer.parseInt(sPlatinum);
            iGold = Integer.parseInt(sGold);
            iSilver = Integer.parseInt(sSilver);
            iCopper = Integer.parseInt(sCopper);
            iUseTime = Integer.parseInt(sUseTime);
            iDamage = Integer.parseInt(sDamage);
            fKnockback = Float.parseFloat(sKnockback);
            iCritChance = Integer.parseInt(sCritChance);
        } catch(NumberFormatException e) {
            return;
        }

        Item item = new MeleeItem();
        item.setSprite(sprite);
        item.setWidth(Integer.parseInt(sWidth));
        item.setHeight(Integer.parseInt(sHeight));
        item.setName(sName);
        item.setDisplayName(sDisplayName);
        item.setMaxStack(Integer.parseInt(sMaxStack));
        item.setValue(TerrariaUtil.toValue(iPlatinum, iGold, iSilver, iCopper));
        item.setRarity(rarity);
        item.setExpertOnly(expertExclusive.isSelected());

        item.setUseStyle(useStyle);
        item.setUseTime(iUseTime);
        item.setAutoReuse(autoReuse.isSelected());

        item.setDamage(iDamage);
        item.setKnockback(fKnockback);
        item.setCritChance(iCritChance);

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

        Stages.createWeaponItem.hide();
    }
}
