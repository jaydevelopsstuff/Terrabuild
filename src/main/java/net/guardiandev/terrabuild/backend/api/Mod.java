package net.guardiandev.terrabuild.backend.api;

import lombok.Data;

@Data
public class Mod {
    public boolean loadFailed = false;
    public String failReason;

    private String name;
    private String displayName;
    private String version;
    private String author;
    private String description;

    private ModContent content = new ModContent();

    public static Mod failed(String reason) {
        Mod mod = new Mod();
        mod.loadFailed = true;
        mod.failReason = reason;
        return mod;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
