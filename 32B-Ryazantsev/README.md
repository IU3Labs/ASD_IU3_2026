# Лабораторная работа №1 - Рязанцев, ИУ3-32Б

Выполнены задачи 49, 50, 51, 52, 54, 55, 58 и 59
Всего 8 задач по 3 балла, при полном зачёте - 24 балла

## Отчёты по задачам

В каждой задаче есть отдельный отчёт с тремя разделами: идея решения и структура данных, анализ времени и памяти с пояснением кода, ручные тесты с ожидаемыми результатами

| Задача | Тема | Отчёт | Java | JUnit |
|---|---|---|---|---|
| 49 | Суммы строк матрицы | [task_49.md](tasks/task_49.md) | [Task49.java](src/main/java/ru/bmstu/lab01/task_49/Task49.java) | [Task49Test.java](src/test/java/ru/bmstu/lab01/task_49/Task49Test.java) |
| 50 | Суммы столбцов матрицы | [task_50.md](tasks/task_50.md) | [Task50.java](src/main/java/ru/bmstu/lab01/task_50/Task50.java) | [Task50Test.java](src/test/java/ru/bmstu/lab01/task_50/Task50Test.java) |
| 51 | Максимумы строк матрицы | [task_51.md](tasks/task_51.md) | [Task51.java](src/main/java/ru/bmstu/lab01/task_51/Task51.java) | [Task51Test.java](src/test/java/ru/bmstu/lab01/task_51/Task51Test.java) |
| 52 | Минимумы столбцов матрицы | [task_52.md](tasks/task_52.md) | [Task52.java](src/main/java/ru/bmstu/lab01/task_52/Task52.java) | [Task52Test.java](src/test/java/ru/bmstu/lab01/task_52/Task52Test.java) |
| 54 | Сумма главной диагонали | [task_54.md](tasks/task_54.md) | [Task54.java](src/main/java/ru/bmstu/lab01/task_54/Task54.java) | [Task54Test.java](src/test/java/ru/bmstu/lab01/task_54/Task54Test.java) |
| 55 | Сумма побочной диагонали | [task_55.md](tasks/task_55.md) | [Task55.java](src/main/java/ru/bmstu/lab01/task_55/Task55.java) | [Task55Test.java](src/test/java/ru/bmstu/lab01/task_55/Task55Test.java) |
| 58 | Заполнение матрицы змейкой | [task_58.md](tasks/task_58.md) | [Task58.java](src/main/java/ru/bmstu/lab01/task_58/Task58.java) | [Task58Test.java](src/test/java/ru/bmstu/lab01/task_58/Task58Test.java) |
| 59 | Определитель матрицы 3 на 3 | [task_59.md](tasks/task_59.md) | [Task59.java](src/main/java/ru/bmstu/lab01/task_59/Task59.java) | [Task59Test.java](src/test/java/ru/bmstu/lab01/task_59/Task59Test.java) |

## Проверка

Требуются JDK 25 и Maven

Из корня репозитория:

```sh
mvn -pl 32B-Ryazantsev -am clean verify
```

JUnit проверяет обычные случаи, граничные значения и некорректные входные данные
JaCoCo проверяет минимум 80% покрытия строк каждого класса задачи, а не только среднее покрытие модуля
Фактическое покрытие видно после успешного запуска в `32B-Ryazantsev/target/site/jacoco/index.html`

## Запуск задачи

Например, задача 49 после сборки:

```sh
java -cp 32B-Ryazantsev/target/classes ru.bmstu.lab01.task_49.Task49
```

Для задач 49-55 и 59 на вход подаются размеры и элементы матрицы, для задачи 58 - только размеры
Отчёты и реализации разделены по номерам задач, общей папки `common` нет
