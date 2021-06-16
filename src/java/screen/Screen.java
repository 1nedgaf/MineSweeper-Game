package screen;

import client.Main;
import client.Window;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import screen.screens.MainMenu;
import screen.screens.OptionScreen;
import utils.*;
import utils.effect.Snow;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUseProgram;


public class Screen {

    public ArrayList<Long> frames;
    public ArrayList<Snow> snows;
    private int eventButton;
    private long lastMouseEvent;
    private int clickPositionX, clickPositionY;
    boolean dragging;
    public FontRenderer fontRenderer;
    public FontRenderer fontRendererSmall;
    public FontRenderer bigFontRenderer;
    public ScreenBase currentScreen;
    public OptionScreen optionScreen;
    private GLSLRender glslShader;
    long startTime;

    public void renderGLSL(int mouseX, int mouseY) {
        this.glslShader.useShader(Display.getWidth(), Display.getHeight(), mouseX, mouseY, (System.currentTimeMillis() - startTime) / 1000f);
        glBegin(GL_QUADS);
        glVertex2f(-1f, -1f);
        glVertex2f(-1f, 1f);
        glVertex2f(1f, 1f);
        glVertex2f(1f, -1f);
        glEnd();
        glUseProgram(0);
    }

    public Screen() {
        this.currentScreen = (new MainMenu());
        startTime = System.currentTimeMillis();
        try {
            this.glslShader = new GLSLRender("/wallpaper.fsh");
        } catch (IOException e) {
            // End execution
            throw new IllegalStateException(e);
        }
        fontRenderer = new FontRenderer(new Font("Consolas", Font.BOLD, 18), true);
        fontRendererSmall = new FontRenderer(new Font("Consolas", Font.BOLD, 14), true);
        bigFontRenderer = new FontRenderer(new Font("Arial", Font.BOLD, 38), true);
        frames = new ArrayList<>();
        this.snows = new ArrayList<>();
    }

    public void setScreen(ScreenBase screenBase) {
        this.currentScreen = screenBase;
    }

    public void handleInput() throws IOException
    {
        if (Mouse.isCreated())
        {
            while (Mouse.next())
            {
                this.handleMouseInput();
            }
        }

        if (Keyboard.isCreated())
        {
            while (Keyboard.next())
            {
                this.handleKeyboardInput();
            }
        }
    }

