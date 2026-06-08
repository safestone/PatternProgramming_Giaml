package Frames;

import Shapes.GShape;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Vector;

public class GLayerPanel extends JPanel {

    private DefaultListModel<GShape> model;
    private JList<GShape> layerList;

    private GDrawingPanel gDrawingPanel;

    private boolean adjusting = false;

    public GLayerPanel() {
        this.setLayout(new BorderLayout());

        model = new DefaultListModel<>();
        layerList = new JList<>(model);

        layerList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.getShapeName());

            if (isSelected) {
                label.setOpaque(true);
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            }

            return label;
        });

        layerList.addListSelectionListener(new LayerSelectionListener());

        this.add(new JLabel("Layers"), BorderLayout.NORTH);
        this.add(new JScrollPane(layerList), BorderLayout.CENTER);
    }

    public void setDrawingPanel(GDrawingPanel gDrawingPanel) {
        this.gDrawingPanel = gDrawingPanel;
    }

    public void setShapes(Vector<GShape> shapes) {
        model.clear();

        for (int i = shapes.size() - 1; i >= 0; i--) {
            model.addElement(shapes.get(i));
        }
    }

    public void setSelectedShape(GShape shape) {
        adjusting = true;
        layerList.setSelectedValue(shape, true);
        adjusting = false;
    }

    private class LayerSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (adjusting) return;
            if (e.getValueIsAdjusting()) return;

            GShape shape = layerList.getSelectedValue();

            if (gDrawingPanel != null) {
                gDrawingPanel.setSelectedShape(shape);
            }
        }
    }
}