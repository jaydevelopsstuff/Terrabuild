package net.guardiandev.terrabuild.backend.api.content;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AIStyle {
    None(-1),
    Default(0),
    ArrowBullet(1),
    Shuriken(2),
    EnchantedBoomerang(3),
    Vilethorn(4),
    FallingStar(5),
    PurificationPowder(6),
    Hook(7),
    BallOfFire(8),
    MagicMissile(9),
    DirtBall(10);
    // TODO: The rest

    public final int id;
}
