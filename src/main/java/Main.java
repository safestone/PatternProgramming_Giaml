import Frames.GMainFrame;
import Shapes.GShape;
import Tool.GShapeFactory;

public class Main {
    public static void main(String[] args) {
        String line =
                "RECTANGLE|10|20|100|50|30|-16777216|-65536";

        GShape shape =
                GShapeFactory.load(line);

        System.out.println(shape);

        GMainFrame gMainFrame = new GMainFrame();
        gMainFrame.setVisible(true);
    }
}
