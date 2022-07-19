package net.guardiandev.terrabuild.backend.api.saver;

import net.guardiandev.terrabuild.backend.api.Item;
import net.guardiandev.terrabuild.backend.api.Mod;
import net.guardiandev.terrabuild.backend.api.ModContent;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TBModSaver implements ModSaver {
    @Override
    public boolean save(Mod mod, String saveLocation) {
        return save(mod, new File(saveLocation));
    }

    @Override
    public boolean save(Mod mod, Path savePath) {
        return save(mod, savePath.toFile());
    }

    @Override
    public boolean save(Mod mod, File saveFile) {
        if(!saveFile.toString().endsWith(".tbmod")) throw new RuntimeException("Save file extension isn't .tbmod");
        if(saveFile.exists()) {
            try {
                Files.copy(saveFile.toPath(), Paths.get(String.format("%s.bak", saveFile.toString())), StandardCopyOption.REPLACE_EXISTING);
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
            saveFile.delete();
        }

        try {
            saveFile.createNewFile();

            // Big Endian
            DataOutputStream output = new DataOutputStream(new FileOutputStream(saveFile));

            ByteBuffer magicNumber = ByteBuffer.wrap("guarddev".getBytes(StandardCharsets.UTF_8));
            output.writeLong(magicNumber.getLong());

            output.writeUTF(mod.getName());
            output.writeUTF(mod.getDisplayName());
            output.writeUTF(mod.getVersion());
            output.writeUTF(mod.getAuthor());
            output.writeUTF(mod.getDescription());

            saveModContent(mod.getContent(), output);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public void saveModContent(ModContent content, DataOutputStream output) throws IOException {
        output.writeInt(content.getItems().size());
        for(Item item : content.getItems()) {
            item.write(output);
        }
        // TODO
    }
}
