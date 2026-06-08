package Frames;

import Menu.GLayerMenu;
import Menu.GFileMenu;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GMenuBar extends JMenuBar {
    private GFileMenu gFileMenu;
    private GLayerMenu gLayerMenu;

    public GMenuBar() {
        gFileMenu = new GFileMenu();
        gLayerMenu = new GLayerMenu();

        this.add(gFileMenu);
        this.add(gLayerMenu);
    }
    public void setSaveListener(ActionListener listener){
        gFileMenu.getSaveMenuItem().setSaveListener(listener);
    }
    public void setLoadListener(ActionListener listener){
        gFileMenu.getLoadMenuItem().setLoadListener(listener);
    }
    public void setBringToFrontListener(ActionListener listener){
        gLayerMenu.getBringToFrontMenuItem().setListener(listener);
    }
    public void setBringFowardListener(ActionListener listener){
        gLayerMenu.getBringFowardMenuItem().setListener(listener);
    }
    public void setSendBackwardListener(ActionListener listener){
        gLayerMenu.getSendBackwardMenuItem().setListener(listener);
    }
    public void setSendToBackListener(ActionListener listener){
        gLayerMenu.getSendToBackMenuItem().setListener(listener);
    }
}
