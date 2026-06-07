package Transformers;

import Shapes.GShape;

public abstract class GTransformer {
    protected GShape gShape;

    public GTransformer(GShape gShape) {
        this.gShape = gShape;
    }

    public abstract void start(int x, int y);
    public abstract void keep(int x, int y);
    public abstract void end(int x, int y);
}
