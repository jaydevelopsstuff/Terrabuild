package net.guardiandev.terrabuild.backend.api.content.item;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Data
public class Item {
    // Common
    protected String name;
    protected String displayName;
    protected int width;
    protected int height;
    /**
     * Scales item (multiplies width/height by this).
     */
    protected float scale;
    protected int maxStack = 1;
    /**
     * Sell value for NPCs.
     */
    protected int value = 0;
    protected Rarity rarity = Rarity.White;
    /**
     * Sprite for the item.
     */
    protected BufferedImage sprite;


    // Type
    protected ItemType type = ItemType.Basic;
    protected WeaponType weaponType = WeaponType.None;
    /**
     * Whether this item does melee damage with its animation.
     */
    protected boolean noMeleeDamage = false;
    /**
     * Whether this item is a consumable.
     */
    protected boolean consumable = false;
    /**
     * Whether this item is an accessory.
     */
    protected boolean accessory = false;


    // Use
    // TODO: Review creates
    /**
     * The ID of the block this item will place (-1 for no place).
     */
    protected int createBlockId = -1;
    /**
     * The ID of the wall this item will place (-1 for no place).
     */
    protected int createWallId = -1;
    protected UseStyle useStyle = UseStyle.None;
    protected int useAnimation = 100;
    protected int useTime = 100;
    /**
     * Whether this item is an expert mode exclusive item.
     */
    protected boolean expertOnly;


    // Stats
    protected int damage = -1;
    protected float knockback = 0;
    /**
     * Base critical chance for this item (player has base crit chance of 4).
     */
    protected int critChance = 0;
    /**
     * How much mana this item uses on swing.
     */
    protected int manaUse = 0;

    /**
     * The defense this item adds, applicable to armor and accessories.
     */
    protected int defense;

    /**
     * Pickaxe power.
     */
    protected int pickPower = 0;
    /**
     * Axe power (multiplied by 5).
     */
    protected int axePower = 0;
    /**
     * Hammer power.
     */
    protected int hammerPower = 0;

    /**
     * How much health this item heals on use.
     */
    protected int healLife = 0;
    /**
     * How much mana this item restores on use.
     */
    protected int healMana = 0;


    // Projectile
    /**
     * The projectile id this item shoots (0 if no projectile is shot).
     */
    protected int shootProjectileId = 0;
    /**
     * How often to shoot a projectile.
     */
    protected float shootSpeed = 0;


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

    @RequiredArgsConstructor
    public enum UseStyle {
        None(0),
        Swinging(1),
        Drinking(2),
        Shortsword(3),
        LifeCrystal(4),
        StaffGun(5);

        public final int id;

        public static UseStyle fromId(int id) {
            for(UseStyle useStyle : UseStyle.values()) {
                if(useStyle.id == id) return useStyle;
            }
            return null;
        }
    }
}
