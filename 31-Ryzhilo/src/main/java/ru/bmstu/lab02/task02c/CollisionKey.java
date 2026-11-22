package ru.bmstu.lab02.task02c;

import java.util.Objects;

/** Строковый ключ с постоянным хешем для эксперимента с коллизиями. */
public record CollisionKey(String value) {
    private static final int FIXED_HASH = 42;

    /** Сохраняет строку и отклоняет null. */
    public CollisionKey {
        Objects.requireNonNull(value);
    }

    /** Возвращает одинаковый хеш для всех экземпляров. */
    @Override
    public int hashCode() {
        return FIXED_HASH;
    }
}
