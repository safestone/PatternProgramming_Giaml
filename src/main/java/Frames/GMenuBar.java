package Frames;

import javax.swing.*;

public class GMenuBar extends JMenuBar {
    private JMenu fileMenu;

    public GMenuBar() {
        fileMenu = new JMenu("File");
        this.add(fileMenu);
    }
}
