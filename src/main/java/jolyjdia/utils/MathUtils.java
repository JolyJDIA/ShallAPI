package jolyjdia.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Random;

public final class MathUtils {
    public static final Random RANDOM = new Random();
    private static final double DEGREES_TO_RADIANS = 0.017453292519943295;
    public static final double CIRCE = 6.283185307179586;

    private MathUtils() {}

    public static double offset(Location loc, Location loc1) {
        return offset(loc.toVector(), loc1.toVector());
    }

    public static double offset(Vector v, Vector v1) {
        return v.subtract(v1).length();
    }

    public static double randomDouble(float v, float v1) {
        return (v + Math.random() * (v1 - v));
    }
    //если джава 8 или ниже, в противном случае лучше юзать Math.toRadians
    public static double toRadians(double angdeg) {
        //angdeg / 180.0 * PI
        //angdeg * PI/180;
        return angdeg * DEGREES_TO_RADIANS;
    }
    public static double toRadians(int angdeg) {
        return angdeg * DEGREES_TO_RADIANS;
    }

    public static int randomDouble(int i, int i1) {
        return RANDOM.nextInt(i1 - i + 1) + i;
    }
    public static float randomFloat() {
        return RANDOM.nextFloat();
    }

    public static byte toPackedByte(double var0) {
        return (byte) (var0 * 256.0F / 360.0F);
    }
}
