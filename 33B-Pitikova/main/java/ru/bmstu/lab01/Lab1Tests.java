package ru.university.acsd.lab01;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для решений ЛР1.
 */
public class Lab1Tests {

    /**
     * Вспомогательный метод для запуска задачи с заданным вводом и перехватом вывода.
     *
     * @param input строка с входными данными
     * @param task  задача для запуска
     * @return строка с результатом выполнения
     */
    private String runTask(String input, Runnable task) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            
            task.run();
            
            return outContent.toString().trim();
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }

    @Test
    public void shouldReturnSumOfElementsForTask25() {
        String input = "5\n1 2 3 4 5\n";
        String expected = "15";
        assertEquals(expected, runTask(input, () -> task_25.main(new String[]{})));
    }

    @Test
    public void shouldReturnProductModuloForTask26() {
        String input = "3\n2 3 4\n";
        String expected = "24";
        assertEquals(expected, runTask(input, () -> task_26.main(new String[]{})));
    }

    @Test
    public void shouldReturnSumOfSquaresForTask27() {
        String input = "3\n1 2 3\n";
        String expected = "14"; // 1^2 + 2^2 + 3^2 = 1 + 4 + 9 = 14
        assertEquals(expected, runTask(input, () -> task_27.main(new String[]{})));
    }

    @Test
    public void shouldReturnDotProductForTask31() {
        String input = "3\n1 2 3\n4 5 6\n";
        String expected = "32"; // 1*4 + 2*5 + 3*6 = 4 + 10 + 18 = 32
        assertEquals(expected, runTask(input, () -> task_31.main(new String[]{})));
    }

    @Test
    public void shouldReturnPrefixSumsForTask35() {
        String input = "5\n1 2 3 4 5\n2\n0 2\n1 4\n";
        String expected = "6\n14"; // (1+2+3)=6, (2+3+4+5)=14
        assertEquals(expected, runTask(input, () -> task_35.main(new String[]{})));
    }

    @Test
    public void shouldReturnRunningSumForTask36() {
        String input = "4\n1 2 3 4\n";
        String expected = "1 3 6 10";
        assertEquals(expected, runTask(input, () -> task_36.main(new String[]{})));
    }
}