package Shapes;

import Global.EAnchor;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class GFreeLine extends GShape{
    private static final int MIN_SIZE = 20;
    private Path2D.Double path;
    @Override
    public GFreeLine clone(){

        GFreeLine shape=(GFreeLine)super.clone();

        if(this.path!=null){
            shape.path=(Path2D.Double)this.path.clone();
            shape.geometry=shape.path;
        }

        return shape;
    }
    @Override
    public void setStart(int x, int y) {
        path = new Path2D.Double();
        path.moveTo(x, y);
        geometry = path;
    }

    @Override
    public void setEnd(int x, int y) {
        path.lineTo(x, y);
        geometry = path;
    }

    @Override
    public void transfer(int dx, int dy) {
        AffineTransform at = AffineTransform.getTranslateInstance(dx, dy);
        path.transform(at);

        geometry = path;
    }

    @Override
    public void resize(EAnchor anchor, int dx, int dy) {

        Rectangle bounds = geometry.getBounds();

        if(bounds.width <= 0 || bounds.height <= 0) {
            return;
        }

        AffineTransform at = new AffineTransform();

        switch(anchor) {

            case eSE: {
                if(bounds.width + dx < MIN_SIZE || bounds.height + dy < MIN_SIZE) return;

                double sx = (bounds.width + dx) / (double) bounds.width;
                double sy = (bounds.height + dy) / (double) bounds.height;

                at.translate(bounds.x, bounds.y);
                at.scale(sx, sy);
                at.translate(-bounds.x, -bounds.y);
                break;
            }

            case eEE: {
                if(bounds.width + dx < MIN_SIZE || bounds.height + dy < MIN_SIZE) return;

                double sx = (bounds.width + dx) / (double) bounds.width;

                at.translate(bounds.x, bounds.y);
                at.scale(sx, 1);
                at.translate(-bounds.x, -bounds.y);
                break;
            }

            case eSS: {
                if(bounds.width + dx < MIN_SIZE || bounds.height + dy < MIN_SIZE) return;

                double sy = (bounds.height + dy) / (double) bounds.height;

                at.translate(bounds.x, bounds.y);
                at.scale(1, sy);
                at.translate(-bounds.x, -bounds.y);
                break;
            }

            case eNW: {
                if(bounds.width + dx < MIN_SIZE || bounds.height + dy < MIN_SIZE) return;

                double sx = (bounds.width - dx) / (double) bounds.width;
                double sy = (bounds.height - dy) / (double) bounds.height;

                at.translate(bounds.x + bounds.width, bounds.y + bounds.height);
                at.scale(sx, sy);
                at.translate(-(bounds.x + bounds.width), -(bounds.y + bounds.height));
                break;
            }

            case eNE: {
                if(bounds.width + dx < MIN_SIZE || bounds.height + dy < MIN_SIZE) return;

                double sx = (bounds.width + dx) / (double) bounds.width;
                double sy = (bounds.height - dy) / (double) bounds.height;

                at.translate(bounds.x, bounds.y + bounds.height);
                at.scale(sx, sy);
                at.translate(-bounds.x, -(bounds.y + bounds.height));
                break;
            }

            case eSW: {
                if(bounds.width + dx < MIN_SIZE || bounds.height + dy < MIN_SIZE) return;

                double sx = (bounds.width - dx) / (double) bounds.width;
                double sy = (bounds.height + dy) / (double) bounds.height;
                at.translate(bounds.x + bounds.width, bounds.y);
                at.scale(sx, sy);
                at.translate(-(bounds.x + bounds.width), -bounds.y);
                break;
            }

            case eWW: {
                if(bounds.width + dx < MIN_SIZE || bounds.height + dy < MIN_SIZE) return;

                double sx = (bounds.width - dx) / (double) bounds.width;
                at.translate(bounds.x + bounds.width, bounds.y);
                at.scale(sx, 1);
                at.translate(-(bounds.x + bounds.width), -bounds.y);
                break;
            }

            case eNN: {
                if(bounds.width + dx < MIN_SIZE || bounds.height + dy < MIN_SIZE) return;

                double sy = (bounds.height - dy) / (double) bounds.height;
                at.translate(bounds.x, bounds.y + bounds.height);
                at.scale(1, sy);
                at.translate(-bounds.x, -(bounds.y + bounds.height));
                break;
            }

            default:
                return;
        }

        path.transform(at);

        geometry = path;
    }

}
