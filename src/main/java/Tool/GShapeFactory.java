package Tool;

import Shapes.*;

public class GShapeFactory {
    public static GShape load(String line) {
        String [] tokens = line.split("\\|");
        String type = tokens[0];

        switch (type) {
            case "RECTANGLE" : {
                GRectangle gRectangle = new GRectangle();
                gRectangle.load(tokens);
                return gRectangle;
            }
            case "OVAL" : {
                GOval gOval = new GOval();
                gOval.load(tokens);
                return gOval;
            }
            case "LINE" : {
                GLine gLine = new GLine();
                gLine.load(tokens);
                return gLine;
            }
            case "FREELINE" : {
                GFreeLine gFreeLine = new GFreeLine();
                gFreeLine.load(tokens);
                return gFreeLine;
            }
            case "POLYGON" : {
                GPolygon gPolygon = new GPolygon();
                gPolygon.load(tokens);
                return gPolygon;
            }
            case "TEXT" : {
                GText gText = new GText();
                gText.load(tokens);
                return gText;
            }
        }

        return null;
    }
}
