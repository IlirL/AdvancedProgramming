import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Shapes1Test {

    public static void main(String[] args) throws IOException {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}

class ShapesApplication {
    ArrayList<Canvas> canvas;

    public ShapesApplication() {
        this.canvas = new ArrayList<Canvas>();
    }

    public int readCanvases (InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        canvas =  br.lines()
                .map(line -> new Canvas(line))
                .collect(Collectors.toCollection(ArrayList::new));
        br.close();
        return getTotalNumberOfWindows();
    }

    public void printLargestCanvasTo (OutputStream outputStream)
    {
        Canvas largestCanvas = canvas.get(0);
        for(int i = 1; i<canvas.size(); i++)
            if(largestCanvas.getTotalPerimeter() < canvas.get(i).getTotalPerimeter())
                largestCanvas = canvas.get(i);

        PrintStream ps = new PrintStream(outputStream);
        ps.println(largestCanvas.toString());

    }
    public int getTotalNumberOfWindows()
    {
        int total = 0;
        for(int i = 0; i<canvas.size(); i++)
        {
            total+=canvas.get(i).getNumberOfWindows();
        }

        return total;
    }
}

class Canvas{
    private String id;
    private Integer[] windows;

    public Canvas(String id, Integer... windows) {
        this.id = id;
        this.windows = Arrays.copyOf(windows, windows.length);
    }

    public Canvas(String line)
    {
        String[] lines = line.split("\\s+");
        this.id = lines[0];
        windows = new Integer[lines.length -1];

        for(int i=1; i<lines.length; i++)
        {
            windows[i-1] = Integer.parseInt(lines[i]);
        }
    }

    public int getTotalPerimeter(){
        int perimeter = 0;
        for(int i = 0; i<windows.length; i++)
            perimeter+=windows[i]*4;

        return perimeter;
    }

    public int getNumberOfWindows()
    {
        return windows.length;
    }

    @Override
    public String toString() {
        return String.format("%s %d %d", id, windows.length, getTotalPerimeter());
    }
}