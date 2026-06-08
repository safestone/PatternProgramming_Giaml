package Menu;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GSendBackwardMenuItem extends JMenuItem {
    public GSendBackwardMenuItem() {
        super("뒤로");
    }

    public void setListener(ActionListener listener) {
        addActionListener(listener);
    }
}
