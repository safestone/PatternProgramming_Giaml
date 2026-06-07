package Menu;

import javax.swing.*;

public class GFileMenu extends JMenu {
    private GSaveMenuItem gSaveMenuItem;
    private GLoadMenuItem gLoadMenuItem;

    public GFileMenu() {
        super("File");

        gSaveMenuItem = new GSaveMenuItem();
        gLoadMenuItem = new GLoadMenuItem();

        this.add(gSaveMenuItem);
        this.add(gLoadMenuItem);
    }

    public GSaveMenuItem getSaveMenuItem() {
        return gSaveMenuItem;
    }

    public GLoadMenuItem getLoadMenuItem() {
        return gLoadMenuItem;
    }
}
