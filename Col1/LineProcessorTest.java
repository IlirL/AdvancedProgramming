import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class LineProcessorTest {
    public static void main(String[] args) {
        LineProcessor lineProcessor = new LineProcessor();

        try {
            lineProcessor.readLines(System.in, System.out, 'a');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
class LineProcessor
{
    void readLines (InputStream is, OutputStream os, char c) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader((is)));

        int maxLength = -1;
        String saveString = new String();
        String line = new String();
        while ((line = br.readLine()) != null)
        {

            int tempLength = 0;
            for(int i = 0; i<line.length(); i++)
            {
                if(line.toLowerCase().charAt(i) == c)
                    tempLength++;
            }

            if(maxLength<=tempLength)
                {
                    maxLength = tempLength;
                    saveString = line;
                }
        }
        br.close();

    PrintWriter pw = new PrintWriter(os);
    pw.println(saveString);
    pw.close();
    }
}