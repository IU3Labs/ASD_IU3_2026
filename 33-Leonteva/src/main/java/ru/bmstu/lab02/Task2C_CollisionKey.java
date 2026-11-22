/*Задача 2C (искусственные коллизии). Создайте обёртку CollisionKey над строкой,
у которой hashCode() всегда возвращает одну и ту же константу. Проверьте, что ваша
таблица корректно обрабатывает 10 000 таких ключей: вставка, поиск, удаление — без ошибок
и без потери элементов. Продемонстрируйте деградацию производительности и объясните её в README.md.*/

package ru.bmstu.lab02;

import java.util.Objects;

public final class Task2C_CollisionKey {
    private final String value;

    public Task2C_CollisionKey(String value) {
        this.value = Objects.requireNonNull(value, "value must not be null");
    }

    @Override
    public int hashCode() {
        return 42;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Task2C_CollisionKey)) {
            return false;
        }
        return value.equals(((Task2C_CollisionKey) other).value);
    }

    @Override
    public String toString() {
        return value;
    }
}
