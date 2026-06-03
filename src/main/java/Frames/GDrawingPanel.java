package Frames;

import Global.EAnchor;
import Global.EDrawingType;
import Global.EShapeType;
import Shapes.GShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

public class GDrawingPanel extends JPanel {
    private GToolBar gToolBar;

    private EAnchor currentAnchor;

    private Vector<GShape> shapes;
    private GShape currentShape;
    private GShape selectedShape;

    private int prevX, prevY;

    public GDrawingPanel() {
        this.setBackground(Color.WHITE);

        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);

        this.currentAnchor = EAnchor.eNone;

        this.shapes = new Vector<>();
    }
    public void associaton(GToolBar gToolBar) {
        this.gToolBar = gToolBar;
    }

    public class MouseHandler implements MouseListener, MouseMotionListener {
        @Override
        public void mouseClicked(MouseEvent e) {

            if(gToolBar.getCurrentType().getDrawingType() == EDrawingType.eNPoint){
                if(e.getClickCount()==1){
                    mouseClickedOneTime(e);
                }else if(e.getClickCount()==2){
                    mouseClickedTwoTime(e);
                }
            }
        }
        public void mouseClickedOneTime(MouseEvent e) {
            if (currentShape == null) {
                startTransformer(e.getX(), e.getY());
            } else {
                contTransformer(e.getX(), e.getY());
            }
        }

        @Override
        public void mouseMoved(MouseEvent e){
            if(gToolBar.getCurrentType() == EShapeType.Polygon){
                if(currentShape != null){
                    currentShape.setEnd(e.getX(), e.getY());
                    repaint();
                }
            }
        }

        public void mouseClickedTwoTime(MouseEvent e) {
            endTransformer(e.getX(), e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e){
            if(gToolBar.getCurrentType()== EShapeType.Select){
                startTransformer(e.getX(), e.getY());
            }else if(gToolBar.getCurrentType().getDrawingType()== EDrawingType.e2Point){
                startTransformer(e.getX(), e.getY());
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(gToolBar.getCurrentType()== EShapeType.Select){
                keepTransformer(e.getX(), e.getY());
            }else if(gToolBar.getCurrentType().getDrawingType()== EDrawingType.e2Point){
                keepTransformer(e.getX(), e.getY());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(gToolBar.getCurrentType().getDrawingType() == EDrawingType.e2Point) {
                endTransformer(e.getX(), e.getY());
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }


    }

    private void contTransformer(int x, int y) {
        if(currentShape != null) {
            currentShape.setCont(x, y);
            repaint();
        }
    }

    private void startTransformer(int x, int y) {
        if(gToolBar.getCurrentType()== EShapeType.Select){

            selectedShape = null;
            currentAnchor = EAnchor.eNone;

            for(int i=shapes.size()-1; i>=0; i--){

                GShape shape = shapes.get(i);

                // 1. 앵커 먼저 검사
                EAnchor anchor = shape.getAnchor(x, y);

                if(anchor != EAnchor.eNone){
                    selectedShape = shape;
                    currentAnchor = anchor;
                    prevX = x;
                    prevY = y;
                    break;
                }

                // 2. 도형 내부 검사
                if(shape.getContains(x, y)){
                    selectedShape = shape;
                    currentAnchor = EAnchor.eNone;
                    prevX = x;
                    prevY = y;
                    break;
                }
            }
            System.out.println("선택된 도형: " + selectedShape + " 선택된 Anchor: " + currentAnchor);
            repaint();
        }
        else{
            currentShape=gToolBar.getCurrentType().getShape();
            if(currentShape!=null){
                currentShape.setStart(x,y);
                selectedShape=null;
            }
        }
    }
    private void keepTransformer(int x, int y){
        if(gToolBar.getCurrentType()== EShapeType.Select){
            if(selectedShape!=null){
                int dx = x - prevX;
                int dy = y - prevY;
                if (currentAnchor == EAnchor.eNone) {
                    selectedShape.transfer(dx, dy);
                } else if (currentAnchor == EAnchor.eRotate){
                } else {
                    selectedShape.resize(currentAnchor, dx, dy);
                }

                prevX = x;
                prevY = y;
                repaint();
            }
        }else if(currentShape!=null){
            currentShape.setEnd(x, y);
            repaint();
        }
    }
    private void endTransformer(int x, int y) {
        if (currentShape != null) {
            currentShape.setEnd(x, y);
            shapes.add(currentShape);
            selectedShape = currentShape;
            gToolBar.setSelectedIndex(0);
            currentShape = null;
            System.out.println("선택된 도형: " + selectedShape + " 선택된 Anchor: " + currentAnchor);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for(GShape shape : shapes){
            if(shape==selectedShape){
                shape.drawSelected(graphics2D);
            }
            shape.draw(graphics2D);
        }
        if(currentShape != null) {
            currentShape.draw(graphics2D);
        }
    }
}
