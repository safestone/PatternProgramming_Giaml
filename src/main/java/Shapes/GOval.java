package Shapes;

import Global.EAnchor;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class GOval extends GShape{
    public int startX, startY;
    public int endX, endY;
    private static final int MIN_SIZE = 20;

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
    public void transfer(int dx, int dy) {
        Ellipse2D oval = (Ellipse2D) geometry;

        oval.setFrame(oval.getX() + dx, oval.getY() + dy, oval.getWidth(), oval.getHeight());
    }

    @Override
    public void resize(EAnchor anchor, int dx, int dy) {
        Ellipse2D oval = (Ellipse2D) geometry;

        switch (anchor) {
            case eNW:
                if(oval.getWidth()-dx < MIN_SIZE || oval.getHeight()-dy < MIN_SIZE) return;
                oval.setFrame(oval.getX()+dx, oval.getY()+dy, oval.getWidth()-dx, oval.getHeight()-dy);
                break;

            case eNN:
                if(oval.getHeight()-dy < MIN_SIZE) return;
                oval.setFrame(oval.getX(), oval.getY()+dy, oval.getWidth(), oval.getHeight()-dy);
                break;

            case eNE:
                if(oval.getWidth()+dx < MIN_SIZE || oval.getHeight()-dy < MIN_SIZE) return;
                oval.setFrame(oval.getX(), oval.getY()+dy, oval.getWidth()+dx, oval.getHeight()-dy);
                break;

            case eWW:
                if(oval.getWidth()-dx < MIN_SIZE) return;
                oval.setFrame(oval.getX()+dx, oval.getY(), oval.getWidth()-dx, oval.getHeight());
                break;

            case eEE:
                if(oval.getWidth()+dx < MIN_SIZE) return;
                oval.setFrame(oval.getX(), oval.getY(), oval.getWidth()+dx, oval.getHeight());
                break;

            case eSW:
                if(oval.getWidth()-dx < MIN_SIZE || oval.getHeight()+dy < MIN_SIZE) return;
                oval.setFrame(oval.getX()+dx, oval.getY(), oval.getWidth()-dx, oval.getHeight()+dy);
                break;

            case eSS:
                if(oval.getHeight()+dy < MIN_SIZE) return;
                oval.setFrame(oval.getX(), oval.getY(), oval.getWidth(), oval.getHeight()+dy);
                break;

            case eSE:
                if(oval.getWidth()+dx < MIN_SIZE || oval.getHeight()+dy < MIN_SIZE) return;
                oval.setFrame(oval.getX(), oval.getY(), oval.getWidth()+dx, oval.getHeight()+dy);
                break;
        }
    }


}
