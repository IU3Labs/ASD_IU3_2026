# Замеры структур данных

Windows, Microsoft OpenJDK 21.0.12.1, один поток. Для каждого размера один массив случайных Integer (seed 123). Время генерации исключено. Каждый вариант создаёт новую структуру, проходит 5 прогревов и 10 измеряемых повторов. В таблице среднее время полной серии операций в одном повторе, в мс.

| Структура | Операции | N | Среднее, мс |
|---|---|---:|---:|
| PriorityQueueCustom | add + poll | 10000 | 1.164 |
| PriorityQueue | add + poll | 10000 | 1.030 |
| PriorityQueueCustom | add + poll | 50000 | 6.725 |
| PriorityQueue | add + poll | 50000 | 5.974 |
| PriorityQueueCustom | add + poll | 100000 | 15.162 |
| PriorityQueue | add + poll | 100000 | 14.821 |
| PriorityQueueCustom | add | 100000 | 1.812 |
| PriorityQueueCustom | heapify | 100000 | 1.391 |



На 100 000 элементов полный цикл собственной очереди занял 15,162 мс, библиотечной — 14,821 мс. Построение снизу вверх заняло 1,391 мс против 1,812 мс при последовательных вставках. Обе очереди используют память под массив ссылок.

Повторить после сборки из папки модуля в PowerShell:

```powershell
java -cp "target/classes;target/test-classes" ru.bmstu.lab2.bench.Benchmark
```
