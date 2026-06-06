package Shapes;

import Global.EAnchor;

import java.awt.geom.Rectangle2D;

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


}
