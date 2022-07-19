package net.guardiandev.terrabuild.backend.api.saver;

import net.guardiandev.terrabuild.backend.api.Mod;

import java.io.File;
import java.nio.file.Path;

public interface ModSaver {
    boolean save(Mod mod, String saveLocation);
    boolean save(Mod mod, Path savePath);
    boolean save(Mod mod, File saveFile);
}
