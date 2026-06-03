package Shapes;

import java.awt.geom.Line2D;

public class GLine extends GShape{
    public int startX, startY;
    public int endX, endY;

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
    public void transfer(int x, int y) {

    }

    @Override
    public boolean getContains(int x, int y) {
        Line2D line = (Line2D) geometry;
        return line.ptSegDist(x, y) < 5;
    }
}
