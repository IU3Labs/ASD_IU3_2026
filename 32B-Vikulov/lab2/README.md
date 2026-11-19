# ЛР 2. Структуры данных

**ФИО:** Викулов Петр · **Группа:** ИУ3-32Б · **Модуль:** `32B-Vikulov`, пакет `ru.bmstu.lab2`

Выполнены задачи на **7 баллов** (нужно 7):

| Задача | Баллы | Что сделано | Код | Отчёт |
|--------|:-----:|-------------|-----|-------|
| 2A | 3 | Хеш-таблица `HashMapCustom<K, V>` с линейным пробированием | [`HashMapCustom.java`](../src/main/java/ru/bmstu/lab2/task_2a/HashMapCustom.java) | [report](../src/main/java/ru/bmstu/lab2/task_2a/report.md) |
| 3B | 2 | Частота слов в тексте на своей хеш-таблице, топ-10 на тексте 1 МБ | [`WordFrequency.java`](../src/main/java/ru/bmstu/lab2/task_3b/WordFrequency.java) | [report](../src/main/java/ru/bmstu/lab2/task_3b/report.md) |
| 3C | 2 | Уникальные элементы в порядке первого вхождения, сравнение с `LinkedHashSet` | [`Distinct.java`](../src/main/java/ru/bmstu/lab2/task_3c/Distinct.java) | [report](../src/main/java/ru/bmstu/lab2/task_3c/report.md) |

Замеры и выводы — в [`benchmarks.md`](benchmarks.md).

## Операции и асимптотика

`n` — число элементов, `m` — ёмкость таблицы, `L` — длина текста, `u` — число уникальных элементов.

| Класс | Операция | Время |
|-------|----------|-------|
| `HashMapCustom` | `put(K, V)` | O(1) амортизированно в среднем, O(m + n²) в худшем |
| `HashMapCustom` | `get(K)`, `containsKey(K)`, `remove(K)` | O(1) в среднем, O(m) в худшем |
| `HashMapCustom` | `size()`, `isEmpty()`, `capacity()` | O(1) |
| `HashMapCustom` | `forEach(BiConsumer)` | O(m) |
| `WordFrequency` | `countWords(String)` | O(L) в среднем |
| `WordFrequency` | `topWords(map, k)` | O(m + u log u) |
| `Distinct` | `distinct(List)` | O(n) в среднем |

Обоснование по строкам кода — в отчётах задач.

## Ключевые решения

- **Открытая адресация, два параллельных массива** `keys[]` и `values[]`, без объектов-узлов. Это экономит
  память: на элемент приходятся только ячейки массивов (см. сравнение памяти в `benchmarks.md`).
- **Маркер `DELETED`** при удалении, чтобы не разрывать цепочки пробирования. Вставка переиспользует такие
  ячейки.
- **Живые ключи занимают до 75 % ячеек.** При превышении порога ёмкость удваивается. При заполнении
  ячеек ключами и маркерами сверх 87,5 % таблица может очистить маркеры с сохранением ёмкости.
- **Индекс `Math.abs(hash % capacity)`** вместо `Math.abs(hash) % capacity`: у `Integer.MIN_VALUE` модуль не
  представим в `int`, и индекс получился бы отрицательным.
- `null`-ключи запрещены (`null` в массиве означает пустую ячейку), `null`-значения разрешены.
- Счётчики слов хранятся в собственной таблице и доступны через интерфейс `Map`. Уникальные элементы
  накапливаются в динамическом массиве и возвращаются через интерфейс `List`.

## Тесты

35 тестов JUnit 5: 14 для `HashMapCustom`, 11 для `WordFrequency`, 10 для `Distinct`.
Покрытие JaCoCo: 98,5 % строк и 93,4 % ветвей. Кроме граничных случаев, решения сверяются со стандартными реализациями
на случайных входных данных.

```bash
mvn -pl 32B-Vikulov -am clean test
```

## Структура

```
32B-Vikulov/
├── lab2/
│   ├── README.md          этот файл
│   └── benchmarks.md      замеры и выводы
└── src/
    ├── main/java/ru/bmstu/lab2/
    │   ├── task_2a/       HashMapCustom.java + report.md
    │   ├── task_3b/       WordFrequency.java + report.md
    │   └── task_3c/       Distinct.java + report.md
    └── test/java/ru/bmstu/lab2/
        ├── task_2a/, task_3b/, task_3c/   тесты JUnit
        └── bench/         бенчмарки (запускаются через main, не входят в mvn test)
```
