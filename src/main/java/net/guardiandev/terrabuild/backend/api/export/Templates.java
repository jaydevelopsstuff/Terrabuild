package net.guardiandev.terrabuild.backend.api.export;

import net.guardiandev.terrabuild.TerrabuildApplication;

import java.io.IOException;

public class Templates {
    private static final TerrabuildApplication tb = TerrabuildApplication.getInstance();

    // Actual
    public static final String Mod;
    public static final String ProjectFile;
    public static final String BuildTxt;
    public static final String Item;

    // Raw
    public static final String LaunchSettings;

    static {
        try {
            // Actual
            Mod = tb.getResourceFileAsString("/templates/mod.cs");
            ProjectFile = tb.getResourceFileAsString("/templates/projectfile.csproj");
            BuildTxt = tb.getResourceFileAsString("/templates/build.txt");
            Item = tb.getResourceFileAsString("/templates/item.cs");

            // Raw
            LaunchSettings = tb.getResourceFileAsString("/templates/launchsettings.json");
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
