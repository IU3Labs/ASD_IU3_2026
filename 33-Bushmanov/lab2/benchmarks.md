# Замеры производительности ЛР2

Среда измерений: Microsoft OpenJDK 25.0.4.1, Windows 11, максимальная память JVM — 512 МиБ.

Для каждого размера выполнены 5 прогревочных и 10 измеряемых повторов.
Собственные и стандартные структуры получают одинаковые наборы случайных целых чисел.
Подготовка данных не входит в измеряемое время.

Для очередей измеряется суммарное время N добавлений и N извлечений.
Для хеш-таблиц отдельно измеряются N вставок, N успешных поисков и N удалений.
В таблице указано среднее время серии, включая расширения при вставках.
Память оценивается по устройству структур, без измерения в байтах.

| Структура | Операция | N элементов | Среднее время (мс) | Примечание |
|---|---|---:|---:|---|
| PriorityQueueCustom | add + poll | 10000 | 2.057 | min-heap |
| java.util.PriorityQueue | add + poll | 10000 | 2.170 | min-heap |
| PriorityQueueCustom | add + poll | 10000 | 1.925 | max-heap |
| java.util.PriorityQueue | add + poll | 10000 | 2.123 | max-heap |
| HashMapCustom | put | 10000 | 0.967 | load = 0.75 |
| HashMapCustom | get | 10000 | 0.331 | load = 0.75 |
| HashMapCustom | remove | 10000 | 0.249 | load = 0.75 |
| java.util.HashMap | put | 10000 | 0.760 | load = 0.75 |
| java.util.HashMap | get | 10000 | 0.232 | load = 0.75 |
| java.util.HashMap | remove | 10000 | 0.128 | load = 0.75 |
| PriorityQueueCustom | add + poll | 50000 | 12.804 | min-heap |
| java.util.PriorityQueue | add + poll | 50000 | 13.870 | min-heap |
| PriorityQueueCustom | add + poll | 50000 | 11.340 | max-heap |
| java.util.PriorityQueue | add + poll | 50000 | 14.821 | max-heap |
| HashMapCustom | put | 50000 | 5.337 | load = 0.75 |
| HashMapCustom | get | 50000 | 0.787 | load = 0.75 |
| HashMapCustom | remove | 50000 | 0.673 | load = 0.75 |
| java.util.HashMap | put | 50000 | 3.517 | load = 0.75 |
| java.util.HashMap | get | 50000 | 0.395 | load = 0.75 |
| java.util.HashMap | remove | 50000 | 0.370 | load = 0.75 |
| PriorityQueueCustom | add + poll | 100000 | 34.549 | min-heap |
| java.util.PriorityQueue | add + poll | 100000 | 28.837 | min-heap |
| PriorityQueueCustom | add + poll | 100000 | 34.763 | max-heap |
| java.util.PriorityQueue | add + poll | 100000 | 30.277 | max-heap |
| HashMapCustom | put | 100000 | 11.170 | load = 0.75 |
| HashMapCustom | get | 100000 | 2.026 | load = 0.75 |
| HashMapCustom | remove | 100000 | 1.702 | load = 0.75 |
| java.util.HashMap | put | 100000 | 7.504 | load = 0.75 |
| java.util.HashMap | get | 100000 | 1.130 | load = 0.75 |
| java.util.HashMap | remove | 100000 | 1.118 | load = 0.75 |

## Объяснение результатов

На одинаковом наборе из 100 000 элементов собственные min-heap и max-heap показали близкое время: 34,549 и 34,763 мс; оба режима имеют сложность O(N log N), а небольшая разница может быть связана с последовательностью сравнений и колебаниями времени выполнения.
На этом размере стандартная очередь оказалась быстрее собственной, хотя на меньших наборах результат был обратным.
Стандартная HashMap во всех проведённых замерах работала быстрее собственной таблицы; на время влияют организация поиска, коллизии и перехеширование.
Обе очереди используют массив ссылок, а HashMapCustom хранит данные в трёх массивах без отдельных объектов записей; память зависит от ёмкости, и точное сравнение в байтах не проводилось.