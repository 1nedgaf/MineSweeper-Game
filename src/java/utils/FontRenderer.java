package utils;


import client.Main;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class FontRenderer extends TrueTypeFont {

    public FontRenderer(Font font, boolean antiAlias) {
        super(font, antiAlias);
    }

    public void drawString(String text, float x, float y, int color) {
        this.drawString(x, y, text, new org.newdawn.slick.Color(color));
    }

    public void drawStringWithShadow(String text, float x, float y, org.newdawn.slick.Color color) {
        Main.instance.window.GL.glEnable(3553);
        this.drawString(x + 1, y + 1, text, new org.newdawn.slick.Color(0xFF000000));
        this.drawString(x, y, text, color);
    }

}
