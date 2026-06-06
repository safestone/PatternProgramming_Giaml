package Frames;

import Shapes.GFreeLine;
import Shapes.GLine;
import Shapes.GShape;
import Shapes.GText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GPropertyPanel extends JPanel {
    private Runnable repaintListener;
    private GShape selectedShape;

    private JLabel typeLabel;
    private JButton lineColorButton;
    private JButton fillColorButton;

    public GPropertyPanel() {
        this.setLayout(new GridLayout(0,2,5,5));
        typeLabel = new JLabel("선택 없음.");
        lineColorButton = new JButton("선 색상");
        fillColorButton = new JButton("색 채우기");

        lineColorButton.addActionListener(new LineColorButtonListener());
        fillColorButton.addActionListener(new FillColorButtonListener());

        this.add(new JLabel("대상"));
        this.add(typeLabel);

        this.add(new JLabel("선 색상"));
        this.add(lineColorButton);

        this.add(new JLabel("색 채우기"));
        this.add(fillColorButton);
    }


    public void setShape(GShape gShape) {
        this.selectedShape = gShape;

        if(gShape == null) {
            typeLabel.setText("선택 없음.");
            fillColorButton.setEnabled(false);
        } else {
            typeLabel.setText(gShape.getShapeName());
            if(gShape instanceof GLine || gShape instanceof GFreeLine || gShape instanceof GText){
                fillColorButton.setEnabled(false);
            }else{
                fillColorButton.setEnabled(true);
            }
        }
    }
    public void setRepaintListener(Runnable repaintListener) {
        this.repaintListener = repaintListener;
    }

    private class LineColorButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(selectedShape == null) {
                return;
            }

            Color color = JColorChooser.showDialog(GPropertyPanel.this, "색상 선택", selectedShape.getLineColor());

            if(color != null) {
                selectedShape.setLineColor(color);
                if(repaintListener != null) {
                    repaintListener.run();
                }
            }
        }
    }

    private class FillColorButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(selectedShape == null) {
                return;
            }
            Color color = JColorChooser.showDialog(GPropertyPanel.this, "색상 선택", selectedShape.getFillColor());
            if(color != null) {
                selectedShape.setFillColor(color);
                if(repaintListener != null) {
                    repaintListener.run();
                }
            }
        }
    }
}
