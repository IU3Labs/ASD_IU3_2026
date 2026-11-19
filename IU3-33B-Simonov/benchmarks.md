# Замеры производительности

Apple M4 Pro, OpenJDK 25.0.4.1, JVM с `-Xms512m -Xmx512m`.
На каждом размере использовался один и тот же набор разных случайных целых чисел
для всех реализаций. Набор строился заранее с фиксированным seed 33.

Каждая серия создаёт пустую структуру. Для очереди сначала добавляются все элементы,
затем все извлекаются. Для таблицы выполняются три прохода — вставка, поиск и удаление.
Для каждого варианта сделано 8 прогревочных и 16 измеряемых запусков.
Порядок вариантов сдвигается между запусками. Таблица показывает среднее время целой серии.

| Структура | Операция | N элементов | Среднее время, мс |
|---|---|---:|---:|
| PriorityQueueCustom (min) | add + poll | 10 000 | 0,827 |
| java.util.PriorityQueue (min) | add + poll | 10 000 | 0,761 |
| PriorityQueueCustom (max) | add + poll | 10 000 | 0,829 |
| java.util.PriorityQueue (max) | add + poll | 10 000 | 0,866 |
| HashMapCustom | put + get + remove | 10 000 | 0,513 |
| java.util.HashMap | put + get + remove | 10 000 | 0,316 |
| PriorityQueueCustom (min) | add + poll | 50 000 | 5,251 |
| java.util.PriorityQueue (min) | add + poll | 50 000 | 4,862 |
| PriorityQueueCustom (max) | add + poll | 50 000 | 5,227 |
| java.util.PriorityQueue (max) | add + poll | 50 000 | 5,441 |
| HashMapCustom | put + get + remove | 50 000 | 2,795 |
| java.util.HashMap | put + get + remove | 50 000 | 2,032 |
| PriorityQueueCustom (min) | add + poll | 100 000 | 13,587 |
| java.util.PriorityQueue (min) | add + poll | 100 000 | 12,055 |
| PriorityQueueCustom (max) | add + poll | 100 000 | 12,358 |
| java.util.PriorityQueue (max) | add + poll | 100 000 | 12,574 |
| HashMapCustom | put + get + remove | 100 000 | 5,892 |
| java.util.HashMap | put + get + remove | 100 000 | 5,332 |

Из корня проекта:

```bash
mvn -pl IU3-33B-Simonov -am test-compile
java -Xms512m -Xmx512m -cp IU3-33B-Simonov/target/classes:IU3-33B-Simonov/target/test-classes ru.bmstu.lab02.Benchmarks
```

## Вывод

На одном наборе из 100 000 элементов min-heap и max-heap отработали за 13,587 и 12,358 мс соответственно.
Асимптотика у них одинаковая, а число просеиваний и сравнений зависит от порядка значений, поэтому
этот замер не показывает, что один режим всегда быстрее.
Стандартная хеш-таблица прошла измеряемую серию быстрее на всех размерах, а в собственной
при коллизиях приходится последовательно проверять ячейки.
Собственная таблица хранит ключи и значения в двух массивах, а стандартная выделяет узлы для записей,
но фактический расход памяти в этом замере не измерялся.
