package utils.render;

import client.Main;
import org.newdawn.slick.Color;
import utils.MouseUtil;
import utils.RenderUtil;

public class Button {

    public String text;
    public int id, x, y, width, height;

    public Button(String text, int id, int x, int y, int width, int height) {
        this.text = text;
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(int mouseX, int mouseY) {
        Color color1 = new Color(MouseUtil.getHover(x, y, x + width, y + height, mouseX, mouseY) ? 0x77888888: 0x77333333);
        Color color = new Color(MouseUtil.getHover(x, y, x + width, y + height, mouseX, mouseY) ? 0x77555555: 0x77000000);
        RenderUtil.drawGradiantQuad(x, y, x + width, y + height, color1, color);
        Main.instance.screen.fontRendererSmall.drawStringWithShadow(text, x + width / 2 - Main.instance.screen.fontRendererSmall.getWidth(text) / 2, y + height /2 - 7, new Color(-1));
    }
    public boolean pressed(int mouseX, int mouseY) {
        return (MouseUtil.getHover(x, y, x + width, y + height, mouseX, mouseY));
    }
}
