package ru.bmstu.lab02.task_3c;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ru.bmstu.lab02.task_2a.HashMapCustom;

/**
 * Задача 3C. Уникальные элементы списка в порядке первого вхождения.
 *
 * <p>Хеш-таблица из задачи 2A служит множеством уже встреченных элементов: проверка стоит O(1) в среднем,
 * а не O(u), как при поиске по результату. Порядок хранит сам результирующий список.
 */
public final class Distinct {

    private Distinct() {
    }

    /**
     * Время O(n) в среднем, дополнительная память O(u), где u - число уникальных элементов.
     * Элементы списка могут быть null.
     *
     * @throws NullPointerException если сам список null
     */
    public static <T> List<T> distinct(List<T> items) {
        Objects.requireNonNull(items, "Список не может быть null");
        HashMapCustom<T, Boolean> seen = new HashMapCustom<>();
        List<T> result = new ArrayList<>();
        // Таблица не принимает null-ключ, поэтому встреча с null отслеживается отдельным флагом.
        boolean seenNull = false;
        for (T item : items) {
            if (item == null) {
                if (!seenNull) {
                    seenNull = true;
                    result.add(null);
                }
            } else if (!seen.containsKey(item)) {
                seen.put(item, Boolean.TRUE);
                result.add(item);
            }
        }
        return result;
    }
}
