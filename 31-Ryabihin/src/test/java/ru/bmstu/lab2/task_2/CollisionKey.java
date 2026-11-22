package ru.bmstu.lab2.task_2;

/** Ключ для проверки цепочек пробирования при совпадающих хешах. */
record CollisionKey(String value) {
    private static final int COLLISION_HASH = 42;

    @Override
    public int hashCode() {
        return COLLISION_HASH;
    }
}
