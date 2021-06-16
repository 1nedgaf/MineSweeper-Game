import client.Window;
import org.lwjgl.LWJGLUtil;

public class Start {

    public static void main(String[] args) {
        String path = "";
        switch (LWJGLUtil.getPlatform()) {
            case LWJGLUtil.PLATFORM_WINDOWS: {
                path = Start.class.getResource("/native/windows/").getPath();
            }
            break;

            case LWJGLUtil.PLATFORM_LINUX: {
                path = Start.class.getResource("/native/linux/").getPath();
            }
            break;

            case LWJGLUtil.PLATFORM_MACOSX: {
                path = Start.class.getResource("/native/macosx/").getPath();
            }
            break;
        }
        System.setProperty("org.lwjgl.librarypath", path);
        (new Window()).run();
    }
}
