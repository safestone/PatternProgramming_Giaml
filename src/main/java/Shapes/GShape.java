package Shapes;

import Global.EAnchor;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class GShape implements Cloneable{
    protected Shape geometry;

    protected double rotation = 0;
    private GAnchor anchor;


    public GShape() {
        anchor = new GAnchor();
    }

    @Override
    public GShape clone() {
        try {
            GShape cloned = (GShape) super.clone();
            cloned.anchor = new GAnchor();

            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    public void draw(Graphics2D graphics2D) {
        graphics2D.draw(getTransformedShape());
    }
    public boolean getContains(int x, int y) {
        return getTransformedShape().contains(x, y);
    }

    public EAnchor getAnchor(int x, int y) {
        return anchor.getAnchor(x, y);
    }
    public void drawSelected(Graphics2D g2d){
        Rectangle bounds = getTransformedShape().getBounds();
        g2d.draw(bounds);

        anchor.setPosition(bounds);
        anchor.draw(g2d);
    }
    public Shape getTransformedShape() {
        Rectangle bounds = geometry.getBounds();

        double cx = bounds.getCenterX();
        double cy = bounds.getCenterY();

        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(rotation), cx, cy);

        return at.createTransformedShape(geometry);
    }
    public double getRotation() {
        return rotation;
    }
    public Point getCenter(){

        Rectangle bounds = geometry.getBounds();

        return new Point(
                (int)bounds.getCenterX(),
                (int)bounds.getCenterY()
        );
    }

    public abstract void setStart(int x, int y);
    public abstract void setEnd(int x, int y);
    public void setCont(int x, int y){};
    public abstract void transfer(int dx, int dy);
    public abstract void resize(EAnchor anchor, int dx, int dy);

    public void rotate(double angle) {
        rotation += angle;
    }
}
