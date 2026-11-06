## Задача 7. Второй максимум

Дан массив из n целых чисел, все элементы различны. Найти второй по величине элемент массива. Требуется O(n).

### Идея решения

Используется один линейный проход по массиву.
Сначала сравниваются первые два элемента: больший становится `max`, меньший — `secondMax`. Это даёт корректную инициализацию без флагов и без `Integer.MIN_VALUE`.
Затем для каждого следующего элемента:
- если он больше `max` — старый `max` становится `secondMax`, а текущий — новым `max`;
- иначе, если он больше `secondMax` — обновляется `secondMax`.
  Ветка «элемент равен max» невозможна по условию (все элементы различны), поэтому не проверяется.
  Массив `null` и массив менее чем из двух элементов — некорректный вход, выбрасывается `IllegalArgumentException`.

### Асимптотика

Используется один линейный проход по массиву. Первые два элемента сравниваются при инициализации: больший становится `max`, меньший — `secondMax`.
Затем на каждой итерации текущий элемент либо становится новым `max` (а старый `max` — новым `secondMax`), либо обновляет `secondMax`,
если он больше текущего `secondMax`, но меньше `max`. Ветка равенства не нужна, так как все элементы различны. Итого получаем `O(n)`.

**Время:** `O(n)` — n - 2 итераций, на каждой O(1), плюс O(1) на инициализацию.
**Доп. память:** `O(1)` — только max и secondMax.

### Проверки
Согласно JaCoCo покрытие тестами ветвлений составляет 100%.
- `shouldFindSecondMaxInUnsortedArray` — `5 8 3 15 2 11` → 11: неотсортированный, max = 15.
- `shouldFindSecondMaxWhenMaxIsFirst` — `5 15 3 8 2 11` → 11: максимум первый.
- `shouldFindSecondMaxWhenMaxIsLast` — `5 8 3 11 2 15` → 11: максимум последний.
- `shouldFindSecondMaxInSortedAscendingArray` — `5 1 3 5 7 9` → 7: отсортирован по возрастанию.
- `shouldFindSecondMaxInSortedDescendingArray` — `5 9 7 5 3 1` → 7: отсортирован по убыванию.
- `shouldFindSecondMaxInArrayOfTwoElementsAscending` — `2 10 20` → 10: два элемента, возрастание.
- `shouldFindSecondMaxInArrayOfTwoElementsDescending` — `2 20 10` → 10: два элемента, убывание.
- `shouldFindSecondMaxWithNegativeNumbers` — `4 -8 -14 -2 -6` → -6: отрицательные, max = -2.
- `shouldFindSecondMaxWithMixedSigns` — `5 -3 5 0 8 -7` → 5: смешанные знаки, max = 8.
- `shouldFindSecondMaxWhenSecondMaxIsNegative` — `3 -5 -10 -3` → -5: второй максимум отрицательный.
- `shouldFindSecondMaxWithIntegerMinValue` — `2 MIN 5` → MIN: Integer.MIN_VALUE — валидный второй максимум.
- `shouldNotModifyOriginalArray` — `5 8 3 15 2 11` → исходный массив не изменяется: проверка отсутствия побочных эффектов.