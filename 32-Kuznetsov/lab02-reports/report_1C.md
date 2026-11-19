# Отчёт по задаче 1C. Удаление по значению

**Студент:** Кузнецов Д.А., группа ИУ3-32Б, вариант 25
**Код:** `src/main/java/ru/bmstu/lab02/task1c/DeletablePriorityQueue.java`
(использует `positionOf` и `extractAt` из `task1a/PriorityQueueCustom.java`)
**Тесты:** `src/test/java/ru/bmstu/lab02/task1c/DeletablePriorityQueueTest.java`

## Условие

Добавить операцию `boolean remove(T item)` — удаление первого вхождения элемента по значению за O(n).
После удаления свойство кучи должно восстанавливаться. Тесты: удаление корня, удаление из середины кучи,
удаление несуществующего элемента, удаление из пустой очереди.

## 1. Идея решения и структура данных

Структура — та же куча на массиве. `DeletablePriorityQueue<T>` наследуется от `OrderedPriorityQueue<T>` (задача 1B),
поэтому удаление работает и в min-, и в max-куче.

```java
public boolean remove(T item) {
    int position = positionOf(item);
    if (position < 0) {
        return false;
    }
    extractAt(position);
    return true;
}
```

Удаление идёт в два этапа:

1. **Поиск** (`positionOf`) — линейный проход по массиву, сравнение через `equals`. Возвращается индекс первого
   совпадения или `-1`. Ускорить поиск нельзя: куча упорядочена только «по вертикали» (родитель ≤ ребёнок),
   и нужный элемент может оказаться в любой ветке.
2. **Удаление позиции** (`extractAt`):

```java
protected void extractAt(int position) {
    count--;
    Object last = items[count];
    items[count] = null;
    if (position == count) {
        return;
    }
    items[position] = last;
    siftDown(position);
    if (items[position] == last) {
        siftUp(position);
    }
}
```

На место удалённого элемента ставится последний элемент массива, последняя ячейка освобождается.
Перенесённый элемент может нарушить порядок в обе стороны:

- он **больше** ребёнка → его нужно опустить (`siftDown`);
- он **меньше** нового родителя (пришёл из другой ветки) → его нужно поднять (`siftUp`).

Сначала пробуем `siftDown`. Если элемент остался на месте (`items[position] == last`), пробуем `siftUp`.
Отдельный случай — удаление последнего элемента массива: переносить нечего, сразу `return`.
Тот же `extractAt(0)` используется в `poll`, поэтому код удаления не дублируется.

## 2. Анализ асимптотики

| Операция | Время | Доп. память |
|---|---|---|
| `positionOf` | O(n) | O(1) |
| `extractAt` | O(log n) | O(1) |
| `remove` | O(n) | O(1) |
| остальные операции | как в 1A/1B | как в 1A/1B |

### Обоснование кодом

**`positionOf` — O(n).** Один цикл по всем элементам; в худшем случае (элемента нет или он последний) — `n` вызовов `equals`:

```java
for (int i = 0; i < count; i++) {
    if (item.equals(items[i])) {
        return i;
    }
}
return -1;
```

**`extractAt` — O(log n).** Перенос последнего элемента — O(1). Затем `siftDown` (не больше `⌊log₂ n⌋` шагов вниз)
и, возможно, `siftUp` (не больше `⌊log₂ n⌋` шагов вверх). Итого O(2 log n) = O(log n).

**`remove` — O(n).** `positionOf` + `extractAt` = O(n) + O(log n) = **O(n)**, как требует условие.

**Дополнительная память — O(1):** только индексы и ссылка `last`, новых массивов нет.

## 3. Ручные тесты

11 тестов.

| Тест | Что проверяет | Граничный случай |
|---|---|---|
| `shouldRemoveRoot` | удаление минимума через `remove` | **удаление корня** (из условия) |
| `shouldSiftUpAfterRemovingFromMiddle` | куча `[1, 50, 5, 60, 70, 8, 6]`, удаляем `60`, на его место встаёт `6`, который меньше родителя `50` | **удаление из середины**, нужен `siftUp` |
| `shouldSiftDownAfterRemovingFromMiddle` | куча `[1..7]`, удаляем `2`, на его место встаёт `7` | **удаление из середины**, нужен `siftDown` |
| `shouldRemoveLastElementOfArray` | удаление последней ячейки массива | ветка `position == count` |
| `shouldReturnFalseWhenRemovingMissingElement` | элемента нет → `false`, размер не меняется | **несуществующий элемент** (из условия) |
| `shouldReturnFalseWhenRemovingFromEmptyQueue` | `remove` на пустой очереди | **пустая очередь** (из условия) |
| `shouldReturnFalseWhenRemovingNull` | `remove(null)` → `false` | null |
| `shouldRemoveOnlyFirstOccurrence` | из трёх одинаковых удаляется одно | дубликаты |
| `shouldBecomeEmptyAfterRemovingEverything` | удаление всех элементов по одному | очередь становится пустой |
| `shouldRemoveFromMaxHeap` | удаление корня и элемента из max-кучи | другой компаратор |
| `shouldKeepOrderAfterRandomRemovals` | 200 случайных удалений сверяются с `ArrayList.remove` | большой набор |

**Почему эти тесты важны.** Тесты из середины кучи подобраны так, чтобы один требовал `siftDown`, а другой — `siftUp`.
Если убрать вызов `siftUp` из `extractAt`, падает `shouldSiftUpAfterRemovingFromMiddle` (проверено). Тест со случайными
удалениями сверяет и возвращаемое `true/false`, и итоговый порядок со стандартным списком.

### Покрытие

Строки класса `DeletablePriorityQueue` тестами задачи 1C: **9 из 9 (100 %)**. Эти же тесты проходят все ветки
`extractAt` и `positionOf` в базовом классе: корень, середина с `siftUp`, середина с `siftDown`, последняя ячейка, `null`.
