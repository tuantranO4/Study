package text.to.numbers;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SingleLineFileTest {
    @Test
    void invalidFile() {
        try {
            SingleLineFile.addNumbers("this_file_does_not_exist.txt");
            fail();
        } catch (IOException e) {}
    }

    @Test
    void emptyFile() {
        try {
            SingleLineFile.addNumbers("empty.txt");
            fail();
        } catch (IOException e) {
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Empty file", e.getMessage());
        }
    }

    @Test
    void goodFile() {
        try {
            assertEquals(-117, SingleLineFile.addNumbers("numbers.txt"));
        } catch (Exception e) {
            fail();
        }
    }
}
