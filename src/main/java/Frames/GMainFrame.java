package Frames;

import javax.swing.*;
import java.awt.*;

public class GMainFrame extends JFrame {
    private GMenuBar gMenuBar;
    private GToolBar gToolBar;
    private GDrawingPanel gDrawingPanel;
    private GPropertyPanel gPropertyPanel;

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
        this.gPropertyPanel = new GPropertyPanel();


        this.gDrawingPanel.association(gToolBar);
        this.gDrawingPanel.setPropertyPanel(gPropertyPanel);
        this.gPropertyPanel.setRepaintListener(
                () -> gDrawingPanel.repaint()
        );

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gDrawingPanel, gPropertyPanel);

        splitPane.setResizeWeight(0.8);

        this.setJMenuBar(gMenuBar);
        this.add(gToolBar, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
    }
}
