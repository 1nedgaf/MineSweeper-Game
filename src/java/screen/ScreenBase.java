package screen;

import client.Main;
import utils.render.Button;

import java.util.ArrayList;

public class ScreenBase {

    public ScreenBase previousScreen;
    public ArrayList<Button> buttons = new ArrayList<>();

    public Screen getScreen() {
        return Main.instance.screen;
    }

    public ScreenBase() {
    }

    public ScreenBase(ScreenBase screenBase) {
        if(screenBase != null) {
            this.previousScreen = screenBase;
        }
    }

    public void init() {
    }

    public void refresh() {
        init();
    }

    public boolean button(int mouseX, int mouseY) {
        for(Button button : this.buttons) {
            if(button.pressed(mouseX, mouseY)) {
                this.action(button.id);
                return true;
            }
        }
        return false;
    }

    public void action(int id) {

    }

    public void render(int mouseX, int mouseY) {
        for(Button button : this.buttons) {
            button.render(mouseX, mouseY);
        }
    }

    public void tick() {}

    public void mouseClicked(float mouseX, float mouseY, int button) {
    }

    public void keyTyped(int key) {

    }



}
