package Shapes;

import Global.EAnchor;

import java.awt.*;
import java.awt.geom.*;
import java.io.PrintWriter;

public class GPolygon extends GShape {
    private static final int MIN_SIZE = 20;
    private Path2D.Double path;
    private int lastX;
    private int lastY;

    private int previewX;
    private int previewY;
    @Override
    public GPolygon clone(){
        GPolygon shape=(GPolygon)super.clone();

        if(this.path!=null){
            shape.path=(Path2D.Double)this.path.clone();
            shape.geometry=shape.path;
        }

        return shape;
    }
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
    public void transfer(int dx, int dy) {
        AffineTransform at = AffineTransform.getTranslateInstance(dx, dy);
        path.transform(at);
        geometry = path;
        lastX += dx;
        lastY += dy;
        previewX += dx;
        previewY += dy;
    }


    @Override
    public void setEnd(int x, int y){
        previewX=x;
        previewY=y;
    }

    @Override
    public void draw(Graphics2D g2d){
        g2d.setColor(lineColor);
        g2d.draw(getTransformedShape());
        g2d.draw(new Line2D.Double(lastX, lastY, previewX, previewY));
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

    @Override
    public String getShapeName() {
        return "폴리곤";
    }

    @Override
    public void save(PrintWriter writer){

        StringBuilder points = new StringBuilder();

        PathIterator iterator = path.getPathIterator(null);

        double[] coords = new double[6];

        while(!iterator.isDone()){

            int type = iterator.currentSegment(coords);

            if(type == PathIterator.SEG_MOVETO || type == PathIterator.SEG_LINETO){
                if(!points.isEmpty()){
                    points.append(";");
                }
                points.append(coords[0]).append(",").append(coords[1]);
            }
            iterator.next();
        }

        writer.println("POLYGON|"+
                rotation+"|"+
                lineColor.getRGB()+"|"+
                points);
    }

    @Override
    public void load(String[] tokens) {
        path = new Path2D.Double();

        double rotation = Double.parseDouble(tokens[1]);
        int lineColor = Integer.parseInt(tokens[2]);
        String pointString = tokens[3];

        this.rotation = rotation;
        setLineColor(new Color(lineColor));

        String[] points = pointString.split(";");  //points[0] = 20.0, 23.0 points[1] = 32.0, 50.2 처럼 저장

        for(int i=0;i<points.length;i++){

            String[] xy = points[i].split(",");

            double x = Double.parseDouble(xy[0]);

            double y = Double.parseDouble(xy[1]);

            if(i==0){
                path.moveTo(x,y);
            }else{
                path.lineTo(x,y);
            }
        }
        geometry = path;

    }
}