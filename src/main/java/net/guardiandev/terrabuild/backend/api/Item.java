package net.guardiandev.terrabuild.backend.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Data
public class Item {
    private String name;
    private String displayName;
    private int width;
    private int height;
    private int maxStack = 1;
    private int value = 0;
    private Rarity rarity = Rarity.White;
    private BufferedImage sprite;

    public void read(DataInputStream input) throws IOException {
        name = input.readUTF();
        width = input.readInt();
        height = input.readInt();
        maxStack = input.readInt();
        value = input.readInt();
        rarity = Rarity.fromId(input.readInt());
        sprite = ImageIO.read(input);
    }

    public void write(DataOutputStream output) throws IOException {
        output.writeUTF(name);
        output.writeInt(width);
        output.writeInt(height);
        output.writeInt(maxStack);
        output.writeInt(value);
        output.writeInt(rarity.id);
        ImageIO.write(sprite, "png", output);
    }

    @RequiredArgsConstructor
    public enum Rarity {
        Gray(-1),
        // Defualt rarity
        White(0),
        Blue(1),
        Green(2),
        Orange(3),
        LightRed(4),
        Pink(5),
        LightPurple(6),
        Lime(7),
        Yellow(8),
        Cyan(9),
        Red(10),
        Purple(11),
        // Expert
        Rainbow(-12),
        // Master
        FieryRed(-13),
        // Quest
        Amber(-11);

        public final int id;

        public static Rarity fromId(int id) {
            for(Rarity rarity : Rarity.values()) {
                if(rarity.id == id) return rarity;
            }
            return null;
        }

        public static Rarity fromName(String name) {
            for(Rarity rarity : Rarity.values()) {
                if(rarity.name().equals(name)) return rarity;
            }
            return null;
        }
    }
}
