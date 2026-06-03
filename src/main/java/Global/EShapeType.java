package Global;

import Shapes.*;

public enum EShapeType {

    Select(null, null),

    Rectangle(new GRectangle(), EDrawingType.e2Point),

    Oval(new GOval(), EDrawingType.e2Point),

    Line(new GLine(), EDrawingType.e2Point),

    Polygon(new GPolygon(), EDrawingType.eNPoint),

    FreeLine(new GFreeLine(), EDrawingType.e2Point),

    Text(null, null);

    private GShape shape;
    private EDrawingType drawingType;

    EShapeType(
            GShape shape,
            EDrawingType drawingType
    ){
        this.shape=shape;
        this.drawingType=drawingType;
    }

    public GShape getShape(){

        if(shape==null){
            return null;
        }

        return shape.clone();
    }

    public EDrawingType getDrawingType(){
        return drawingType;
    }
}