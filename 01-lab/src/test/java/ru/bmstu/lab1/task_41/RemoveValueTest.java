package ru.bmstu.lab1.task_41;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveValueTest {

    @Test
    void shouldRemoveAllOccurrences() {
        int[] result = RemoveValue.removeValue(new int[] {1, 2, 3, 2, 4}, 2);

        assertArrayEquals(new int[] {1, 3, 4}, result);
    }

    @Test
    void shouldKeepArrayWhenValueIsMissing() {
        int[] result = RemoveValue.removeValue(new int[] {1, 3, 4}, 2);

        assertArrayEquals(new int[] {1, 3, 4}, result);
    }

    @Test
    void shouldReturnEmptyWhenEveryElementIsRemoved() {
        int[] result = RemoveValue.removeValue(new int[] {7, 7}, 7);

        assertEquals(0, result.length);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(RemoveValue::main, "5 2\n1 2 3 2 4\n");

        assertEquals("1 3 4", output);
    }
}
