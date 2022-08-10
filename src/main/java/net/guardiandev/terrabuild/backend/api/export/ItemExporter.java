package net.guardiandev.terrabuild.backend.api.export;

import net.guardiandev.terrabuild.backend.api.content.item.Item;
import net.guardiandev.terrabuild.backend.api.content.Mod;

public class ItemExporter {
    public static String toCode(Mod mod, Item item) {
        // TODO

        return Template.apply(Templates.Item, new Template.Optional[]{
                new Template.Optional("UsingID", true), new Template.Optional("SetTooltip", false)
        }, new Template.Variable[]{
                new Template.Variable("ModName", mod.getName()), new Template.Variable("ItemName", item.getName()),
                new Template.Variable("ItemWidth", Integer.toString(item.getWidth())), new Template.Variable("ItemHeight", Integer.toString(item.getHeight())),
                new Template.Variable("ItemValue", Integer.toString(item.getValue())), new Template.Variable("ItemRarity", String.format("ItemRarityID.%s", item.getRarity().name())),
                new Template.Variable("ItemMaxStack", Integer.toString(item.getMaxStack()))
        });
    }
}
