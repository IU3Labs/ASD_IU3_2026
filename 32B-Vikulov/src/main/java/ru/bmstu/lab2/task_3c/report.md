# Задача 3C. Уникальные элементы

**Студент:** Викулов Петр, ИУ3-32Б · **ЛР 2** · **Баллы:** 2 ·
**Решение:** [`Distinct.java`](Distinct.java) ·
**Тесты:** [`DistinctTest.java`](../../../../../../test/java/ru/bmstu/lab2/task_3c/DistinctTest.java)

## Условие

Реализовать `List<T> distinct(List<T> items)` — уникальные элементы в порядке первого вхождения, используя
собственную хеш-таблицу. Сравнить по времени и памяти с `new LinkedHashSet<>(items)`.

## Идея

Таблица [`HashMapCustom`](../task_2a/HashMapCustom.java) хранит увиденные элементы.
[Алгоритм](Distinct.java) проходит по входу и при первой встрече добавляет элемент в результирующий
массив. Длина массива удваивается по мере заполнения. В конце возвращается представление заполненной
части массива через интерфейс `List`.

Первый `null` учитывается отдельным флагом `seenNull`, поскольку ключ `null` обозначает пустую ячейку
таблицы. Исходный список сохраняется.

## Асимптотика

Пусть `n` — число входных элементов, `u` — число уникальных. Проход выполняет до `n` поисков и до `u`
вставок. Расширения результирующего массива в сумме копируют O(u) ссылок. Время O(n) в среднем и O(n·u)
при совпадающих хешах. Таблица и массив результата занимают O(u) дополнительной памяти.

## Сравнение с `LinkedHashSet`

Таблица замеров времени и памяти, условия эксперимента и выводы приведены в
[`benchmarks.md`](../../../../../../../lab2/benchmarks.md).

## Тесты

| Тест | Что проверяет |
|------|---------------|
| `shouldKeepFirstOccurrenceOrder` | порядок первого вхождения при повторах |
| `shouldReturnEmptyListForEmptyInput` | пустой вход |
| `shouldReturnSingleElementWhenAllEqual` | все элементы одинаковы |
| `shouldReturnSameListWhenAllElementsUnique` | повторов нет — результат совпадает со входом |
| `shouldKeepSingleNullAtItsFirstPosition` | `null` учитывается один раз и на своём месте |
| `shouldNotModifyInputList` | исходный список не меняется |
| `shouldCompareElementsByEqualsNotByReference` | два разных объекта `String` с одинаковым текстом — один элемент |
| `shouldMatchLinkedHashSetOnLargeRandomInput` | 100 000 случайных чисел — сверка с `LinkedHashSet` |
| `shouldReturnSingleNullForAllNullInput` | список целиком из `null` |
| `shouldGrowResultArrayAndKeepOrder` | рост массива и сохранение порядка |
