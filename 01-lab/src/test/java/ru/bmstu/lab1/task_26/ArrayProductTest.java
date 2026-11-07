package ru.bmstu.lab1.task_26;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ProgramOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayProductTest {

    @Test
    void shouldMultiplyAndTakeModulo() {
        long product = ArrayProduct.productModulo(new int[] {2, 3, 4});

        assertEquals(24, product);
    }

    @Test
    void shouldTurnNegativeProductIntoPositiveRemainder() {
        long product = ArrayProduct.productModulo(new int[] {-2, 3});

        assertEquals(1000000001L, product);
    }

    @Test
    void shouldReturnZeroWhenArrayContainsZero() {
        long product = ArrayProduct.productModulo(new int[] {0, 5, 8});

        assertEquals(0, product);
    }

    @Test
    void shouldTakeModuloOfLargeProduct() {
        long product = ArrayProduct.productModulo(new int[] {100000, 100000});

        assertEquals(999999937L, product);
    }

    @Test
    void shouldReadArrayFromInput() {
        String output = ProgramOutput.read(ArrayProduct::main, "3\n2 3 4\n");

        assertEquals("24", output);
    }
}
