package net.guardiandev.terrabuild.backend;

import lombok.Getter;
import net.guardiandev.terrabuild.TerrabuildApplication;
import net.guardiandev.terrabuild.backend.api.content.Mod;
import net.guardiandev.terrabuild.backend.api.loader.ModLoader;
import net.guardiandev.terrabuild.backend.api.loader.NameModLoader;
import net.guardiandev.terrabuild.backend.api.loader.TBModLoader;
import net.guardiandev.terrabuild.backend.api.saver.ModSaver;
import net.guardiandev.terrabuild.backend.api.saver.TBModSaver;
import net.guardiandev.terrabuild.gui.Terrabuild;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ModManager {
    public static final File modsFolder = new File("./mods");

    private final ModLoader nameModLoader = new NameModLoader();
    private final ModLoader loader = new TBModLoader();
    private final ModSaver saver = new TBModSaver();
    private final Timer autoSaveTimer = new Timer();
    private boolean startedAutoSave = false;

    @Getter
    private Mod loadedMod = null;

    public ModManager() {
        if(modsFolder.isFile()) modsFolder.delete();
        if(!modsFolder.exists()) modsFolder.mkdir();
    }

    public void exit() {
        saveLoadedMod();
        autoSaveTimer.cancel();
    }

    public List<Mod> getSavedMods() {
        File[] files = modsFolder.listFiles();

        List<Mod> mods = new ArrayList<>();
        for(File file : files) {
            if(!file.isFile()) continue;
            if(!file.toString().endsWith(".tbmod")) continue;
            mods.add(nameModLoader.load(file));
        }
        return mods;
    }

    public Mod createNewMod() {
        loadedMod = new Mod();
        return loadedMod;
    }

    public void loadMod(String name) {
        loadModAbs(String.format("./mods/%s.tbmod", name));
    }

    public void loadModAbs(String location) {
        loadModAbs(new File(location));
    }

    public void loadModAbs(Path path) {
        loadModAbs(path.toFile());
    }

    public void loadModAbs(File file) {
        loadedMod = loader.load(file);

        // Schedule auto save cycle if not already started
        if(startedAutoSave) return;
        autoSaveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(loadedMod.isDirty()) {
                    if(saveLoadedMod()) {
                        Terrabuild.getINSTANCE().statusText.setText(String.format("Successfully saved %s", loadedMod.getName()));
                        Terrabuild.getINSTANCE().createStatusDeleteTask(3);
                    } else {
                        Terrabuild.getINSTANCE().statusText.setText(String.format("Failed to save %s", loadedMod.getName()));
                        Terrabuild.getINSTANCE().createStatusDeleteTask(3);
                    }
                }
            }
        }, 500, 45 * 1000);
        startedAutoSave = true;
    }

    public boolean saveLoadedMod() {
        if(loadedMod == null) return false;
        TerrabuildApplication.Logger.info(String.format("Saving mod %s", loadedMod.getName()));
        return saveMod(loadedMod.getName());
    }

    public boolean saveMod(String name) {
        return saveModAbs(String.format("./mods/%s.tbmod", name));
    }

    public boolean saveModAbs(String location) {
        return saveModAbs(new File(location));
    }

    public boolean saveModAbs(Path path) {
        return saveModAbs(path.toFile());
    }

    public boolean saveModAbs(File file) {
        if(loadedMod == null) throw new RuntimeException("No loaded mod");
        boolean result = saver.save(loadedMod, file);
        loadedMod.clean();
        return result;
    }

    public void deleteLoadedMod() {
        deleteMod(loadedMod.getName());
    }

    public void deleteMod(String name) {
        if(loadedMod != null)
            if(loadedMod.getName().equals(name)) loadedMod = null;
        deleteModAbs(String.format("./mods/%s.tbmod", name));
    }

    public void deleteModAbs(String location) {
        deleteModAbs(new File(location));
    }

    public void deleteModAbs(Path path) {
        deleteModAbs(path.toFile());
    }

    public void deleteModAbs(File file) {
        file.delete();
    }
}
