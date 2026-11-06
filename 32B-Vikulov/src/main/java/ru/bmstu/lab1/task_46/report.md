# Задача 46. Сжатие диапазона

**Студент:** Викулов Петр, ИУ3-32Б · **ЛР 1** · **Баллы:** 2 · **Решение:** [`RankCompression.java`](RankCompression.java) · **Тесты:** [`RankCompressionTest.java`](../../../../../../test/java/ru/bmstu/lab1/task_46/RankCompressionTest.java)

## Условие

**Вход:** массив из n целых чисел.  
**Выход:** массив той же длины, где каждый элемент заменён его рангом — номером в отсортированном порядке без дубликатов (начиная с 0).  
**Пример:** `[100, 50, 100, 300]` → `[1, 0, 1, 2]`.  
**Сложность:** высокая

## Формат ввода-вывода

- **Ввод:** `n`, затем `n` целых чисел. Числа разделяются пробелами или переводами строк.
- **Вывод:** массив рангов (номер среди различных значений по возрастанию, с нуля).

Пример запуска:

```text
ввод:
3
30 -10 20

вывод:
2 0 1
```

## Идея решения и выбор структуры данных

Ранг элемента — это его индекс в отсортированном массиве **различных** значений. Отсюда три шага:

1. скопировать массив и отсортировать копию;
2. удалить повторы одним проходом: элемент сохраняется, если он отличается от предыдущего;
3. для каждого исходного элемента найти его индекс в массиве уникальных значений бинарным поиском.

Выбор структуры данных: отсортированный массив вместо `HashMap` или `TreeMap` «значение → ранг». Он занимает
меньше памяти (нет объектов-обёрток `Integer`) и даёт гарантированную оценку `O(n log n)`, а не «в среднем».
Бинарный поиск всегда находит элемент, потому что каждое исходное значение есть среди уникальных.

Исходный массив не изменяется, ранги возвращаются новым массивом.

## Асимптотика — [`RankCompression.java`](RankCompression.java#L28-L48)

```java
29:        int[] unique = sortedUnique(values);
30:        int[] ranks = new int[values.length];
31:        for (int i = 0; i < values.length; i++) {
33:            ranks[i] = Arrays.binarySearch(unique, values[i]);
34:        }
...
39:        int[] sorted = values.clone();
40:        Arrays.sort(sorted);
42:        for (int i = 0; i < sorted.length; i++) {
43:            if (i == 0 || sorted[i] != sorted[i - 1]) {
44:                sorted[size++] = sorted[i];
45:            }
46:        }
47:        return Arrays.copyOf(sorted, size);
```

Пусть `u ≤ n` — число различных значений.

| Строки | Стоимость | Раз | Вклад |
|--------|-----------|-----|-------|
| 39 — копия | O(n) | 1 | O(n) |
| 40 — `Arrays.sort` (Dual-Pivot Quicksort для `int[]`) | O(n log n) | 1 | O(n log n) |
| 42–45 — удаление повторов | O(1) | n | O(n) |
| 47 | O(u) | 1 | O(n) |
| 30 | O(n) | 1 | O(n) |
| 31–33 — `binarySearch` по `unique` | O(log u) | n | O(n log n) |

**Время:** `O(n) + O(n log n) + O(n) + O(n log n) = O(n log n)`.
**Память:** `sorted` (`n`), `unique` (`u ≤ n`), `ranks` (`n`), это `O(n)`.

## Тесты

| Тест | Что проверяет и почему это важно |
|------|----------------------------------|
| [`shouldCompressExampleFromStatement`](../../../../../../test/java/ru/bmstu/lab1/task_46/RankCompressionTest.java#L12) | пример из условия `[100, 50, 100, 300] → [1, 0, 1, 2]` |
| [`shouldGiveZeroToAllEqualElements`](../../../../../../test/java/ru/bmstu/lab1/task_46/RankCompressionTest.java#L17) | все элементы равны, все ранги `0` |
| [`shouldHandleNegativeAndExtremeValues`](../../../../../../test/java/ru/bmstu/lab1/task_46/RankCompressionTest.java#L22) | отрицательные значения и границы `int` |
| [`shouldNotModifyInputArray`](../../../../../../test/java/ru/bmstu/lab1/task_46/RankCompressionTest.java#L29) | исходный массив не меняется |
| [`shouldPrintRanksFromConsole`](../../../../../../test/java/ru/bmstu/lab1/task_46/RankCompressionTest.java#L38) | полный запуск через `main` |
