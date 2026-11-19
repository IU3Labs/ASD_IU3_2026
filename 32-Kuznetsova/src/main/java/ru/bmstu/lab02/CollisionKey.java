package ru.bmstu.lab02;

import java.util.Objects;

/**
 * Обёртка над строкой с заведомо плохой хеш-функцией: {@link #hashCode()} у всех экземпляров одинаков.
 * Нужна для проверки корректности и деградации хеш-таблицы при массовых коллизиях.
 */
public final class CollisionKey {

    private static final int CONSTANT_HASH = 42;

    private final String value;

    /**
     * Создаёт ключ.
     *
     * @param value строка, по которой ключи сравниваются через {@link #equals(Object)}
     */
    public CollisionKey(String value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    /** Возвращает исходную строку. */
    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CollisionKey that && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return CONSTANT_HASH;
    }

    @Override
    public String toString() {
        return "CollisionKey(" + value + ")";
    }
}
