package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_11Test {

    @Test
    void shouldReturnClosestToAverageElement() {
        String input = "3\n1 2 10\n"; // Среднее 4.33, ближе всего 2
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_11.main(new String[]{});

        assertEquals("2", out.toString().trim());
    }

    @Test
    void shouldReturnFirstWhenEquidistant() {
        String input = "2\n1 3\n"; // Среднее 2.0, разница до 1 и до 3 одинакова (1.0). Выводит первый (1).
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_11.main(new String[]{});

        assertEquals("1", out.toString().trim());
    }
}