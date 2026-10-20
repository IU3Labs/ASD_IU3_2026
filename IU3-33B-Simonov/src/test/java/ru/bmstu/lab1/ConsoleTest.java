package ru.bmstu.lab1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleTest {
    @Test
    void shouldPrintRowSums() {
        assertEquals("6 3\n", run("2 3\n1 2 3\n-4 0 7\n",
                () -> ru.bmstu.lab1.task_49.Main.main(new String[0])));
    }

    @Test
    void shouldPrintColumnSums() {
        assertEquals("-3 2 10\n", run("2 3\n1 2 3\n-4 0 7\n",
                () -> ru.bmstu.lab1.task_50.Main.main(new String[0])));
    }

    @Test
    void shouldPrintMatrixProduct() {
        assertEquals("58 64\n139 154\n", run("2 3 2\n1 2 3\n4 5 6\n7 8\n9 10\n11 12\n",
                () -> ru.bmstu.lab1.task_56.Main.main(new String[0])));
    }

    @Test
    void shouldPrintSpiral() {
        assertEquals("1 2 3 6 5 4\n", run("2 3\n1 2 3\n4 5 6\n",
                () -> ru.bmstu.lab1.task_57.Main.main(new String[0])));
    }

    private String run(String input, Runnable task) {
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (PrintStream out = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(out);
            task.run();
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
        return bytes.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
}
