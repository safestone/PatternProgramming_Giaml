package Frames;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GMenuBar extends JMenuBar {
    private JMenu fileMenu;

    private JMenuItem saveItem;
    private JMenuItem loadItem;

    public GMenuBar() {
        fileMenu = new JMenu("File");

        saveItem = new JMenuItem("저장");
        loadItem = new JMenuItem("불러오기");

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);

        this.add(fileMenu);
    }

    public void setSaveListener(ActionListener listener) {
        saveItem.addActionListener(listener);
    }

    public void setLoadListener(ActionListener listener) {
        loadItem.addActionListener(listener);
    }
}
