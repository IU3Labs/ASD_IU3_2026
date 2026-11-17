package ru.bmstu.lab1.common;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Scanner;
import org.junit.jupiter.api.Test;

class ArrayIoTest {
    @Test
    void shouldRejectNegativeArraySize() {
        try (Scanner input = new Scanner("-1")) {
            assertThrows(IllegalArgumentException.class, () -> ArrayIo.readArray(input));
        }
    }
}
