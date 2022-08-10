package net.guardiandev.terrabuild.backend.api.loader;

import net.guardiandev.terrabuild.backend.api.content.item.Item;
import net.guardiandev.terrabuild.backend.api.content.Mod;
import net.guardiandev.terrabuild.backend.api.ModContent;

import java.io.*;
import java.nio.file.Path;

public class TBModLoader implements ModLoader {
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
            mod.setVersion(input.readUTF());
            mod.setAuthor(input.readUTF());
            mod.setDescription(input.readUTF());

            readModContent(mod.getContent(), input);

            return mod;
        } catch(IOException e) {
            e.printStackTrace();
            return Mod.failed("Failed to read file");
        }
    }

    public void readModContent(ModContent content, DataInputStream input) throws IOException {
        int itemCount = input.readInt();

        for(int i = 0; i < itemCount; i++) {
            Item item = new Item();
            item.read(input);
            content.addItem(item);
        }
        // TODO
    }
}
