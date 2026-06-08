package Menu;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GBringFowardMenuItem extends JMenuItem {
    public GBringFowardMenuItem() {
        super("앞으로");
    }

    public void setListener(ActionListener listener) {
        addActionListener(listener);
    }
}
