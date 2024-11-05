package text.to.numbers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SingleLineFile {
    public static int addNumbers(String filename) throws IOException, IllegalArgumentException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename));
             PrintWriter pr = new PrintWriter("wrong." + filename)) {
            String line = br.readLine();

            if (line == null) {
                throw new IllegalArgumentException("Empty file");
            }

            String[] parts = line.split(" ");
            int retval = 0;

            for (String part: parts) {
                try {
                    retval += Integer.parseInt(part);
                } catch (NumberFormatException e) {
                    pr.println("Invalid: " + part);
                }
            }

            return retval;
        }
    }
}