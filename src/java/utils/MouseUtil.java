package utils;

import org.lwjgl.opengl.Display;

import java.awt.*;

public class MouseUtil {

    public static boolean getHover(float x, float y, float x2, float y2, float mouseX, float mouseY) {
        return (x < mouseX && y < mouseY && x2 > mouseX && y2 > mouseY);
    }

    public static boolean isMouseOnWindow() {
        return Display.isActive() && getHover(Display.getX(), Display.getY(), Display.getX() + Display.getWidth(), Display.getY() + Display.getHeight(), MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
    }

}
