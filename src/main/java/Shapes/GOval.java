package Shapes;

import java.awt.geom.Ellipse2D;

public class GOval extends GShape{
    public int startX, startY;
    public int endX, endY;

    @Override
    public GOval clone() {
        GOval cloned = (GOval) super.clone();

        if (geometry != null) {
            Ellipse2D oval = (Ellipse2D) geometry;
            cloned.geometry = new Ellipse2D.Double(oval.getX(), oval.getY(), oval.getWidth(), oval.getHeight());
        }
        return cloned;
    }


    @Override
    public void setStart(int x, int y) {
        this.startX = x;
        this. startY = y;

        geometry = new Ellipse2D.Double(x, y, 1, 1);
    }

    @Override
    public void setEnd(int x, int y) {
        this.endX = x;
        this. endY = y;

        int rx = Math.min(startX, endX);
        int ry = Math.min(startY, endY);
        int rw = Math.abs(endX - startX);
        int rh = Math.abs(endY - startY);

        geometry = new Ellipse2D.Double(rx, ry, rw, rh);
    }

    @Override
    public void transfer(int x, int y) {

    }
}
