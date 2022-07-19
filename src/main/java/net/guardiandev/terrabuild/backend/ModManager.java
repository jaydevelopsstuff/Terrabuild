package net.guardiandev.terrabuild.backend;

import lombok.Getter;
import net.guardiandev.terrabuild.backend.api.Mod;
import net.guardiandev.terrabuild.backend.api.loader.ModLoader;
import net.guardiandev.terrabuild.backend.api.loader.NameModLoader;
import net.guardiandev.terrabuild.backend.api.loader.TBModLoader;
import net.guardiandev.terrabuild.backend.api.saver.ModSaver;
import net.guardiandev.terrabuild.backend.api.saver.TBModSaver;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ModManager {
    public static final File modsFolder = new File("./mods");

    private final ModLoader nameModLoader = new NameModLoader();
    private final ModLoader loader = new TBModLoader();
    private final ModSaver saver = new TBModSaver();

    @Getter
    private Mod loadedMod = null;

    public ModManager() {
        if(modsFolder.isFile()) modsFolder.delete();
        if(!modsFolder.exists()) modsFolder.mkdir();
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
    }

    public void saveLoadedMod() {
        saveMod(loadedMod.getName());
    }

    public void saveMod(String name) {
        saveModAbs(String.format("./mods/%s.tbmod", name));
    }

    public void saveModAbs(String location) {
        saveModAbs(new File(location));
    }

    public void saveModAbs(Path path) {
        saveModAbs(path.toFile());
    }

    public void saveModAbs(File file) {
        if(loadedMod == null) throw new RuntimeException("No loaded mod");
        saver.save(loadedMod, file);
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
