package utils.effect;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import utils.MathHelper;
import utils.RenderUtil;

public class Snow {

    public float x, y, speed, size, vector, scaling;
    public long time;

    public Snow(float width) {
        time = System.currentTimeMillis();
        vector = MathHelper.getRandom(-25, -1);
        this.x = MathHelper.getRandom(vector > -15 ? 0 : -100, width - 50);
        this.y = MathHelper.getRandom(-50, 0);
        this.speed = MathHelper.getRandom(1, 3);
        this.size = MathHelper.getRandom(1, 3);
        this.scaling = MathHelper.getRandom(-1, 0.5F);
    }

    public float getSize() {
        return size + (scaling * size * ((float)(System.currentTimeMillis() - time) / 2000));
    }

    public float getY() {
        double sin = Math.sin(Math.toRadians(vector + 90.0f));
        return (float) (this.y + (sin * speed * ((System.currentTimeMillis() - time) / 7)));
    }

    public float getX() {
        double cos = Math.cos(Math.toRadians(vector + 90.0f));
        return (float) (this.x + (cos * speed * ((System.currentTimeMillis() - time) / 7)));
    }

    public void draw() {
        RenderUtil.drawCircle(getX(), getY(), getSize(), new Color(0x22FFFFFF));
    }

}
