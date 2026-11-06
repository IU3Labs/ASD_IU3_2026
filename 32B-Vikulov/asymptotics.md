# Расчёт асимптотики — ЛР 1

## Обозначения и метод

- `n` — длина массива; для матриц `n` строк и `m` столбцов; `q` — число запросов; `k` — диапазон значений
  (`max − min + 1`).
- Для каждой задачи разобраны строки метода-решения: **стоимость** одного выполнения строки × **сколько раз**
  она выполняется = **вклад**. Итоговая оценка — сумма вкладов, из которой оставляется старший член.
- Строки с присваиваниями, сравнениями, обращением к элементу массива и арифметикой стоят `O(1)`.
- «Доп. память» — память сверх входных данных. Если ответ создаётся новым массивом, это указано отдельно.
- Ввод-вывод в `main` (чтение `n` чисел и печать ответа) везде стоит `O(n)` / `O(nm)` и асимптотику не ухудшает,
  поэтому не разбирается.
- **Подробный разбор по строкам кода для каждой задачи — в разделе «Асимптотика» её отчёта** `task_NN/report.md`
  (ссылки в последнем столбце таблицы). Номера строк относятся к текущей версии файлов.

## Сводная таблица

| № | Задача | Время | Доп. память | Определяющие строки | Разбор |
|---|--------|-------|-------------|---------------------|-------|
| 1 | Максимум и минимум | O(n) | O(1) | цикл `MinMax.java:29–32` | [task_01](src/main/java/ru/bmstu/lab1/task_01/report.md) |
| 2 | Индекс первого вхождения | O(n) | O(1) | цикл `FirstOccurrence.java:27–31` | [task_02](src/main/java/ru/bmstu/lab1/task_02/report.md) |
| 12 | Пара с заданной суммой | O(n) в среднем | O(n) | два цикла `PairWithSum.java:34–43` + `49–51` | [task_12](src/main/java/ru/bmstu/lab1/task_12/report.md) |
| 13 | Сортировка выбором | O(n²) | O(1) | вложенный цикл `SelectionSort.java:25–29` | [task_13](src/main/java/ru/bmstu/lab1/task_13/report.md) |
| 19 | Слияние отсортированных | O(n + m) | O(n + m) под ответ | циклы `MergeSorted.java:29–38` | [task_19](src/main/java/ru/bmstu/lab1/task_19/report.md) |
| 20 | Сортировка подсчётом | O(n + k) | O(k) | `CountingSort.java:44` и цикл `49–53` | [task_20](src/main/java/ru/bmstu/lab1/task_20/report.md) |
| 33 | Количество инверсий | O(n log n) | O(n) | рекурсия `Inversions.java:38–39` + слияние `47–61` | [task_33](src/main/java/ru/bmstu/lab1/task_33/report.md) |
| 34 | Максимальная сумма подмассива | O(n) | O(1) | цикл `MaxSubarraySum.java:33–36` | [task_34](src/main/java/ru/bmstu/lab1/task_34/report.md) |
| 35 | Префиксные суммы | O(n) + O(1) на запрос | O(n) | цикл `PrefixSums.java:25–27`, запрос `42` | [task_35](src/main/java/ru/bmstu/lab1/task_35/report.md) |
| 38 | Циклический сдвиг влево | O(n) | O(1) | вызовы `RotateLeft.java:32–34` | [task_38](src/main/java/ru/bmstu/lab1/task_38/report.md) |
| 42 | Вставка в отсортированный | O(n) | O(n) под ответ | поиск `InsertSorted.java:47–54`, копирование `29`, `31` | [task_42](src/main/java/ru/bmstu/lab1/task_42/report.md) |
| 46 | Сжатие диапазона | O(n log n) | O(n) | `RankCompression.java:40` и цикл `31–34` | [task_46](src/main/java/ru/bmstu/lab1/task_46/report.md) |
| 53 | Седловые точки | O(nm) | O(n + m) | три двойных цикла `SaddlePoints.java:52–59`, `65–70`, `77–81` | [task_53](src/main/java/ru/bmstu/lab1/task_53/report.md) |
| 57 | Спиральный обход | O(nm) | O(1) + O(nm) под ответ | строки записи `SpiralTraversal.java:39, 42, 46, 51` | [task_57](src/main/java/ru/bmstu/lab1/task_57/report.md) |
| 60 | Острова из единиц | O(nm) | O(nm) | обход `Islands.java:38–45` + DFS `56–68` | [task_60](src/main/java/ru/bmstu/lab1/task_60/report.md) |
