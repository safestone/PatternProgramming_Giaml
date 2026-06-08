package Menu;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GSendToBackMenuItem extends JMenuItem {
    public GSendToBackMenuItem() {
        super("가장 뒤로");
    }

    public void setListener(ActionListener listener) {
        addActionListener(listener);
    }
}
