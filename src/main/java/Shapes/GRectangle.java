package Shapes;

import Global.EAnchor;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.PrintWriter;

public class GRectangle extends GShape{
    public int startX, startY;
    public int endX, endY;
    private static final int MIN_SIZE = 20;

    @Override
    public GRectangle clone() {
        GRectangle cloned = (GRectangle) super.clone();
        if(geometry != null){
            Rectangle2D rect = (Rectangle2D) geometry;
            cloned.geometry = new Rectangle2D.Double(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
        return cloned;
    }

    @Override
    public void setStart(int x, int y) {
        this.startX = x;
        this.startY = y;

        geometry = new Rectangle2D.Double(x, y, 1, 1);

    }

    @Override
    public void setEnd(int x, int y) {
        this.endX = x;
        this.endY = y;
        int rx = Math.min(startX, endX); //도형의 꼭짓점음 항상 최솟값이어야 함.
        int ry = Math.min(startY, endY);
        int rw = Math.abs(endX - startX); //widht 의 절대값
        int rh = Math.abs(endY - startY); //height의 절대값

        geometry = new Rectangle2D.Double(rx, ry, rw, rh);
    }

    @Override
    public void transfer(int dx, int dy) {
        Rectangle2D rect = (Rectangle2D) geometry;

        rect.setFrame(rect.getX() + dx, rect.getY() + dy, rect.getWidth(), rect.getHeight());

    }

    @Override
    public void resize(EAnchor anchor, int dx, int dy) {
        Rectangle2D rect = (Rectangle2D) geometry;

        switch (anchor) {
            case eNW:
                if(rect.getWidth()-dx < MIN_SIZE || rect.getHeight()-dy < MIN_SIZE) return;
                rect.setFrame(rect.getX()+dx, rect.getY()+dy, rect.getWidth()-dx, rect.getHeight()-dy);
                break;

            case eNN:
                if(rect.getHeight()-dy < MIN_SIZE) return;
                rect.setFrame(rect.getX(), rect.getY()+dy, rect.getWidth(), rect.getHeight()-dy);
                break;

            case eNE:
                if(rect.getWidth()+dx < MIN_SIZE || rect.getHeight()-dy < MIN_SIZE) return;
                rect.setFrame(rect.getX(), rect.getY()+dy, rect.getWidth()+dx, rect.getHeight()-dy);
                break;

            case eWW:
                if(rect.getWidth()-dx < MIN_SIZE) return;
                rect.setFrame(rect.getX()+dx, rect.getY(), rect.getWidth()-dx, rect.getHeight());
                break;

            case eEE:
                if(rect.getWidth()+dx < MIN_SIZE) return;
                rect.setFrame(rect.getX(), rect.getY(), rect.getWidth()+dx, rect.getHeight());
                break;

            case eSW:
                if(rect.getWidth()-dx < MIN_SIZE || rect.getHeight()+dy < MIN_SIZE) return;
                rect.setFrame(rect.getX()+dx, rect.getY(), rect.getWidth()-dx, rect.getHeight()+dy);
                break;

            case eSS:
                if(rect.getHeight()+dy < MIN_SIZE) return;
                rect.setFrame(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()+dy);
                break;

            case eSE:
                if(rect.getWidth()+dx < MIN_SIZE || rect.getHeight()+dy < MIN_SIZE) return;
                rect.setFrame(rect.getX(), rect.getY(), rect.getWidth()+dx, rect.getHeight()+dy);
                break;
        }
    }

    @Override
    public String getShapeName() {
        return "사각형";
    }

    @Override
    public void save(PrintWriter writer) {
        Rectangle2D.Double rectangle = (Rectangle2D.Double) geometry;

        writer.println("RECTANGLE|"+
                rectangle.x+"|"+
                rectangle.y+"|"+
                rectangle.width+"|"+
                rectangle.height+"|"+
                rotation+"|"+
                lineColor.getRGB()+"|"+
                (fillColor == null ? "null" : fillColor.getRGB()));
    }

    public void load(String[] tokens) {
        double x = Double.parseDouble(tokens[1]);
        double y = Double.parseDouble(tokens[2]);
        double width = Double.parseDouble(tokens[3]);
        double height = Double.parseDouble(tokens[4]);
        double rotation = Double.parseDouble(tokens[5]);
        int lineColor = Integer.parseInt(tokens[6]);
        int fillColor = Integer.parseInt(tokens[7]);

        geometry = new Rectangle2D.Double(x, y, width, height);
        this.rotation = rotation;
        setLineColor(new Color(lineColor));
        if (tokens[7].equals("null")) {
            setFillColor(null);
        } else {
            setFillColor(new Color(fillColor));
        }
    }
}
