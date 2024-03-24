package twentyfour;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import twentyfour.WriteFile;
import static org.easymock.EasyMock.*;

import java.io.*;
import java.util.*;

public class WriteFileTest {
    @Test
    public void testOpenFile() {
        WriteFile writeFile = new WriteFile(new GameParameter(24, 5, 1, 10));
        writeFile.openFile();
        assertTrue(new File(writeFile.getFullFileName()).exists(), "File should be opened");
        writeFile.closeFile(); // Close the file after testing
    }

    @Test
    public void testWriteToFile() {
        WriteFile writeFile = new WriteFile(new GameParameter(24, 5, 1, 10));
        writeFile.openFile();
        writeFile.writeToFile("Test Data");
        writeFile.closeFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(writeFile.getFullFileName()))) {
            String line = reader.readLine();
            line = reader.readLine();

            assertEquals("Test Data", line);
        } catch (IOException e) {
            fail("Error reading file: " + e.getMessage());
        }
    }

    @Test
    public void testCloseFile() {
        WriteFile writeFile = new WriteFile(new GameParameter(24, 5, 1, 10));
        writeFile.openFile();
        writeFile.closeFile();
        //check that there was not an exception thrown
        Assertions.assertDoesNotThrow(() -> IOException.class);
    }


    @Test
    public void testReadDirectory() {
        WriteFile writeFile = new WriteFile(new GameParameter(24, 5, 1, 10));
        writeFile.readDirectory("datafiles");
        assertFalse(WriteFile.fileTable.isEmpty(), "File list should not be empty");
    }
}

