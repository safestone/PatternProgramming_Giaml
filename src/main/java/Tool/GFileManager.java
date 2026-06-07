package Tool;

import Shapes.GShape;

import java.io.*;
import java.util.Vector;

public class GFileManager {
        public void save(File file, Vector<GShape> shapes) {
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(file));
                for (GShape shape : shapes) {
                    shape.save(writer);
                }

                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Vector<GShape> load(File file) {
            Vector<GShape> shapes = new Vector<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    GShape shape = GShapeFactory.load(line);
                    if(shape != null){
                        shapes.add(shape);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return shapes;
        }
}
