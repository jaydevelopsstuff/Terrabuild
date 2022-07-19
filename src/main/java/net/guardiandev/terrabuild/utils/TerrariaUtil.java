package net.guardiandev.terrabuild.utils;

public class TerrariaUtil {
    public static int toValue(int platinum, int gold, int silver, int copper) {
        return copper + silver * 100 + gold * 10000 * platinum * 1000000;
    }
}