    public void handleMouseInput() throws IOException
    {
        int i = Mouse.getEventX() * Display.getWidth() /  Display.getWidth();
        int j = Display.getHeight() - Mouse.getEventY() *  Display.getHeight() /  Display.getHeight() - 1;
        int k = Mouse.getEventButton();

        if (Mouse.getEventButtonState())
        {
            this.eventButton = k;
            this.lastMouseEvent = Main.getSystemTime();
            this.mouseClicked(i, j, this.eventButton);
        }
        else if (k != -1)
        {
            this.eventButton = -1;
            this.mouseReleased(i, j, k);
        }
        else if (this.eventButton != -1 && this.lastMouseEvent > 0L)
        {
            long l = Main.getSystemTime() - this.lastMouseEvent;
            this.mouseClickMove(i, j, this.eventButton, l);
        }

    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if(optionScreen == null && mouseButton == 0 && this.currentScreen.button(mouseX, mouseY)) {
            return;
        }
        if(optionScreen != null && mouseButton == 0 && this.optionScreen.button(mouseX, mouseY)) {
            return;
        }
        if(mouseButton == 0 && (!Window.fullscreen)) {
            boolean hovered = MouseUtil.isMouseOnWindow() && MouseUtil.getHover(Display.getWidth() - 40, 0, Display.getWidth(), 30, Mouse.getEventX(), Display.getHeight()- Mouse.getEventY());
            if(hovered ) {
                Main.instance.window.shutdown();
            }
            clickPositionX = mouseX;
            clickPositionY = mouseY;
        }

        if(optionScreen == null && currentScreen != null) {
            this.currentScreen.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if(optionScreen != null) {
            this.optionScreen.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int clickedMouseButton)
    {
        if(clickedMouseButton == 0) {
            dragging = false;
        }
    }

    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        if(clickedMouseButton == 0) {
            dragging = true;
        }
    }

    public void handleKeyboardInput() throws IOException
    {
        final char c = Keyboard.getEventCharacter();
        final int k = Keyboard.getEventKey();
        if (Keyboard.getEventKeyState() || (k == 0 && Character.isDefined(c))) {
            this.keyTyped(c, k);
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if(keyCode == Keyboard.KEY_ESCAPE) {
            if(optionScreen == null) {
                optionScreen = (new OptionScreen(this.currentScreen));
                return;
            }
        }
        if(keyCode == 87) {
            try {
                if(!Main.instance.window.fullscreen) {
                    Display.setLocation(0, 0);
                    Display.setDisplayMode(new DisplayMode(1920, 1080));
                    Main.instance.window.enter(1920, 1080);
                    Main.instance.window.fullscreen = true;
                } else {
                    Display.setDisplayMode(new DisplayMode(Main.instance.window.width, Main.instance.window.height));
                    Display.setLocation(Window.x, Window.y);
                    Main.instance.window.enter(Main.instance.window.width, Main.instance.window.height);
                    Main.instance.window.fullscreen = false;
                }
                this.currentScreen.refresh();
            } catch (LWJGLException e) {

            }
        }
        if(this.currentScreen != null) {
            currentScreen.keyTyped(keyCode);
        }
        if(this.optionScreen != null) {
            optionScreen.keyTyped(keyCode);
        }

    }

    public void renderScreen() {
        int mouseX = Mouse.getEventX() * Display.getWidth() /  Display.getWidth();
        int mouseY = Display.getHeight() - Mouse.getEventY() *  Display.getHeight() /  Display.getHeight() - 1;
        frames.add(System.currentTimeMillis());
        for(int i = 0; i < frames.size(); i++) {
            Long time = frames.get(i);
            if(System.currentTimeMillis() - time >= 1000) {
                frames.remove(i);
                i--;
            }
        }
        if(dragging && !Window.fullscreen) {
            int x = (int)MouseInfo.getPointerInfo().getLocation().getX();
            int y = (int)MouseInfo.getPointerInfo().getLocation().getY();
            Display.setLocation(x - clickPositionX, y - clickPositionY);
            Window.x = x - clickPositionX;
            Window.y = y - clickPositionY;
        }

        if(this.currentScreen != null) {
            currentScreen.render(mouseX, mouseY);
        }
        if(optionScreen != null) {
            optionScreen.render(mouseX, mouseY);
        }

        this.snows.stream().forEach(Snow::draw);
        if(!Window.fullscreen) {
            RenderUtil.drawQuad(0, 0, Display.getWidth(), 30, new Color(0xFF222222));
            for(int i = 0; i < Display.getWidth(); i+=3) {
                int color = (java.awt.Color.HSBtoRGB((float)(Main.instance.ticks / 70.0D + Math.sin(-i / 1000D)) % 1.0F, 0.8882353F, 1));
                RenderUtil.drawQuad(0 + i, 30, 0 + i + 3, 31, new Color(color));
            }
            fontRenderer.drawStringWithShadow(Display.getTitle(), Display.getWidth() / 2 - (this.fontRenderer.getWidth(Display.getTitle()) / 2), 7, new Color(0x00FFFF));
            boolean hovered = MouseUtil.getHover(Display.getWidth() - 30, 3, Display.getWidth() - 7, 26, Mouse.getEventX(), Display.getHeight()- Mouse.getEventY());
            //RenderUtil.drawGradiantQuad(Display.getWidth() - 40, 0, Display.getWidth(), 30, hovered ? new Color(0xFFAA3333) : new Color(0xFFAA0000), new Color(0xFF550000));
            fontRenderer.drawStringWithShadow("x", Display.getWidth() - 20, 5, hovered ? new Color(0xFFAA7777) : new Color(0xFFAA0000));
        }
        //fontRenderer.drawStringWithShadow("FPS:" + (int)(frames.size()), 2, 35, new Color(-1));
    }


    public void onTick() {
        if(MathHelper.getRandom(0, 5) >= 4) {
            float times = MathHelper.getRandom(0, 3);
            if(times > 0) {
                for(int i = 0; i < times; i++) {
                    snows.add(new Snow(Display.getWidth()));
                }
            }
        }
        //this.snows.stream().forEach(Snow::onTick);
        for(int i = 0; i < snows.size(); i++) {
            Snow snow = snows.get(i);
            if(snow.getY() > Display.getHeight()) {
                this.snows.remove(i);
                i--;
            }
        }
    }
}
