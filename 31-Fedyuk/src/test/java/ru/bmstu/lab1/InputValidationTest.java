package ru.bmstu.lab1;

import static ru.bmstu.lab1.support.ConsoleAssertions.assertOutput;

import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.bmstu.lab1.task_1.MinMax;
import ru.bmstu.lab1.task_10.LastMultiple;
import ru.bmstu.lab1.task_11.ClosestToAverage;
import ru.bmstu.lab1.task_12.PairWithSum;
import ru.bmstu.lab1.task_2.FirstOccurrence;
import ru.bmstu.lab1.task_25.ArraySum;
import ru.bmstu.lab1.task_26.ModularProduct;
import ru.bmstu.lab1.task_3.OccurrenceCount;
import ru.bmstu.lab1.task_36.RunningSum;
import ru.bmstu.lab1.task_37.ArrayReverse;
import ru.bmstu.lab1.task_38.LeftRotation;
import ru.bmstu.lab1.task_4.AllIndices;
import ru.bmstu.lab1.task_41.RemoveElement;
import ru.bmstu.lab1.task_5.EvenIndexSum;
import ru.bmstu.lab1.task_6.AboveAverageCount;
import ru.bmstu.lab1.task_7.SecondMaximum;
import ru.bmstu.lab1.task_8.SignFilter;
import ru.bmstu.lab1.task_9.SortedCheck;

/** Общие сценарии ошибочного ввода проверяются для каждой из 18 консольных программ. */
class InputValidationTest {
    private static final String LENGTH_PROMPT = "Введите длину массива\n";
    private static final String ARRAY_PROMPT = "Введите массив\n";
    private static final String INTEGER_ERROR = "Ошибка: должно быть целое число!\n";
    private static final String LENGTH_ERROR =
            "Ошибка: некорректная длина массива: допустимо от 2 до 99999\n";

    @ParameterizedTest(name = "Задача {0}")
    @MethodSource("programs")
    void shouldReportEmptyInput(String taskName, Consumer<String[]> program) {
        assertOutput("", LENGTH_PROMPT + INTEGER_ERROR, program);
    }

    @ParameterizedTest(name = "Задача {0}")
    @MethodSource("programs")
    void shouldReportNonNumericLength(String taskName, Consumer<String[]> program) {
        assertOutput("abc", LENGTH_PROMPT + INTEGER_ERROR, program);
    }

    @ParameterizedTest(name = "Задача {0}")
    @MethodSource("programs")
    void shouldRejectTooSmallLength(String taskName, Consumer<String[]> program) {
        assertOutput("1 5", LENGTH_PROMPT + LENGTH_ERROR, program);
    }

    @ParameterizedTest(name = "Задача {0}")
    @MethodSource("programs")
    void shouldRejectTooLargeLength(String taskName, Consumer<String[]> program) {
        assertOutput("100000", LENGTH_PROMPT + LENGTH_ERROR, program);
    }

    @ParameterizedTest(name = "Задача {0}")
    @MethodSource("programs")
    void shouldReportNonNumericFirstElement(String taskName, Consumer<String[]> program) {
        assertOutput("3 x", LENGTH_PROMPT + ARRAY_PROMPT + INTEGER_ERROR, program);
    }

    @ParameterizedTest(name = "Задача {0}")
    @MethodSource("programs")
    void shouldReportNonNumericLaterElement(String taskName, Consumer<String[]> program) {
        assertOutput("3 1 2 x", LENGTH_PROMPT + ARRAY_PROMPT + INTEGER_ERROR, program);
    }

    @ParameterizedTest(name = "Задача {0}")
    @MethodSource("programs")
    void shouldReportMissingElement(String taskName, Consumer<String[]> program) {
        assertOutput("3 1 2", LENGTH_PROMPT + ARRAY_PROMPT + INTEGER_ERROR, program);
    }

    @ParameterizedTest(name = "Задача {0}")
    @MethodSource("programs")
    void shouldRejectElementOutsideIntRange(String taskName, Consumer<String[]> program) {
        assertOutput("2 2147483648 0", LENGTH_PROMPT + ARRAY_PROMPT + INTEGER_ERROR, program);
    }

    private static Stream<Arguments> programs() {
        return Stream.of(
                Arguments.of("1", (Consumer<String[]>) MinMax::main),
                Arguments.of("2", (Consumer<String[]>) FirstOccurrence::main),
                Arguments.of("3", (Consumer<String[]>) OccurrenceCount::main),
                Arguments.of("4", (Consumer<String[]>) AllIndices::main),
                Arguments.of("5", (Consumer<String[]>) EvenIndexSum::main),
                Arguments.of("6", (Consumer<String[]>) AboveAverageCount::main),
                Arguments.of("7", (Consumer<String[]>) SecondMaximum::main),
                Arguments.of("8", (Consumer<String[]>) SignFilter::main),
                Arguments.of("9", (Consumer<String[]>) SortedCheck::main),
                Arguments.of("10", (Consumer<String[]>) LastMultiple::main),
                Arguments.of("11", (Consumer<String[]>) ClosestToAverage::main),
                Arguments.of("12", (Consumer<String[]>) PairWithSum::main),
                Arguments.of("25", (Consumer<String[]>) ArraySum::main),
                Arguments.of("26", (Consumer<String[]>) ModularProduct::main),
                Arguments.of("36", (Consumer<String[]>) RunningSum::main),
                Arguments.of("37", (Consumer<String[]>) ArrayReverse::main),
                Arguments.of("38", (Consumer<String[]>) LeftRotation::main),
                Arguments.of("41", (Consumer<String[]>) RemoveElement::main));
    }
}
