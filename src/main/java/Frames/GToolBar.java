package Frames;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GToolBar extends JToolBar {
    private GToolType currenType;

    public GToolBar() {
        this.setFloatable(false);
        ButtonGroup buttonGroup = new ButtonGroup();
        BtnActionListener btnActionListener = new BtnActionListener();
        for(GToolType toolType : GToolType.values()) {
            JRadioButton button = new JRadioButton(toolType.name());
            button.setActionCommand(toolType.toString());
            button.addActionListener(btnActionListener);
            buttonGroup.add(button);
            this.add(button);
        }
        currenType = GToolType.Select;
        ((JRadioButton) this.getComponent(0)).setSelected(true);
    }
    public GToolType getCurrenType() {
        return currenType;
    }
    class BtnActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currenType = GToolType.valueOf(e.getActionCommand());
        }
    }
}

