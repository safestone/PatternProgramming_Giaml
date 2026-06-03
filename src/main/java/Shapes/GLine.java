package Shapes;

import Global.EAnchor;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class GLine extends GShape{
    public int startX, startY;
    public int endX, endY;
    private static final int ANCHOR_SIZE = 8;
    @Override
    public GLine clone() {
        GLine cloned = (GLine) super.clone();
        if (geometry != null) {
            Line2D line = (Line2D) geometry;
            cloned.geometry = new Line2D.Double(line.getX1(), line.getY1(), line.getX2(), line.getY2());
        }
        return cloned;
    }

    @Override
    public void setStart(int x, int y) {
        this.startX = x;
        this.startY = y;

        geometry = new Line2D.Double(startX, startY, 1, 1);
    }

    @Override
    public void setEnd(int x, int y) {
        this.endX = x;
        this.endY = y;

        geometry = new Line2D.Double(startX, startY, endX, endY);
    }

    @Override
    public void transfer(int dx, int dy) {
        Line2D line = (Line2D) geometry;

        line.setLine(line.getX1() + dx, line.getY1() + dy, line.getX2() + dx, line.getY2() + dy);
    }

    @Override
    public void drawSelected(Graphics2D g2d) {

        Line2D line = (Line2D) geometry;

        int size = 8;

        g2d.fillRect((int)line.getX1()-size/2, (int)line.getY1()-size/2, size, size);
        g2d.fillRect((int)line.getX2()-size/2, (int)line.getY2()-size/2, size, size);
    }

    @Override
    public void resize(EAnchor anchor, int dx, int dy) {

        Line2D line = (Line2D) geometry;

        switch(anchor){

            case eNW:
                line.setLine(line.getX1()+dx, line.getY1()+dy, line.getX2(), line.getY2());
                break;

            case eSE:
                line.setLine(line.getX1(), line.getY1(), line.getX2()+dx, line.getY2()+dy);
                break;
        }
    }

    @Override
    public boolean getContains(int x, int y) {
        Line2D line = (Line2D) geometry;
        return line.ptSegDist(x, y) < 5;
    }

    @Override
    public EAnchor getAnchor(int x, int y) {

        Line2D line = (Line2D) geometry;

        Rectangle start = new Rectangle((int)line.getX1()-ANCHOR_SIZE/2, (int)line.getY1()-ANCHOR_SIZE/2, ANCHOR_SIZE, ANCHOR_SIZE);
        Rectangle end = new Rectangle((int)line.getX2()-ANCHOR_SIZE/2, (int)line.getY2()-ANCHOR_SIZE/2,  ANCHOR_SIZE, ANCHOR_SIZE);

        if(start.contains(x,y))
            return EAnchor.eNW;

        if(end.contains(x,y))
            return EAnchor.eSE;

        return EAnchor.eNone;
    }
}
