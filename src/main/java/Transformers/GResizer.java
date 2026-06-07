package Transformers;

import Global.EAnchor;
import Shapes.GShape;

public class GResizer extends GTransformer {
    private EAnchor anchor;
    private int oldX;
    private int oldY;

    public GResizer(GShape shape, EAnchor anchor){
        super(shape);
        this.anchor=anchor;
    }

    @Override
    public void start(int x,int y){
        oldX=x;
        oldY=y;
    }

    @Override
    public void keep(int x,int y){
        int dx=x-oldX;
        int dy=y-oldY;

        gShape.resize(anchor,dx,dy);

        oldX=x;
        oldY=y;
    }

    @Override
    public void end(int x,int y){}
}