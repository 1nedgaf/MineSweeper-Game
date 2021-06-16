package setting;

import java.util.ArrayList;
import java.util.Arrays;

public class Setting {

    public String name;
    public ArrayList<String> selection;
    public int index;
    public Category category;

    public Setting(String name, Category category, String... selections) {
        this.name = name;
        selection = new ArrayList<String>();
        this.category = category;
        Arrays.stream(selections).forEach(s -> this.selection.add(s));
    }

    public String getValue() {
        return this.selection.get(index);
    }
}




