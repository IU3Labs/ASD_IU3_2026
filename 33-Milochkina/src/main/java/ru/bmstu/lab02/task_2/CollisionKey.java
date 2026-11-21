package ru.bmstu.lab02.task_2;

import java.util.Objects;

/** Строковый ключ с искусственно одинаковым хешем */
public final class CollisionKey {
    private final String text;

    /**
     * Создаёт ключ для строки
     * @param text - содержимое ключа
     */
    public CollisionKey(String text) {
        this.text = Objects.requireNonNull(text);
    }

    /**
     * Сравнивает ключи по исходным строкам
     * @param other - сравниваемый объект
     * @return - признак равенства строковых ключей
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof CollisionKey key && text.equals(key.text);
    }

    /**
     * Возвращает намеренно одинаковый хеш для всех ключей
     * @return - постоянное значение хеша
     */
    @Override
    public int hashCode() {
        return 1;
    }
}
