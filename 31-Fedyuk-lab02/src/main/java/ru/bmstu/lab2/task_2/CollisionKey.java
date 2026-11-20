/* ЛР2, задача 2C. Разные строки намеренно получают одинаковый хеш. */
package ru.bmstu.lab2.task_2;

import java.util.Objects;

/** Ключ с равенством по строке и постоянным хешем для проверки коллизий. */
public record CollisionKey(String value) {
    private static final int CONSTANT_HASH = 7;

    /** Создаёт ключ; строка не должна быть null. */
    public CollisionKey {
        Objects.requireNonNull(value, "Строка не должна быть null");
    }

    /** Возвращает одну константу для всех строк, намеренно создавая коллизии. */
    @Override
    public int hashCode() {
        return CONSTANT_HASH;
    }
}
