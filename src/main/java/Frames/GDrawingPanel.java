package Frames;

import Global.EAnchor;
import Global.EDrawingType;
import Global.EShapeType;
import Shapes.GShape;
import Shapes.GText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class GDrawingPanel extends JPanel {
    private GToolBar gToolBar;

    private EAnchor currentAnchor;

    private Vector<GShape> shapes;
    private GShape currentShape;
    private GShape selectedShape;
    private GShape copiedShape;

    private int prevX, prevY;
    private double startAngle;
    private boolean isRotating = false;

    public GDrawingPanel() {
        this.setBackground(Color.WHITE);

        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);

        KeyHandler keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        this.currentAnchor = EAnchor.eNone;

        this.shapes = new Vector<>();
    }
    public void association(GToolBar gToolBar) {
        this.gToolBar = gToolBar;
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
                    if(anchor == EAnchor.eRotate){
                        Point center = shape.getCenter();
                        startAngle = Math.atan2(y-center.y, x-center.x);
                        isRotating = true;
                    }
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
        System.out.println("Resize 대상: "+selectedShape);
        if(gToolBar.getCurrentType()== EShapeType.Select){
            if(selectedShape!=null){
                int dx = x - prevX;
                int dy = y - prevY;
                if (currentAnchor == EAnchor.eNone) {
                    selectedShape.transfer(dx, dy);
                } else if(currentAnchor == EAnchor.eRotate){
                    Point center = selectedShape.getCenter();
                    double currentAngle = Math.atan2(y-center.y, x-center.x);
                    double delta = Math.toDegrees(currentAngle-startAngle);
                    selectedShape.rotate(delta);
                    startAngle = currentAngle;
                }else {
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
    private void copyShape() {
        if(selectedShape == null) {
            return;
        }

        this.copiedShape = selectedShape.clone();
    }

    private void pasteShape() {
        if(copiedShape == null) {
            return;
        }
        GShape shape = copiedShape.clone();
        shape.transfer(20, 20);
        shapes.add(shape);
        selectedShape=shape;
        repaint();
    }

    private void deleteShape() {
        if(selectedShape != null) {
            shapes.remove(selectedShape);
            selectedShape = null;
            repaint();
        }
    }
    public class KeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.isControlDown()&&e.getKeyCode() == KeyEvent.VK_C) {
                copyShape();
            }
            if(e.isControlDown()&&e.getKeyCode() == KeyEvent.VK_V) {
                pasteShape();
            }
            if(e.getKeyCode() == KeyEvent.VK_DELETE) {
                deleteShape();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }


    public class MouseHandler implements MouseListener, MouseMotionListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount()==2&&selectedShape instanceof GText gText){
                editText(gText);
            }
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
            requestFocusInWindow();

            if(gToolBar.getCurrentType()== EShapeType.Select){
                startTransformer(e.getX(), e.getY());
            }else if(gToolBar.getCurrentType().getDrawingType()== EDrawingType.e2Point){
                startTransformer(e.getX(), e.getY());
            } else if(gToolBar.getCurrentType().getDrawingType()== EDrawingType.eText) {
                startTransformer(e.getX(), e.getY());
                endTransformer(e.getX(), e.getY());
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
            isRotating = false;
            repaint();
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

    private void editText(GText gText) {
        String text=JOptionPane.showInputDialog("Edit Text",gText.getText());

        if(text!=null){
            gText.setText(text);
        }

        String size=JOptionPane.showInputDialog("Font Size",gText.getFontSize());

        if(size!=null){
            try{
                gText.setFontSize(Integer.parseInt(size));
            }catch(NumberFormatException ignored){}
        }

        repaint();
    }
    private void contTransformer(int x, int y) {
        if(currentShape != null) {
            currentShape.setCont(x, y);
            repaint();
        }
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        for(GShape shape : shapes){
            if(shape==selectedShape && !isRotating){
                shape.drawSelected(graphics2D);
            }
            shape.draw(graphics2D);
        }
        if(currentShape != null) {
            currentShape.draw(graphics2D);
        }
    }
}
