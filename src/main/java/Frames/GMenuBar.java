package Frames;

import Menu.GFileMenu;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GMenuBar extends JMenuBar {
    private GFileMenu gFileMenu;

    public GMenuBar() {
        gFileMenu = new GFileMenu();
        this.add(gFileMenu);
    }
    public void setSaveListener(ActionListener listener){
        gFileMenu.getSaveMenuItem().setSaveListener(listener);
    }

    public void setLoadListener(ActionListener listener){
        gFileMenu.getLoadMenuItem().setLoadListener(listener);
    }


}
