package DTO;

import Global.EAnchor;
import Shapes.GShape;

public class GShapeInfo {
    private final GShape gShape;
    private final EAnchor eAnchor;

    public GShapeInfo(GShape gShape, EAnchor eAnchor) {
        this.gShape = gShape;
        this.eAnchor = eAnchor;
    }

    public GShape getShape() {
        return gShape;
    }

    public EAnchor getAnchor() {
        return eAnchor;
    }
}
