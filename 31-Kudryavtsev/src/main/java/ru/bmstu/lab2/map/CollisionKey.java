package ru.bmstu.lab2.map;

import java.util.Objects;

/** Задача 2C: строковый ключ с постоянным хешем для проверки коллизий. */
public final class CollisionKey {
    private final String value;

    /** Создаёт ключ из непустой ссылки на строку. Сама строка может быть пустой. */
    public CollisionKey(String value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    /** Сравнивает содержимое строк. */
    @Override
    public boolean equals(Object other) {
        return other instanceof CollisionKey key && value.equals(key.value);
    }

    /** Всегда возвращает 1: все ключи попадают в одну цепочку пробирования. */
    @Override
    public int hashCode() {
        return 1;
    }

    /** Возвращает исходную строку. */
    @Override
    public String toString() {
        return value;
    }
}
