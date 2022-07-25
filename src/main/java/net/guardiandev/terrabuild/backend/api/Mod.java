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

    private ModContent content = new ModContent(this);

    private boolean dirty = false;

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

    public void setName(String name) {
        this.name = name;
        dirty();
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        dirty();
    }

    public void setVersion(String version) {
        this.version = version;
        dirty();
    }

    public void setAuthor(String author) {
        this.author = author;
        dirty();
    }

    public void setDescription(String description) {
        this.description = description;
        dirty();
    }

    public void dirty() {
        dirty = true;
    }

    public void clean() {
        dirty = false;
    }
}
