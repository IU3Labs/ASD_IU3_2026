# Лабораторная работа № 2. Структуры данных

**Заворотний Никита Игоревич, ИУ3-33Б**

| Задача | Реализация | Баллы |
|---|---|---:|
| 1A | Двоичная min-куча на динамическом массиве | 2 |
| 1C | Удаление первого совпадения из кучи по значению | 2 |
| 2A | Хеш-таблица с линейным пробированием | 3 |
| | **Итого** | **7** |

Классы `PriorityQueueCustom` и `HashMapCustom` находятся в пакете `ru.bmstu.lab02`.

## Сборка и проверки

Из корня репозитория, на JDK 21+:

```sh
mvn -pl 33-Zavorotniy clean verify
```

Команда проверяет форматирование и стиль, запускает JUnit 5 и строит отчёт JaCoCo.
Для каждого класса требуется покрытие не менее 80% строк. Отчёт доступен в
`33-Zavorotniy/target/site/jacoco/index.html`.

Сборка всех модулей командой `mvn clean verify` требует JDK 25 из-за настроек учебного модуля-примера.

## Бенчмарки

После сборки выполните из корня репозитория на OpenJDK HotSpot:

```powershell
# Windows PowerShell
java -XX:+UseSerialGC -XX:ActiveProcessorCount=2 -Xms512m -Xmx512m -cp "33-Zavorotniy/target/classes;33-Zavorotniy/target/test-classes" ru.bmstu.lab02.Lab02Benchmark
```

```sh
# Linux / macOS
java -XX:+UseSerialGC -XX:ActiveProcessorCount=2 -Xms512m -Xmx512m -cp "33-Zavorotniy/target/classes:33-Zavorotniy/target/test-classes" ru.bmstu.lab02.Lab02Benchmark
```

[Отчёт](report.md) · [Расчёт асимптотики](asymptotics.md) · [Результаты измерений](benchmarks.md)
