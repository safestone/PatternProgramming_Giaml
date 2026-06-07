package Shapes;

import Global.EAnchor;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.PrintWriter;

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

    @Override
    public String getShapeName() {
        return "원";
    }

    @Override
    public void save(PrintWriter writer) {
        Ellipse2D.Double oval = (Ellipse2D.Double) geometry;

        writer.println("OVAL|"+
                oval.x+"|"+
                oval.y+"|"+
                oval.width+"|"+
                oval.height+"|"+
                rotation+"|"+
                lineColor.getRGB()+"|"+
                (fillColor == null ? "null" : fillColor.getRGB()));
    }

    @Override
    public void load(String[] tokens) {
        double x = Double.parseDouble(tokens[1]);
        double y = Double.parseDouble(tokens[2]);
        double width = Double.parseDouble(tokens[3]);
        double height = Double.parseDouble(tokens[4]);
        double rotation = Double.parseDouble(tokens[5]);
        int lineColor = Integer.parseInt(tokens[6]);
        int fillColor = Integer.parseInt(tokens[7]);

        geometry = new Ellipse2D.Double(x, y, width, height);
        this.rotation = rotation;
        setLineColor(new Color(lineColor));
        if (tokens[7].equals("null")) {
            setFillColor(null);
        } else {
            setFillColor(new Color(fillColor));
        }
    }
}
