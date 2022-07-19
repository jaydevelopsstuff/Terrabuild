package net.guardiandev.terrabuild.backend.api.export;

import net.guardiandev.terrabuild.backend.api.Item;
import net.guardiandev.terrabuild.backend.api.Mod;
import net.guardiandev.terrabuild.backend.api.ModContent;
import net.guardiandev.terrabuild.utils.FileUtil;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ModExporter {
    public void export(String exportFolder, Mod mod) throws IOException {
        export(new File(exportFolder), mod);
    }

    public void export(Path exportFolder, Mod mod) throws IOException {
        export(exportFolder.toFile(), mod);
    }

    public void export(File exportFolder, Mod mod) throws IOException {
        if(!exportFolder.exists() || exportFolder.isFile()) throw new RuntimeException("Invalid export folder");

        File parentFolder = FileUtil.newFolder(exportFolder.toString(), mod.getName());
        File propertiesFolder = FileUtil.newFolder(parentFolder.toString(), "Properties");

        String modFileContent = Template.apply(Templates.Mod, new Template.Optional[0], new Template.Variable[]{ new Template.Variable("ModName", mod.getName()) });
        String csprojContent = Template.apply(Templates.ProjectFile, new Template.Optional[0], new Template.Variable[]{ new Template.Variable("ModName", mod.getName()) });

        FileUtil.newFileWithContent(parentFolder.toString(), mod.getName(), ".cs", modFileContent);
        FileUtil.newFileWithContent(parentFolder.toString(), mod.getName(), ".csproj", csprojContent);
        FileUtil.newFileWithContent(propertiesFolder.toString(), "launchsettings.json", Templates.LaunchSettings);
        // TODO: Build.txt
        // FileUtil.newFileWithContent(parentFolder.toString(), "build.txt", ...);
        FileUtil.newFileWithContent(parentFolder.toString(), "description.txt", mod.getDescription());

        exportModContent(mod, parentFolder);
    }

    public void exportModContent(Mod mod, File parentFolder) throws IOException {
        String modName = mod.getName();
        ModContent content = mod.getContent();

        File contentFolder = FileUtil.newFolder(parentFolder.toString(), "Content");

        File itemsFolder = FileUtil.newFolder(contentFolder.toString(), "Items");

        for(Item item : content.getItems()) {
            String name = item.getName();

            String codeOutput = ItemExporter.toCode(mod, item);

            FileUtil.newFileWithContent(itemsFolder.toString(), name, ".cs", codeOutput);

            ImageIO.write(item.getSprite(), "png", FileUtil.newFile(itemsFolder.toString(), name, ".png"));
        }
    }
}
