package Transformers;

import Shapes.GShape;

public class GTranslator extends GTransformer{
    private int oldX;
    private int oldY;

    public GTranslator(GShape gShape) {
        super(gShape);
    }

    @Override
    public void start(int x,int y){
        oldX=x;
        oldY=y;
    }

    @Override
    public void keep(int x,int y){
        gShape.transfer(x-oldX,y-oldY);
        oldX=x;
        oldY=y;
    }

    @Override
    public void end(int x, int y){
    }
}
