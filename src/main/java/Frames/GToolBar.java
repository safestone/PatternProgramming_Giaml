package Frames;

import Global.EShapeType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GToolBar extends JToolBar {
    private EShapeType currentType;

    public GToolBar() {
        this.setFloatable(false);
        ButtonGroup buttonGroup = new ButtonGroup();
        BtnActionListener btnActionListener = new BtnActionListener();
        for(EShapeType toolType : EShapeType.values()) {
            JRadioButton button = new JRadioButton(toolType.name());
            button.setActionCommand(toolType.toString());
            button.addActionListener(btnActionListener);
            buttonGroup.add(button);
            this.add(button);
        }
        currentType = EShapeType.Select;
        ((JRadioButton) this.getComponent(0)).setSelected(true);
    }
    public EShapeType getCurrentType() {
        return currentType;
    }

    public void setSelectedIndex(int i) {
        ((JRadioButton) this.getComponent(i)).setSelected(true);
        currentType = EShapeType.Select;
    }

    class BtnActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentType = EShapeType.valueOf(e.getActionCommand());
        }
    }
}

