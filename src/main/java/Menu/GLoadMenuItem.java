package Menu;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GLoadMenuItem extends JMenuItem {
    public GLoadMenuItem() {
        super("불러오기");
    }

    public void setLoadListener(ActionListener listener) {
        this.addActionListener(listener);
    }
}
