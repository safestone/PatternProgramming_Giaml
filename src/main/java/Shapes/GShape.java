package Shapes;

import Global.EAnchor;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class GShape implements Cloneable{
    protected Shape geometry;
    private GAnchor anchor;
    protected double rotation = 0;

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
        AffineTransform old = graphics2D.getTransform();

        Rectangle bounds = geometry.getBounds();
        graphics2D.rotate(rotation, bounds.getCenterX(), bounds.getCenterY());

        graphics2D.draw(geometry);
        graphics2D.setTransform(old);
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
    public Rectangle getBounds() {
        return geometry.getBounds();
    }
    public abstract void setStart(int x, int y);
    public abstract void setEnd(int x, int y);
    public void setCont(int x, int y){};
    public abstract void transfer(int dx, int dy);
    public abstract void resize(EAnchor anchor, int dx, int dy);

    public void rotate(double angle) {
        rotation += angle;
    }
    protected Point getCenter() {
        Rectangle bounds = geometry.getBounds();
        return new Point(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
    }
}
