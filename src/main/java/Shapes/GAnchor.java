package Shapes;

import Global.EAnchor;

import java.awt.*;
import java.awt.geom.Ellipse2D;

class GAnchor {
    private static final int SIZE = 8;
    private static final int HIT_SIZE = 20;

    private Ellipse2D.Double[] anchors;

    public GAnchor() {
        anchors = new Ellipse2D.Double[EAnchor.values().length - 1];

        for (int i = 0; i < anchors.length; i++) {
            anchors[i] = new Ellipse2D.Double();
        }
    }

    public void draw(Graphics2D g2d) {
        for (Ellipse2D.Double anchor : anchors) {
            g2d.fill(anchor);
        }
    }

    public EAnchor getAnchor(int x, int y) {
        for (int i = 0; i < anchors.length; i++) {
            Rectangle hitBox = anchors[i].getBounds();
            hitBox.grow((HIT_SIZE - SIZE) / 2, (HIT_SIZE - SIZE) / 2);

            if (hitBox.contains(x, y)) {
                return EAnchor.values()[i];
            }
        }

        return EAnchor.eNone;
    }

    public void setPosition(Rectangle br) {
        int x = br.x;
        int y = br.y;
        int w = br.width;
        int h = br.height;

        anchors[EAnchor.eNW.ordinal()].setFrame(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
        anchors[EAnchor.eNN.ordinal()].setFrame(x + w / 2 - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
        anchors[EAnchor.eNE.ordinal()].setFrame(x + w - SIZE / 2, y - SIZE / 2, SIZE, SIZE);

        anchors[EAnchor.eWW.ordinal()].setFrame(x - SIZE / 2, y + h / 2 - SIZE / 2, SIZE, SIZE);
        anchors[EAnchor.eEE.ordinal()].setFrame(x + w - SIZE / 2, y + h / 2 - SIZE / 2, SIZE, SIZE);

        anchors[EAnchor.eSW.ordinal()].setFrame(x - SIZE / 2, y + h - SIZE / 2, SIZE, SIZE);
        anchors[EAnchor.eSS.ordinal()].setFrame(x + w / 2 - SIZE / 2, y + h - SIZE / 2, SIZE, SIZE);
        anchors[EAnchor.eSE.ordinal()].setFrame(x + w - SIZE / 2, y + h - SIZE / 2, SIZE, SIZE);

        anchors[EAnchor.eRotate.ordinal()].setFrame(x + w / 2 - SIZE / 2, y - 25, SIZE, SIZE);
    }
}
