package screen.screens;

import client.Main;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import screen.ScreenBase;
import setting.Category;
import setting.Setting;
import utils.MouseUtil;
import utils.RenderUtil;
import utils.render.Button;

import java.util.stream.Collectors;

public class OptionScreen extends ScreenBase {

    public long openTime;
    public String category;

    public OptionScreen(ScreenBase screenBase) {
        super(screenBase);
        this.init();
        category = Category.Visual.name();
        openTime = System.currentTimeMillis();
    }

    @Override
    public void action(int id) {
        super.action(id);
        if(id == 0) {
            Main.instance.screen.currentScreen = new MainMenu();
        }
    }

    @Override
    public void init() {
        super.init();
        this.buttons.add(new Button("Back to MainMenu", 0, Display.getWidth()/2 - 135 / 2, Display.getHeight()/ 2 + 82, 135, 20));
    }

    public void render(int mouseX, int mouseY) {
        RenderUtil.drawQuad(Display.getWidth()/2 - 130, Display.getHeight() /2 - 80, Display.getWidth() / 2 + 130, Display.getHeight() / 2 + 80, new Color(0xAA000000));
        String text = "Options";
        Main.instance.screen.fontRenderer.drawStringWithShadow(text, Display.getWidth() /2 - Main.instance.screen.fontRenderer.getWidth(text) / 2, Display.getHeight()/2 - 75, new Color(-1));
        float y = 0;
        for(Category category : Category.values()) {
            Main.instance.screen.fontRendererSmall.drawStringWithShadow(category.name(), Display.getWidth() /2 - 125, Display.getHeight()/2 - 50 + y, new Color(category.name().equals(this.category) ? -1 : 0x888888));
            y += Main.instance.screen.fontRendererSmall.getHeight();
        }
        float y1 = 0;
        for(Object obj : Main.instance.settingManager.settings) {
            Setting setting = (Setting) obj;
            if(!setting.category.name().equals(this.category)) {
                continue;
            }
            Main.instance.screen.fontRendererSmall.drawStringWithShadow(setting.name, Display.getWidth() / 2, Display.getHeight()/2 - 50 + y1, new Color(-1));
            y1 += Main.instance.screen.fontRendererSmall.getHeight();
            for(String string : setting.selection) {
                RenderUtil.drawCircle(Display.getWidth() / 2, Display.getHeight()/2 - 50 + y1 + 7, 4, new Color(0xAAFFFFFF));
                if(string.equals(setting.getValue())) {
                    RenderUtil.drawCircle(Display.getWidth() / 2, Display.getHeight()/2 - 50 + y1 + 7, 3, new Color(0xAA00AAFF));
                }

                Main.instance.screen.fontRendererSmall.drawStringWithShadow(string, Display.getWidth() / 2 + 7, Display.getHeight()/2 - 50 + y1, new Color(-1));
                y1 += Main.instance.screen.fontRendererSmall.getHeight();
            }
        }
        super.render(mouseX, mouseY);
    }



    @Override
    public void mouseClicked(float mouseX, float mouseY, int button) {
        float y = 0;
        for(Category category : Category.values()) {
            if(MouseUtil.getHover(Display.getWidth() /2 - 125, Display.getHeight()/2 - 50 + y, Display.getWidth() /2 - 125 + 50, Display.getHeight()/2 - 50 + y + Main.instance.screen.fontRendererSmall.getHeight(), mouseX, mouseY)) {
                this.category = category.name();
                break;
            }
            y += Main.instance.screen.fontRendererSmall.getHeight();
        }
        float y1 = 0;
        for(Object obj : Main.instance.settingManager.settings.stream().filter(s-> s.category.name().equals(this.category)).collect(Collectors.toList())) {
            Setting setting = (Setting) obj;
            y1 += Main.instance.screen.fontRendererSmall.getHeight();
            for(int i = 0; i < setting.selection.size(); i++) {
                if(MouseUtil.getHover(Display.getWidth() / 2- 7, Display.getHeight()/2 - 50 + y1 - 1, Display.getWidth() / 2 + 10, Display.getHeight()/2 - 50 + y1 + 12, mouseX, mouseY)) {
                    setting.index = i;
                    break;
                }
                y1 += Main.instance.screen.fontRendererSmall.getHeight();
            }
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void keyTyped(int key) {
        if(key == Keyboard.KEY_ESCAPE) {
            Main.instance.screen.optionScreen = null;
        }
        super.keyTyped(key);
    }
}
