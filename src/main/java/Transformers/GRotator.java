package Transformers;

import Shapes.GShape;

import java.awt.*;

public class GRotator extends GTransformer {
    private double startAngle;

    public GRotator(GShape shape){
        super(shape);
    }

    @Override
    public void start(int x,int y){
        Point center=gShape.getCenter();
        startAngle=Math.atan2(
                y-center.y,
                x-center.x
        );
    }

    @Override
    public void keep(int x,int y){
        Point center=gShape.getCenter();

        double currentAngle=Math.atan2(
                y-center.y,
                x-center.x
        );

        double delta=
                Math.toDegrees(currentAngle-startAngle);

        gShape.rotate(delta);

        startAngle=currentAngle;
    }

    @Override
    public void end(int x,int y){}
}