package client;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import utils.RenderUtil;
import utils.Timer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Window {

    public Timer timer;
    public static SGL GL = Renderer.get();
    public static int width = 920, height = 580;
    public static boolean fullscreen;
    public static int x, y;

    public void run() {
        init();
        new Main(this);
        timer = new Timer(Main.instance.settingManager.getSetting("FrameRate").equals("Unlimited" ) ? 9999: Float.valueOf(Main.instance.settingManager.getSetting("FrameRate")));
        update();
        shutdown();
    }

    public void init() {
        Display.setTitle("untitled");
        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setInitialBackground(0.1F, 0.1F, 0.1F);
            Display.setIcon(new ByteBuffer[]{
                    RenderUtil.loadIcon(getClass().getResource("icon16.png")),
                    RenderUtil.loadIcon(getClass().getResource("icon32.png")),
            });
            Display.create();
            if (Display.isCreated()) {
                enter(width, height);
            }
        } catch (LWJGLException | IOException e) {
        }
    }

    public void enter(int width, int height) {
        GL.initDisplay(width, height);
        GL.enterOrtho(width, height);
    }

    public void shutdown() {
        Display.destroy();
        System.exit(0);
    }

    public void update() {
        while(!Display.isCloseRequested()) {
            timer.updateTimer();
            for (int j = 0; j < this.timer.elapsedTicks; ++j)
            {
                if(timer.ticksPerSecond != (Main.instance.settingManager.getSetting("FrameRate").equals("Unlimited" ) ? 9999: Float.valueOf(Main.instance.settingManager.getSetting("FrameRate")))) {
                    timer = new Timer(Main.instance.settingManager.getSetting("FrameRate").equals("Unlimited" ) ? 9999: Float.valueOf(Main.instance.settingManager.getSetting("FrameRate")));
                }
                Display.update();
                GL.glClear(GL.GL_COLOR_BUFFER_BIT);
                GL.glDisable(3553);
                Main.instance.update();
            }
        }
    }


}
