package ru.bmstu.lab1.task_31;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScalarProductTest {

    @Test
    void shouldMultiplyPairsAndAddThem() {
        long product = ScalarProduct.scalarProduct(new int[] {1, 2, 3}, new int[] {4, 5, 6});

        assertEquals(32, product);
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        long product = ScalarProduct.scalarProduct(new int[] {-1, 2}, new int[] {3, -4});

        assertEquals(-11, product);
    }

    @Test
    void shouldKeepLargeProduct() {
        long product = ScalarProduct.scalarProduct(new int[] {100000, 100000}, new int[] {100000, 100000});

        assertEquals(20000000000L, product);
    }

    @Test
    void shouldReadArraysFromInput() {
        String output = ProgramOutput.read(ScalarProduct::main, "3\n1 2 3\n4 5 6\n");

        assertEquals("32", output);
    }
}
