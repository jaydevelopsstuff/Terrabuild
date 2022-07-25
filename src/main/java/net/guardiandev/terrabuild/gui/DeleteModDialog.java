package net.guardiandev.terrabuild.gui;

import net.guardiandev.terrabuild.TerrabuildApplication;

public class DeleteModDialog {
    public void deleteConfirmReleased() {
        ManageMods mMods = ManageMods.getINSTANCE();
        String name = mMods.modsList.getSelectionModel().getSelectedValues().get(0).getName();
        TerrabuildApplication.Logger.info(String.format("Deleting mod %s", name));
        TerrabuildApplication.modManager.deleteMod(name);
        mMods.refresh();
        Terrabuild.getINSTANCE().refreshLoadedMod(false);
        Stages.deleteModDialog.hide();
    }

    public void deleteCancelReleased() {
        Stages.deleteModDialog.hide();
    }
}
