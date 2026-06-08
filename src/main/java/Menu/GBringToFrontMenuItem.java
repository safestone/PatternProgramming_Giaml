package Menu;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GBringToFrontMenuItem extends JMenuItem {
    public GBringToFrontMenuItem() {
        super("가장 앞으로");
    }

    public void setListener(ActionListener listener) {
        addActionListener(listener);
    }
}
