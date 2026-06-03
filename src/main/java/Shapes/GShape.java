package Shapes;

import Global.EAnchor;

import java.awt.*;

public abstract class GShape implements Cloneable{
    protected Shape geometry;
    private GAnchor anchor;

    public GShape() {
        anchor = new GAnchor();
    }

    @Override
    public GShape clone() {
        try {
            return (GShape) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    public void draw(Graphics2D graphics2D) {
        graphics2D.draw(geometry);
    }
    public boolean getContains(int x, int y) {
        return geometry.contains(x, y);
    }

    public EAnchor getAnchor(int x, int y) {
        return anchor.getAnchor(x, y);
    }
    public void drawSelected(Graphics2D g2d){
        Rectangle bounds = geometry.getBounds();
        g2d.draw(bounds);

        anchor.setPosition(bounds);
        anchor.draw(g2d);
    }

    public abstract void setStart(int x, int y);
    public abstract void setEnd(int x, int y);
    public void setCont(int x, int y){};
    public abstract void transfer(int dx, int dy);
    public abstract void resize(EAnchor anchor, int dx, int dy);
}
