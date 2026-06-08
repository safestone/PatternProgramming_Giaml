package Frames;

import DTO.GShapeInfo;
import Global.EAnchor;
import Global.EDrawingType;
import Global.EShapeType;
import Manager.GShapeManager;
import Shapes.GShape;
import Shapes.GText;
import Tool.GFileManager;
import Transformers.GResizer;
import Transformers.GRotator;
import Transformers.GTransformer;
import Transformers.GTranslator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;

public class GDrawingPanel extends JPanel {
    private GToolBar gToolBar;
    private GPropertyPanel gPropertyPanel;
    private GTransformer transformer;
    private GLayerPanel gLayerPanel;
    private GShapeManager shapeManager;

    private EAnchor currentAnchor;

    private GShape currentShape;


    private boolean isRotating = false;

    public GDrawingPanel() {
        this.setBackground(Color.WHITE);

        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);

        KeyHandler keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        this.shapeManager = new GShapeManager();
        this.currentAnchor = EAnchor.eNone;
    }
    public void association(GToolBar gToolBar) {
        this.gToolBar = gToolBar;
    }
    public void setPropertyPanel(GPropertyPanel gPropertyPanel) {
        this.gPropertyPanel = gPropertyPanel;
    }
    public void setLayerPanel(GLayerPanel gLayerPanel) {
        this.gLayerPanel = gLayerPanel;
    }
    private void startTransformer(int x, int y) {
        if(gToolBar.getCurrentType()==EShapeType.Select){

            setSelectedShape(null);
            currentAnchor=EAnchor.eNone;

            GShapeInfo shapeInfo=shapeManager.findShapeInfo(x,y);

            if(shapeInfo!=null){
                GShape shape=shapeInfo.getShape();
                EAnchor anchor=shapeInfo.getAnchor();

                setSelectedShape(shape);
                currentAnchor=anchor;

                if(anchor==EAnchor.eRotate){
                    transformer=new GRotator(shape);
                    isRotating=true;
                }else if(anchor!=EAnchor.eNone){
                    transformer=new GResizer(shape,anchor);
                }else{
                    transformer=new GTranslator(shape);
                }

                transformer.start(x,y);
            }

            System.out.println("선택된 도형: "+shapeManager.getSelectedShape()+" 선택된 Anchor: "+currentAnchor);
            repaint();
        } else{
            currentShape=gToolBar.getCurrentType().getShape();
            if(currentShape!=null){
                currentShape.setStart(x,y);
                setSelectedShape(null);
            }
        }
    }
    private void keepTransformer(int x,int y){
        if(gToolBar.getCurrentType()==EShapeType.Select){
            if(transformer!=null){
                transformer.keep(x,y);
                repaint();
            }
        }else if(currentShape!=null){
            currentShape.setEnd(x,y);
            repaint();
        }
    }
    private void endTransformer(int x,int y){
        if(transformer!=null){
            transformer.end(x,y);
            transformer=null;
        }
        if(currentShape!=null){
            currentShape.setEnd(x,y);

            shapeManager.addShape(currentShape);
            refreshLayerPanel();

            setSelectedShape(currentShape);
            gToolBar.setSelectedIndex(0);
            currentShape=null;
            repaint();
        }
    }
    private void contTransformer(int x, int y) {
        if(currentShape != null) {
            currentShape.setCont(x, y);
            repaint();
        }
    }
    private void copyShape() {
        shapeManager.copy();
    }

    private void pasteShape() {
        GShape shape=shapeManager.paste();

        if(shape!=null){
            refreshLayerPanel();
            setSelectedShape(shape);
        }
    }

    private void deleteShape() {
        GShape shape=shapeManager.getSelectedShape();

        if(shape!=null){
            shapeManager.removeShape(shape);
            refreshLayerPanel();
            setSelectedShape(null);
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
    public void save(File file) {
        GFileManager fileManager = new GFileManager();
        fileManager.save(file, shapeManager.getShapes());
    }
    public void load(File file) {
        GFileManager fileManager=new GFileManager();

        shapeManager.setShapes(fileManager.load(file));

        setSelectedShape(null);
        currentShape=null;

        refreshLayerPanel();
        repaint();
    }
    public void setSelectedShape(GShape shape) {
        shapeManager.setSelectedShape(shape);

        if(gPropertyPanel!=null){
            gPropertyPanel.setShape(shape);
        }

        if(gLayerPanel!=null){
            gLayerPanel.setSelectedShape(shape);
        }

        repaint();
    }
    public void refreshLayerPanel() {
        if(gLayerPanel!=null) {
            gLayerPanel.setShapes(shapeManager.getShapes());
        }
    }
    public void bringToFront(){
        shapeManager.bringToFront();
        refreshLayerPanel();
        repaint();
    }

    public void sendToBack(){
        shapeManager.sendToBack();
        refreshLayerPanel();
        repaint();
    }

    public void bringForward(){
        shapeManager.bringForward();
        refreshLayerPanel();
        repaint();
    }

    public void sendBackward(){
        shapeManager.sendBackward();
        refreshLayerPanel();
        repaint();
    }
    //이벤트 핸들러
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
            if(e.getClickCount()==2&&shapeManager.getSelectedShape() instanceof GText gText){
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D=(Graphics2D)g;

        for(GShape shape : shapeManager.getShapes()){
            if(shape==shapeManager.getSelectedShape() && !isRotating){
                shape.drawSelected(graphics2D);
            }

            shape.draw(graphics2D);
        }

        if(currentShape!=null){
            currentShape.draw(graphics2D);
        }
    }
}
