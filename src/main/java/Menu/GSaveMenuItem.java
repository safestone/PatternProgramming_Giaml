package Menu;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GSaveMenuItem extends JMenuItem {
    public GSaveMenuItem() {
        super("저장");
    }

    public void setSaveListener(ActionListener listener) {
        this.addActionListener(listener);
    }
}
