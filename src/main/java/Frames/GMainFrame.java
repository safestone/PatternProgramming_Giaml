package Frames;

import javax.swing.*;
import java.awt.*;

public class GMainFrame extends JFrame {
    private GMenuBar gMenuBar;
    private GToolBar gToolBar;
    private GDrawingPanel gDrawingPanel;

    public GMainFrame() throws HeadlessException {
        super("Graphic Editor");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth()*8/10;
        int height = (int) screenSize.getHeight()*8/10;

        this.setSize(width, height);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.gMenuBar = new GMenuBar();
        this.gToolBar = new GToolBar();
        this.gDrawingPanel = new GDrawingPanel();
        this.gDrawingPanel.association(gToolBar);

        this.setJMenuBar(gMenuBar);
        this.add(gToolBar, BorderLayout.NORTH);
        this.add(gDrawingPanel, BorderLayout.CENTER);
    }
}
