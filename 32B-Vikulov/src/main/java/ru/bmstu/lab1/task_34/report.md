# Задача 34. Максимальная сумма подмассива

**Студент:** Викулов Петр, ИУ3-32Б · **ЛР 1** · **Баллы:** 2 · **Решение:** [`MaxSubarraySum.java`](MaxSubarraySum.java) · **Тесты:** [`MaxSubarraySumTest.java`](../../../../../../test/java/ru/bmstu/lab1/task_34/MaxSubarraySumTest.java)

## Условие

**Вход:** массив из n целых чисел (могут быть отрицательные).  
**Выход:** максимальная сумму непрерывного подмассива.  
**Требование:** O(n) (алгоритм Кадане).  
**Сложность:** высокая

## Формат ввода-вывода

- **Ввод:** `n`, затем `n` целых чисел. Числа разделяются пробелами или переводами строк.
- **Вывод:** максимальная сумма непустого непрерывного подмассива.

Пример запуска:

```text
ввод:
3
2 -1 4

вывод:
5
```

## Идея решения и выбор структуры данных

Алгоритм Кадане, это динамическое программирование с одной переменной состояния.

`bestEndingHere` — наибольшая сумма подмассива, который **заканчивается** в текущей позиции `i`. Такой
подмассив либо продолжает лучший подмассив, кончающийся в `i − 1`, либо начинается заново с `a[i]`.
Выгоднее начать заново, если накопленная сумма отрицательна:

`bestEndingHere = max(a[i], bestEndingHere + a[i])`

Ответ — максимум `bestEndingHere` по всем позициям (`best`).

Обе переменные инициализируются `a[0]`, а не нулём. Поэтому для массива из одних отрицательных чисел ответом
будет наибольший элемент, а не `0` (пустой подмассив по условию не допускается). Суммы хранятся в `long`.

## Асимптотика — [`MaxSubarraySum.java`](MaxSubarraySum.java#L27-L38)

```java
31:        long bestEndingHere = values[0];
32:        long best = values[0];
33:        for (int i = 1; i < values.length; i++) {
34:            bestEndingHere = Math.max(values[i], bestEndingHere + values[i]);
35:            best = Math.max(best, bestEndingHere);
36:        }
```

| Строки | Стоимость | Раз | Вклад |
|--------|-----------|-----|-------|
| 28, 31, 32, 37 | O(1) | 1 | O(1) |
| 33–35 | O(1) | n − 1 | O(n) |

**Время:** `O(n)`. **Память:** две переменные `long`, `O(1)`.
Для сравнения: перебор всех подмассивов `[l, r]` — `O(n²)` (или `O(n³)` без накопления суммы).

## Тесты

| Тест | Что проверяет и почему это важно |
|------|----------------------------------|
| [`shouldFindClassicExample`](../../../../../../test/java/ru/bmstu/lab1/task_34/MaxSubarraySumTest.java#L12) | классический пример, ответ `6` у подмассива `[4, −1, 2, 1]` |
| [`shouldReturnLargestElementWhenAllNegative`](../../../../../../test/java/ru/bmstu/lab1/task_34/MaxSubarraySumTest.java#L18) | все отрицательные, ответ `−1`, а не `0` |
| [`shouldTakeWholeArrayWhenAllPositive`](../../../../../../test/java/ru/bmstu/lab1/task_34/MaxSubarraySumTest.java#L23) | все положительные, берётся весь массив |
| [`shouldNotOverflowOnLargeValues`](../../../../../../test/java/ru/bmstu/lab1/task_34/MaxSubarraySumTest.java#L28) | сумма трёх `MAX_VALUE` больше `int` |
| [`shouldThrowWhenArrayIsEmpty`](../../../../../../test/java/ru/bmstu/lab1/task_34/MaxSubarraySumTest.java#L35) | пустой массив отклоняется |
| [`shouldPrintSumFromConsole`](../../../../../../test/java/ru/bmstu/lab1/task_34/MaxSubarraySumTest.java#L40) | полный запуск через `main` |
