package net.guardiandev.terrabuild.backend.api.loader;

import net.guardiandev.terrabuild.backend.api.Mod;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class NameModLoader implements ModLoader {
    @Override
    public Mod load(String location) {
        return load(new File(location));
    }

    @Override
    public Mod load(Path path) {
        return load(path.toFile());
    }

    @Override
    public Mod load(File file) {
        if(!file.exists()) return Mod.failed("File does not exist");

        try {
            DataInputStream input = new DataInputStream(new FileInputStream(file));

            if(input.readLong() != 7454971902120191350L) return Mod.failed("Failed to read file, non-matching magic number");

            Mod mod = new Mod();

            mod.setName(input.readUTF());
            mod.setDisplayName(input.readUTF());

            return mod;
        } catch(IOException e) {
            e.printStackTrace();
            return Mod.failed("Failed to read file");
        }
    }
}
