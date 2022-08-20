package net.guardiandev.terrabuild.backend.api.export;

import net.guardiandev.terrabuild.backend.api.content.item.Item;
import net.guardiandev.terrabuild.backend.api.content.Mod;
import net.guardiandev.terrabuild.backend.api.content.item.WeaponType;

public class ItemExporter {
    public static String toCode(Mod mod, Item item) {
        // TODO

        String rarityEnum = item.getRarity().name();
        if(item.getRarity() == Item.Rarity.Rainbow) rarityEnum = "Expert";
        else if(item.getRarity() == Item.Rarity.Amber) rarityEnum = "Quest";
        return Template.apply(Templates.Item, new Template.Optional[]{
                new Template.Optional("UsingID", true), new Template.Optional("SetTooltip", false),
                new Template.Optional("SetExpertExclusive", item.isExpertOnly()), new Template.Optional("SetDamageClass", item.getWeaponType() != WeaponType.None)
        }, new Template.Variable[]{
                new Template.Variable("ModName", mod.getName()), new Template.Variable("ItemName", item.getName()),
                new Template.Variable("ItemWidth", item.getWidth()), new Template.Variable("ItemHeight", item.getHeight()),
                new Template.Variable("ItemValue", item.getValue()), new Template.Variable("ItemRarity", String.format("ItemRarityID.%s", rarityEnum)),
                new Template.Variable("ItemMaxStack", item.getMaxStack()), new Template.Variable("ExpertExclusive", item.isExpertOnly()),
                new Template.Variable("UseStyle", item.getUseStyle().id), new Template.Variable("UseTime", item.getUseTime()),
                new Template.Variable("UseAnimation", item.getUseAnimation()), new Template.Variable("AutoReuse", item.isAutoReuse()),
                new Template.Variable("DamageClass", item.getWeaponType().name()), new Template.Variable("Damage", item.getDamage()),
                new Template.Variable("Knockback", item.getKnockback()), new Template.Variable("CritChance", item.getCritChance())
        });
    }
}
