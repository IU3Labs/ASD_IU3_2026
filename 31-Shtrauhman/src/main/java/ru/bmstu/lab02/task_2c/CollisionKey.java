package ru.bmstu.lab02.task_2c;

import java.util.Objects;

/**
 * Задача 2C. Обёртка над строкой, у которой все экземпляры имеют одинаковый хеш-код.
 *
 * <p>Все ключи попадают в одну стартовую ячейку хеш-таблицы и выстраиваются в одну цепочку пробирования -
 * худший случай для открытой адресации. Равенство при этом определяется строкой, поэтому разные ключи
 * остаются различимыми и контракт equals/hashCode не нарушается.
 */
public final class CollisionKey {

    /**
     * Значение произвольное: важно лишь, что оно одно на все ключи.
     */
    public static final int CONSTANT_HASH = 42;

    private final String value;

    /**
     * Строка не может быть null, иначе {@code NullPointerException}.
     */
    public CollisionKey(String value) {
        this.value = Objects.requireNonNull(value, "Строка ключа не может быть null");
    }

    /**
     * Обёрнутая строка.
     */
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return other instanceof CollisionKey key && value.equals(key.value);
    }

    @Override
    public int hashCode() {
        return CONSTANT_HASH;
    }

    @Override
    public String toString() {
        return "CollisionKey[" + value + "]";
    }
}
