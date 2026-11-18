## Задача 1B. Min-heap и max-heap через компаратор

Реализован `ru.bmstu.lab02.task_01b.ComparatorPriorityQueue<T>` с конструкторами без аргументов
и с `Comparator<? super T>`. Вариант наследует базовые операции задачи 1A.

### Идея решения

Класс хранит компаратор и переопределяет только compare. При null используется естественный
порядок, как при вызове конструктора без аргументов. Comparator.naturalOrder задаёт min-heap,
Comparator.reverseOrder — max-heap. Меньший в выбранном порядке элемент всегда находится в корне;
алгоритмы просеивания и индексы массива остаются прежними.

Обобщение `? super T` позволяет применять компаратор базового типа. При наличии компаратора
элементам не требуется реализовывать Comparable: это проверяется на объектах Job.
Comparator является способом сравнения, а не готовой реализацией структуры данных.

### Асимптотика

Конструктор пустой очереди — O(1). Сравнение по компаратору предполагается O(1).
Высота и число просеиваний не изменяются: add — O(log n) амортизированно,
poll — O(log n), peek/size/isEmpty — O(1). Если сравнение само стоит C, стоимость
просеивания составляет O(C * log n). Память массива — O(c), ссылка на компаратор — O(1).

### Проверки

10 независимых тестов JUnit 5 проверяют конструктор без компаратора, null-компаратор,
явный min-heap, max-heap и peek, не-Comparable объекты, компаратор базового типа,
равные приоритеты, крайние значения int, рост массива с эталонной max-heap из JDK
и противоположный порядок извлечения на одном случайном наборе.
Равный приоритет не означает стабильного порядка; тест сохраняет оба значения без ожидания их очередности.
Тестовый класс: [ComparatorPriorityQueueTest.java](../../src/test/java/ru/bmstu/lab02/task_01b/ComparatorPriorityQueueTest.java).

| Юнит-тест | Что проверяет |
|---|---|
| `shouldUseNaturalOrderByDefault` | Конструктор без аргументов задаёт естественный порядок. |
| `shouldUseNaturalOrderForNullComparator` | null-компаратор сохраняет естественный порядок. |
| `shouldSupportExplicitMinHeap` | Comparator.naturalOrder формирует min-heap. |
| `shouldSupportMaxHeapAndPreservePeekedElement` | reverseOrder формирует max-heap; peek не удаляет максимум. |
| `shouldOrderObjectsWithoutComparable` | Объекты Job без Comparable упорядочиваются по priority. |
| `shouldAcceptComparatorOfSupertype` | `Comparator<Number>` используется для очереди `Integer`. |
| `shouldKeepAllValuesWithEqualPriorities` | Строки равной длины не теряются; стабильность их порядка не предполагается. |
| `shouldCompareExtremeValuesInMaxHeapWithoutOverflow` | MIN_VALUE и MAX_VALUE сравниваются корректно в max-heap. |
| `shouldMatchStandardMaxHeapAfterArrayGrowth` | 5000 случайных значений после расширений сравниваются с java.util.PriorityQueue. |
| `shouldProduceOppositeOrdersForSameRandomInput` | На одном наборе из 1000 значений min-heap и max-heap дают противоположные порядки. |

Результат `mvn clean verify` **BUILD SUCCESS**.

| Показатель для класса задачи 1B | Результат |
|---|---|
| JUnit 5 | 10 тестов; все успешны; ошибок и пропусков нет |
| Строки JaCoCo | 6/6 (100%) |
| Ветвления JaCoCo | 2/2 (100%) |

Покрытие получено при полном прогоне модуля. Общие унаследованные методы учитываются
в тех классах, где они объявлены; таблица показывает собственный код `task_01b.ComparatorPriorityQueue`.

Пороги JaCoCo не менее 80% пройдены.

Ручные примеры и ожидаемые результаты:


| Ручная проверка | Ожидаемый результат | Зачем нужна |
|---|---|---|
| 2, 9, 4; естественный порядок | 2, 4, 9 | Min-heap |
| 2, 9, 4; reverseOrder | 9, 4, 2 | Max-heap |
| aa, bb, x; сравнение длины | Сначала x, затем aa и bb в любом порядке | Равные приоритеты |
| Job(second, 2), Job(first, 1); сравнение priority | Job(first, 1), Job(second, 2) | Comparable не обязателен |
| MIN_VALUE, 0, MAX_VALUE; reverseOrder | MAX_VALUE, 0, MIN_VALUE | Корректное сравнение без вычитания |

### Сравнение min-heap и max-heap

В [benchmarks.md](benchmarks.md) приведены реальные средние, медианы и отклонения для одного
массива из 100 000 случайных Integer. Оба режима выполняют N вставок, затем N извлечений.
Для обоих режимов суммарная оценка — O(n log n): инверсия порядка не изменяет высоту дерева.
Разница времени зависит от путей просеивания, сравнения и оптимизаций JVM; по одному замеру
нельзя объявить один режим всегда более быстрым. Генерация данных исключена из времени.

### Повторение проверки

Из каталога `32-Rayvo`:

```bash
mvn clean verify
mvn test-compile exec:java
```