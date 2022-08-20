package net.guardiandev.terrabuild.backend.api.content.projectile;

import lombok.Data;
import net.guardiandev.terrabuild.backend.api.content.AIStyle;
import net.guardiandev.terrabuild.backend.api.content.DamageType;

@Data
public class Projectile {
    protected String name;
    protected int width;
    protected int height;
    protected AIStyle aiStyle;
    protected boolean hostile;
    protected boolean friendly;
    protected int penetrate;
    protected int timeLeft;
    protected DamageType damageType;
    protected boolean blockCollide;

}
