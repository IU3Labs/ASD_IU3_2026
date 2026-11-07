package ru.bmstu.lab1.task_37;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseArrayTest {

    @Test
    void shouldReverseEvenLength() {
        int[] numbers = {1, 2, 3, 4};
        ReverseArray.reverse(numbers);

        assertArrayEquals(new int[] {4, 3, 2, 1}, numbers);
    }

    @Test
    void shouldReverseOddLength() {
        int[] numbers = {1, 2, 3};
        ReverseArray.reverse(numbers);

        assertArrayEquals(new int[] {3, 2, 1}, numbers);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(ReverseArray::main, "4\n1 2 3 4\n");

        assertEquals("4 3 2 1", output);
    }
}
