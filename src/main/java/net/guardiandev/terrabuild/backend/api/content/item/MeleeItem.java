package net.guardiandev.terrabuild.backend.api.content.item;

public class MeleeItem extends Item {
    public MeleeItem() {
        this.weaponType = WeaponType.Melee;
        this.useStyle = UseStyle.Swinging;
    }
}
