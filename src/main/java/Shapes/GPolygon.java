package Shapes;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class GPolygon extends GShape {

    private Path2D.Double path;

    private int lastX;
    private int lastY;

    private int previewX;
    private int previewY;

    @Override
    public void setStart(int x, int y){
        path=new Path2D.Double();

        path.moveTo(x,y);

        geometry=path;

        lastX=x;
        lastY=y;

        previewX=x;
        previewY=y;
    }

    @Override
    public void setCont(int x, int y){

        path.lineTo(x,y);

        lastX=x;
        lastY=y;

        geometry=path;
    }

    @Override
    public void setEnd(int x, int y){
        previewX=x;
        previewY=y;
    }

    @Override
    public void draw(Graphics2D g2d){
        g2d.draw(path);

        g2d.draw(new Line2D.Double(lastX, lastY, previewX, previewY)
        );
    }

    @Override
    public void transfer(int x, int y){

    }
}