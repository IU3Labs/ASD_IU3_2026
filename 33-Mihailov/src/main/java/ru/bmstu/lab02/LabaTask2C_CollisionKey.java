/*
Задача 2C (искусственные коллизии). Создайте обёртку CollisionKey над строкой, у которой hashCode() всегда возвращает одну и ту же константу. Проверьте, что ваша таблица корректно обрабатывает 10 000 таких ключей: вставка, поиск, удаление — без ошибок и без потери элементов. Продемонстрируйте деградацию производительности и объясните её в README.md.
*/

package ru.bmstu.lab02;

import java.util.Objects;

public final class LabaTask2C_CollisionKey {
    private final String text;

    public LabaTask2C_CollisionKey(String text) {
        this.text = Objects.requireNonNull(text);
    }

    @Override
    public int hashCode() { return 17; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof LabaTask2C_CollisionKey)) return false;
        return text.equals(((LabaTask2C_CollisionKey) other).text);
    }

    @Override
    public String toString() { return text; }
}
