package Manager;

import DTO.GShapeInfo;
import Global.EAnchor;
import Shapes.GShape;

import java.util.Vector;

public class GShapeManager {
    private final Vector<GShape> shapes;
    private GShape selectedShape;
    private GShape copiedShape;

    public GShapeManager() {
        this.shapes=new Vector<>();
    }

    public Vector<GShape> getShapes() {
        return shapes;
    }

    public void setShapes(Vector<GShape> shapes) {
        this.shapes.clear();
        this.shapes.addAll(shapes);

        selectedShape=null;
        copiedShape=null;
    }

    public void addShape(GShape shape) {
        shapes.add(shape);
    }

    public void removeShape(GShape shape) {
        shapes.remove(shape);

        if(selectedShape==shape){
            selectedShape=null;
        }
    }

    public GShape getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(GShape shape) {
        this.selectedShape=shape;
    }

    public void copy() {
        if(selectedShape!=null){
            copiedShape=selectedShape.clone();
        }
    }

    public GShape paste() {
        if(copiedShape==null){
            return null;
        }

        GShape shape=copiedShape.clone();
        shape.transfer(20,20);

        shapes.add(shape);
        selectedShape=shape;

        return shape;
    }

    public void clear() {
        shapes.clear();
        selectedShape=null;
        copiedShape=null;
    }

    public GShape findShape(int x,int y){
        for(int i=shapes.size()-1;i>=0;i--){
            GShape shape=shapes.get(i);

            if(shape.getContains(x,y)){
                return shape;
            }
        }

        return null;
    }
    public GShapeInfo findShapeInfo(int x,int y){
        for(int i=shapes.size()-1;i>=0;i--){
            GShape shape=shapes.get(i);

            EAnchor anchor=shape.getAnchor(x,y);

            if(anchor!=EAnchor.eNone){
                return new GShapeInfo(shape,anchor);
            }

            if(shape.getContains(x,y)){
                return new GShapeInfo(shape, EAnchor.eNone);
            }
        }

        return null;
    }
    public void bringToFront(){
        if(selectedShape==null){
            return;
        }

        shapes.remove(selectedShape);
        shapes.add(selectedShape);
    }

    public void sendToBack(){
        if(selectedShape==null){
            return;
        }

        shapes.remove(selectedShape);
        shapes.add(0,selectedShape);
    }

    public void bringForward(){
        if(selectedShape==null){
            return;
        }

        int index=shapes.indexOf(selectedShape);

        if(index<shapes.size()-1){
            shapes.remove(index);
            shapes.add(index+1,selectedShape);
        }
    }
    public void sendBackward(){
        if(selectedShape==null){
            return;
        }

        int index=shapes.indexOf(selectedShape);

        if(index>0){
            shapes.remove(index);
            shapes.add(index-1,selectedShape);
        }
    }
}