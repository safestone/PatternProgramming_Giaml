package Frames;

import Shapes.GShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

public class GDrawingPanel extends JPanel {
    private GToolBar gToolBar;
    private Vector<GShape> shapes;
    private GShape currentShape;
    private GShape selectedShape;

    private int prevX, prevY;

    public GDrawingPanel() {
        this.setBackground(Color.WHITE);

        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);

        this.shapes = new Vector<>();
    }
    public void associaton(GToolBar gToolBar) {
        this.gToolBar = gToolBar;
    }

    public class MouseHandler implements MouseListener, MouseMotionListener {
        @Override
        public void mouseClicked(MouseEvent e) {

            if(gToolBar.getCurrenType().getDrawingType() == EDrawingType.eNPoint){
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
            if(gToolBar.getCurrenType() == GToolType.Polygon){
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
            if(gToolBar.getCurrenType()==GToolType.Select){
                startTransformer(e.getX(), e.getY());
            }else if(gToolBar.getCurrenType().getDrawingType()==EDrawingType.e2Point){
                startTransformer(e.getX(), e.getY());
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(gToolBar.getCurrenType()==GToolType.Select){
                keepTransformer(e.getX(), e.getY());
            }else if(gToolBar.getCurrenType().getDrawingType()==EDrawingType.e2Point){
                keepTransformer(e.getX(), e.getY());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(gToolBar.getCurrenType().getDrawingType() == EDrawingType.e2Point) {
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
        if(gToolBar.getCurrenType()==GToolType.Select){
            selectedShape=null;
            for(int i=shapes.size()-1;i>=0;i--){
                if(shapes.get(i).getContains(x,y)){
                    selectedShape=shapes.get(i);
                    prevX=x;
                    prevY=y;
                    break;
                }
            }

            repaint();

        }else{
            currentShape=gToolBar.getCurrenType().getShape();

            if(currentShape!=null){
                currentShape.setStart(x,y);
                selectedShape=null;
            }
        }
    }
    private void keepTransformer(int x, int y){
        if(gToolBar.getCurrenType()==GToolType.Select){
            if(selectedShape!=null){
                selectedShape.transfer(x, y);
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
            currentShape = null;
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
                System.out.println(shape.getClass().getName());
            }
            shape.draw(graphics2D);
        }
        if(currentShape != null) {
            currentShape.draw(graphics2D);
        }
    }
}
