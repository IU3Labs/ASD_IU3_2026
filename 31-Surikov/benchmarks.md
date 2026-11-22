# Замеры структур данных

Windows, Microsoft OpenJDK 21.0.12.1, один поток. Для каждого размера один массив случайных Integer (seed 123). Время генерации исключено. Каждый вариант создаёт новую структуру, проходит 5 прогревов и 10 измеряемых повторов. В таблице среднее время полной серии операций в одном повторе, в мс.

| Структура | Операции | N | Среднее, мс |
|---|---|---:|---:|
| PriorityQueueCustom | add + poll | 10000 | 1.164 |
| PriorityQueue | add + poll | 10000 | 1.030 |
| HashMapCustom | put + get + remove | 10000 | 0.981 |
| HashMap | put + get + remove | 10000 | 0.564 |
| PriorityQueueCustom | add + poll | 50000 | 6.725 |
| PriorityQueue | add + poll | 50000 | 5.974 |
| HashMapCustom | put + get + remove | 50000 | 4.110 |
| HashMap | put + get + remove | 50000 | 2.503 |
| PriorityQueueCustom | add + poll | 100000 | 15.162 |
| PriorityQueue | add + poll | 100000 | 14.821 |
| HashMapCustom | put + get + remove | 100000 | 8.515 |
| HashMap | put + get + remove | 100000 | 6.394 |
| PriorityQueueCustom | add | 100000 | 1.812 |
| PriorityQueueCustom | heapify | 100000 | 1.391 |


На 100 000 элементов обе стандартные структуры быстрее собственных в данном запуске. Построение кучи снизу вверх быстрее последовательных вставок: большинство узлов находится у листьев, а массив выделяется сразу. Обе очереди хранят ссылки в массиве; собственная таблица резервирует два массива, тогда как HashMap выделяет узлы для записей. Память в байтах не измерялась; на время влияют распределение ключей, прогрев JVM и сборка мусора.

Повторить после сборки из папки модуля в PowerShell:

```powershell
java -cp "target/classes;target/test-classes" ru.bmstu.lab2.bench.Benchmark
```
