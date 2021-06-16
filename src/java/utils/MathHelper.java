package utils;

import java.util.Random;

public class MathHelper {

    public static double clamp_double(double num, double min, double max)
    {
        return num < min ? min : (num > max ? max : num);
    }

    public static float getRandom(float min, float max) {
        return min + (max - min) * new Random().nextFloat();
    }

}
