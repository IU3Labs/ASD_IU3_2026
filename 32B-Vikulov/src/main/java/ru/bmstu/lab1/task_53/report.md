# Задача 53. Седловые точки

**Студент:** Викулов Петр, ИУ3-32Б · **ЛР 1** · **Баллы:** 3 · **Решение:** [`SaddlePoints.java`](SaddlePoints.java) · **Тесты:** [`SaddlePointsTest.java`](../../../../../../test/java/ru/bmstu/lab1/task_53/SaddlePointsTest.java)

## Условие

**Вход:** матрица n x m из целых чисел.  
**Выход:** все седловые точки — элементы, одновременно минимальные в своей строке и максимальные в своём столбце. Вывести в формате `(индекс_строки, индекс_столбца, значение)`.  
**Сложность:** средняя

## Формат ввода-вывода

- **Ввод:** `n m`, затем матрица `n × m`. Числа разделяются пробелами или переводами строк.
- **Вывод:** каждая седловая точка на отдельной строке в формате `(строка, столбец, значение)`; если точек нет, вывод пуст.

Пример запуска:

```text
ввод:
2 3
1 2 3
4 5 6

вывод:
(1, 0, 4)
```

## Идея решения и выбор структуры данных

Наивно для каждой клетки можно проверить всю её строку и весь столбец, но это `O(nm(n + m))`. Вместо этого
сначала считаются два вспомогательных массива:

- `rowMin[i]` — минимум строки `i`;
- `columnMax[j]` — максимум столбца `j`.

После этого клетка `(i, j)` седловая тогда и только тогда, когда `a[i][j] == rowMin[i]` и
`a[i][j] == columnMax[j]`. Проверка занимает `O(1)`, весь алгоритм — три прохода по матрице, `O(nm)`.

Седловая точка хранится в `record Point(row, column, value)`. Его `toString()` сразу даёт формат вывода из
условия. Результат собирается в `ArrayList`, потому что число точек заранее неизвестно.

**Граничные случаи:** седловых точек нет; седловых точек несколько (в матрице из одинаковых чисел седловая
каждая клетка); пустая матрица. Формат вывода при отсутствии точек в условии не задан, поэтому выбран пустой
вывод.

## Асимптотика — [`SaddlePoints.java`](SaddlePoints.java#L45-L83)

```java
50:        int[] rowMin = rowMinimums(matrix);
51:        int[] columnMax = columnMaximums(matrix);
52:        for (int i = 0; i < matrix.length; i++) {
53:            for (int j = 0; j < matrix[i].length; j++) {
54:                int value = matrix[i][j];
55:                if (value == rowMin[i] && value == columnMax[j]) {
56:                    result.add(new Point(i, j, value));
...
65:        for (int i = 0; i < matrix.length; i++) {           // rowMinimums
67:            for (int value : matrix[i]) {
68:                result[i] = Math.min(result[i], value);
...
77:        for (int[] row : matrix) {                         // columnMaximums
78:            for (int j = 0; j < row.length; j++) {
79:                result[j] = Math.max(result[j], row[j]);
```

| Строки | Стоимость | Раз | Вклад |
|--------|-----------|-----|-------|
| 50 → 65–68 | O(1) | n · m | O(nm) |
| 51 → 76 (`fill`) + 77–79 | O(1) | m + n · m | O(nm) |
| 52–55 — проверка клетки | O(1) | n · m | O(nm) |
| 56 — `ArrayList.add` | O(1) амортизированно | ≤ n · m | O(nm) |

**Время:** `3 · O(nm) = O(nm)`. Без предпосчёта для каждой из `nm` клеток пришлось бы проверять её строку и
столбец (`m + n` операций), итого `O(nm(n + m))`. Строки 50–51 убирают этот множитель.
**Память:** `rowMin` (`n`) + `columnMax` (`m`), это `O(n + m)`, плюс список ответа.

## Тесты

| Тест | Что проверяет и почему это важно |
|------|----------------------------------|
| [`shouldFindSingleSaddlePoint`](../../../../../../test/java/ru/bmstu/lab1/task_53/SaddlePointsTest.java#L14) | одна седловая точка в матрице 3×3 |
| [`shouldReturnEmptyListWhenNoSaddlePoints`](../../../../../../test/java/ru/bmstu/lab1/task_53/SaddlePointsTest.java#L25) | седловых точек нет |
| [`shouldReturnEveryCellOfConstantMatrix`](../../../../../../test/java/ru/bmstu/lab1/task_53/SaddlePointsTest.java#L35) | все элементы равны, седловая каждая клетка |
| [`shouldReturnEmptyListForEmptyMatrix`](../../../../../../test/java/ru/bmstu/lab1/task_53/SaddlePointsTest.java#L40) | пустая матрица |
| [`shouldPrintPointsFromConsole`](../../../../../../test/java/ru/bmstu/lab1/task_53/SaddlePointsTest.java#L45) | запуск через `main` и формат `(i, j, v)` |
