package Menu;

import javax.swing.*;

public class GLayerMenu extends JMenu {
    private GBringToFrontMenuItem gBringToFrontMenuItem;
    private GBringFowardMenuItem gBringFowardMenuItem;
    private GSendBackwardMenuItem gSendBackwardMenuItem;
    private GSendToBackMenuItem gSendToBackMenuItem;

    public GLayerMenu() {
        super("Layer");

        gBringToFrontMenuItem = new GBringToFrontMenuItem();
        gBringFowardMenuItem = new GBringFowardMenuItem();
        gSendBackwardMenuItem = new GSendBackwardMenuItem();
        gSendToBackMenuItem = new GSendToBackMenuItem();

        this.add(gBringToFrontMenuItem);
        this.add(gBringFowardMenuItem);
        this.addSeparator();
        this.add(gSendBackwardMenuItem);
        this.add(gSendToBackMenuItem);
    }
    public GBringToFrontMenuItem getBringToFrontMenuItem() {
        return gBringToFrontMenuItem;
    }
    public GBringFowardMenuItem getBringFowardMenuItem() {
        return gBringFowardMenuItem;
    }
    public GSendBackwardMenuItem getSendBackwardMenuItem() {
        return gSendBackwardMenuItem;
    }
    public GSendToBackMenuItem getSendToBackMenuItem() {
        return gSendToBackMenuItem;
    }
}
