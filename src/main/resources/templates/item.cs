<[UsingID~using Terraria.ID;]>
using Terraria.ModLoader;

namespace <{ModName}>.Content.Items
{
    public class <{ItemName}> : ModItem
    {
        public override void SetStaticDefaults() {
            <[SetTooltip~Tooltip.SetDefault("<{ItemTooltip}>")]>
        }

        public override void SetDefaults()
        {
            Item.width = <{ItemWidth}>;
            Item.height = <{ItemHeight}>;
            Item.value = <{ItemValue}>;
            Item.rare = <{ItemRarity}>;
            Item.maxStack = <{ItemMaxStack}>;
        }
    }
}