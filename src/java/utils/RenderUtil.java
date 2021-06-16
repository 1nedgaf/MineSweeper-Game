package utils;

import client.Main;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.PNGDecoder;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil {

    public static void drawQuad(double x, double y, double x1, double y1, org.newdawn.slick.Color color) {
        Main.instance.window.GL.glDisable(3553);
        color.bind();
        //glColor3d(col.getRed(), col.getGreen(), col.getBlue());
        glBegin(GL_QUADS);
        glVertex2d(x, y);
        glVertex2d(x1, y);
        glVertex2d(x1, y1);
        glVertex2d(x, y1);
        glEnd();
    }

    public static void drawOutline(double x, double y, double x1, double y1, double width, org.newdawn.slick.Color outlineColor) {
        drawQuad(x, y, x1, y + width, outlineColor);
        drawQuad(x, y + width, x + width, y1 - width, outlineColor);
        drawQuad(x1 - width, y + width, x1, y1 - width, outlineColor);
        drawQuad(x, y1 - width, x1, y1, outlineColor);
    }

    public static void drawOutlinedQuad(double x, double y, double x1, double y1, double width, org.newdawn.slick.Color color, org.newdawn.slick.Color outlineColor) {
        drawOutline(x, y, x1, y1, width, outlineColor);
        drawQuad(x + width, y + width,  x1 - width, y1 - width, color);
    }

    public static void drawCircle(float x, float y, double r, Color color)
    {
        Main.instance.window.GL.glDisable(3553);
        color.bind();
        double k=0;
        glBegin(GL_TRIANGLE_FAN);
        for(k=0;k<=360;k+=1){
            glVertex2f((float)(x+r*Math.cos(Math.toRadians(k))),(float)(y-r*Math.sin(Math.toRadians(k))));
        }
        glEnd();
    }

    public static void drawGradiantQuad(double x, double y, double x1, double y1, org.newdawn.slick.Color color, org.newdawn.slick.Color undercolor) {
        Main.instance.window.GL.glDisable(3553);
        color.bind();
        //glColor3d(col.getRed(), col.getGreen(), col.getBlue());
        glBegin(GL_QUADS);
        glVertex2d(x, y);
        glVertex2d(x1, y);
        undercolor.bind();
        glVertex2d(x1, y1);
        glVertex2d(x, y1);
        glEnd();
    }

    public static void drawImage(ByteBuffer byteBuffer) {

    }

    public static ByteBuffer loadIcon(URL url) throws IOException {
        InputStream is = url.openStream();
        try {
            PNGDecoder decoder = new PNGDecoder(is);
            ByteBuffer bb = ByteBuffer.allocateDirect(decoder.getWidth() * decoder.getHeight() * 4);
            decoder.decode(bb, decoder.getWidth() * 4, PNGDecoder.RGBA);
            bb.flip();
            return bb;
        } finally {
            is.close();
        }
    }

}
