package screen.screens;

import client.Main;
import org.lwjgl.opengl.Display;
import utils.GLSLRender;
import screen.ScreenBase;
import utils.render.Button;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class MainMenu extends ScreenBase {


    public MainMenu() {
        this.init();

    }

    @Override
    public void init() {
        this.buttons.clear();
        this.buttons.add(new Button("Play", 0, Display.getWidth()/ 2 - 50, Display.getHeight() /2 - 10, 100, 20));
        this.buttons.add(new Button("Option", 1, Display.getWidth()/ 2 - 50, Display.getHeight() /2 + 15, 100, 20));
        super.init();
    }

    @Override
    public void action(int id) {
        if(id == 0) {
            Main.instance.screen.currentScreen = new MineSweeper();
        }
        if(id == 1) {
            Main.instance.screen.optionScreen = new OptionScreen(Main.instance.screen.currentScreen);
        }
        super.action(id);
    }

    public void render(int mouseX, int mouseY) {
        this.getScreen().renderGLSL(mouseX, mouseY);
        super.render(mouseX, mouseY);
    }
}
