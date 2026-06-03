package Shapes;

import java.awt.*;
import java.awt.geom.Path2D;

public class GFreeLine extends GShape{
    private Path2D.Double path;
    @Override
    public GFreeLine clone(){

        GFreeLine cloned = (GFreeLine) super.clone();

        if (geometry != null) {
            cloned.geometry = (Shape)((Path2D)geometry).clone();
        }

        return cloned;
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
    public void transfer(int x, int y) {

    }
}
