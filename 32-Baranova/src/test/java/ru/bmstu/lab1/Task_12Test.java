package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task_12Test {

    @Test
    void shouldReturnIndicesWhenPairExists() {
        String input = "4\n10 15 3 7\n10\n"; // 3 + 7 = 10, индексы 2 и 3
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_12.main(new String[]{});

        assertEquals("2 3", out.toString().trim());
    }

    @Test
    void shouldReturnMinusOneWhenNoPairExists() {
        String input = "3\n1 2 3\n10\n"; // Нет пары в сумме дающей 10
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Task_12.main(new String[]{});

        assertEquals("-1 -1", out.toString().trim());
    }
}