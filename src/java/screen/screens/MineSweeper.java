package screen.screens;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import screen.ScreenBase;
import screen.screens.minesweeper.Box;
import screen.screens.minesweeper.Map;
import utils.MathHelper;
import utils.MouseUtil;
import utils.RenderUtil;

public class MineSweeper extends ScreenBase {

    public Map map;
    public int size = 15;
    public static int boxSize = 20;
    public boolean firstTouch;

    public MineSweeper() {
        super();
        firstTouch = false;
        map = new Map();
        int x = Display.getWidth() / 2 - size * boxSize / 2;
        int y = Display.getHeight() / 2 - size * boxSize / 2;
        for(int xIndex = 0; xIndex < size; xIndex++) {
            for(int yIndex = 0; yIndex < size; yIndex++) {
                map.addBox(new Box(x + xIndex * boxSize, y + yIndex * boxSize));
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY) {
        this.getScreen().renderGLSL(mouseX, mouseY);
        for(Box box : map.getBoxList()) {
            if(!box.hidden) {
                //RenderUtil.drawQuad(box.getPosX(), box.getPosY(), box.getPosX() + boxSize, box.getPosY() + boxSize, new Color(0xFFAAAAAA));
                if(box.INT != -1) {
                    getScreen().fontRenderer.drawStringWithShadow(Integer.toString(box.INT), box.getPosX() + 5, box.getPosY() + 2, new Color(-1));
                }
                if(box.bomb) {
                    getScreen().fontRenderer.drawStringWithShadow("o", box.getPosX() + 5, box.getPosY() + 2, new Color(Color.red));
                }

            } else {
                RenderUtil.drawGradiantQuad(box.getPosX(), box.getPosY(), box.getPosX() + boxSize, box.getPosY() + boxSize, new Color(0xFF445566), new Color(0xFF111122));
            }
            RenderUtil.drawOutline(box.getPosX(), box.getPosY(), box.getPosX() + boxSize, box.getPosY() + boxSize,1, new Color(0xFF222222));
            if(box.flag) {
                getScreen().fontRenderer.drawStringWithShadow("?", box.getPosX() + 5, box.getPosY() + 2, new Color(0xFF3333));
            }
        }
        super.render(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        for(Box box : map.getBoxList()) {
            if(box.hidden && MouseUtil.getHover(box.getPosX(), box.getPosY(), box.getPosX() + boxSize, box.getPosY() + boxSize, mouseX, mouseY)) {
                if(button == 0 && !box.flag) {
                    if(!firstTouch) {
                        this.map.boom(box.getPosX(), box.getPosY());
                        firstTouch = true;
                    } else {
                        this.map.openBox(box);
                    }
                } else if(button == 1){
                    box.flag = !box.flag;
                }
            }
        }
    }

}
