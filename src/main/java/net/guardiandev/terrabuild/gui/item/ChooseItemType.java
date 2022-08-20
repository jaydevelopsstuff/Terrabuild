package net.guardiandev.terrabuild.gui.item;

import net.guardiandev.terrabuild.gui.Stages;

public class ChooseItemType {
    public void basicReleased() {
        Stages.createBasicItem.show();
        Stages.chooseItemType.hide();
    }

    public void weaponReleased() {
        Stages.createWeaponItem.show();
        Stages.chooseItemType.hide();
    }

    public void toolReleased() {

    }

    public void armorReleased() {

    }

    public void accessoryReleased() {

    }

    public void advancedReleased() {

    }
}
