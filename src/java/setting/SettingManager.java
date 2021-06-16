package setting;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingManager {

    public ArrayList<Setting> settings;

    public SettingManager(Setting... settings1) {
        settings = new ArrayList<>();
        Arrays.stream(Arrays.stream(settings1).toArray()).forEach(s -> this.settings.add((Setting) s));
    }

    public String getSetting(String setting) {
        for(Setting setting1 : this.settings) {
            if(setting1.name.equalsIgnoreCase(setting)) {
                return setting1.getValue();
            }
        }
        return "";
    }

}
