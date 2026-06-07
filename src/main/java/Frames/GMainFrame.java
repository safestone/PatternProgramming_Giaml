package Frames;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

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

        gMenuBar.setSaveListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setFileFilter(new FileNameExtensionFilter("Graphic Files (*txt)", "txt"));
            int result = fileChooser.showSaveDialog(this);

            if(result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                if(!file.getName().toLowerCase().endsWith(".txt")){
                    file = new File(file.getAbsolutePath() + ".txt");
                }
                gDrawingPanel.save(file);
            }
        });

        gMenuBar.setLoadListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setFileFilter(new FileNameExtensionFilter("Graphic Files (*txt)", "txt"));
            int result = fileChooser.showSaveDialog(this);

            if(result == JFileChooser.APPROVE_OPTION) {
                gDrawingPanel.load(fileChooser.getSelectedFile());
            }
        });
    }
}
