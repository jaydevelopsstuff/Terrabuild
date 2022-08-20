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
            <[SetExpertExclusive~Item.expert = <{ExpertExclusive}>;]>

            Item.useStyle = <{UseStyle}>;
            Item.useTime = <{UseTime}>;
            Item.useAnimation = <{UseAnimation}>;
            Item.autoReuse = <{AutoReuse}>;

            <[SetDamageClass~Item.DamageType = DamageClass.<{DamageClass}>;]>
            Item.damage = <{Damage}>;
            Item.knockBack = <{Knockback}>;
            Item.crit = <{CritChance}>;
        }
    }
}