package net.guardiandev.terrabuild.gui;

import net.guardiandev.terrabuild.TerrabuildApplication;

public class DeleteModDialog {
    public void deleteConfirmReleased() {
        ManageMods mMods = ManageMods.getINSTANCE();
        TerrabuildApplication.modManager.deleteMod(mMods.modsList.getSelectionModel().getSelectedValues().get(0).getName());
        mMods.refresh();
        Terrabuild.getINSTANCE().refreshLoadedMod(false);
        Stages.deleteModDialog.hide();
    }

    public void deleteCancelReleased() {
        Stages.deleteModDialog.hide();
    }
}
