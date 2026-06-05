package Shapes;

import Global.EAnchor;

import javax.swing.*;
import java.awt.*;

public class GText extends GShape {

    private String text;
    private int fontSize=30;

    private int x;
    private int y;

    @Override
    public void setStart(int x,int y){

        this.x=x;
        this.y=y;

        text=JOptionPane.showInputDialog("Enter text");

        if(text==null){
            text="";
        }

        String size=JOptionPane.showInputDialog("Font Size");

        if(size!=null){
            try{
                fontSize=Integer.parseInt(size);
            }catch(NumberFormatException e){
                fontSize=30;
            }
        }

        FontMetrics fm=Toolkit.getDefaultToolkit().getFontMetrics(new Font("Arial",Font.PLAIN,fontSize));

        int width=Math.max(fm.stringWidth(text),20);

        geometry=new Rectangle(x, y-fm.getAscent(), width, fm.getHeight());
    }

    @Override
    public void draw(Graphics2D graphics2D){

        graphics2D.setFont(new Font("Arial", Font.PLAIN, fontSize));

        graphics2D.drawString(text,x,y);
    }

    @Override
    public void setEnd(int x,int y){
    }

    @Override
    public void transfer(int dx,int dy){

        x+=dx;
        y+=dy;

        Rectangle rect=(Rectangle)geometry;
        rect.translate(dx,dy);
    }

    @Override
    public void drawSelected(Graphics2D g2d){
        g2d.draw(geometry);
    }

    @Override
    public void resize(EAnchor anchor,int dx,int dy){
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;

        FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(new Font("Arial", Font.PLAIN, fontSize));
        int width = Math.max(fm.stringWidth(text), 20);
        geometry = new Rectangle(x, y-fm.getAscent(), width, fm.getHeight());
    }

    public String getText(){
        return text;
    }
    public void setText(String text){

        this.text=text;

        FontMetrics fm=Toolkit.getDefaultToolkit().getFontMetrics(new Font("Arial",Font.PLAIN,fontSize));

        int width=Math.max(fm.stringWidth(text),20);

        geometry=new Rectangle(x,y-fm.getAscent(),width,fm.getHeight());
    }
}