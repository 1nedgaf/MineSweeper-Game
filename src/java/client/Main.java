package client;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import screen.Screen;
import setting.Category;
import setting.Setting;
import setting.SettingManager;
import utils.RenderUtil;
import utils.Timer;


public class Main {

    public int ticks;
    public static Main instance;
    private final Timer timer;
    public Screen screen;
    public Window window;
    private Image image;
    public SettingManager settingManager;

    public Main(Window window) {
        settingManager = new SettingManager(new Setting("FrameRate", Category.Visual, "15", "60", "120", "240", "360", "Unlimited"), new Setting("Exit", Category.System, "No", "Yes"));
        try {
            image = new Image(getClass().getResource("icon16.png").getPath());
        } catch (SlickException e) {
        }
        this.window = window;
        screen = new Screen();
        instance = this;
        timer = new Timer(20.0F);
    }

    public void update() {
        timer.updateTimer();
        for (int j = 0; j < this.timer.elapsedTicks; ++j)
        {
            this.runTick();
        }
        render();

        if(this.settingManager.getSetting("Exit").equals("Yes")) {
            this.window.shutdown();
        }
    }

    public void runTick() {
        ticks++;
        if(screen != null) {
            try
            {
                screen.handleInput();
            }
            catch (Throwable throwable1) {
            }
            screen.onTick();
        }
    }

    public void render() {
        RenderUtil.drawQuad(0, 0, Display.getWidth(), Display.getHeight(), new Color(0xFF000000));
        RenderUtil.drawQuad(1, 1, Display.getWidth() -1 , Display.getHeight()-3, new Color(0xFF111111));
        if(screen != null) {
            screen.renderScreen();
        }
    }

    public static long getSystemTime()
    {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }


}
