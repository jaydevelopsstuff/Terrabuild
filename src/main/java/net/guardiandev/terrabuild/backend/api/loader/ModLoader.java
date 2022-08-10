package net.guardiandev.terrabuild.backend.api.loader;

import net.guardiandev.terrabuild.backend.api.content.Mod;

import java.io.File;
import java.nio.file.Path;

public interface ModLoader {
    Mod load(String location);
    Mod load(Path path);
    Mod load(File file);
}
