package ru.bmstu.lab2.task_2;

/** Тестовый ключ с управляемым хешем для проверки переноса через конец таблицы. */
record FixedHashKey(String text, int hash) {
    @Override
    public int hashCode() {
        return hash;
    }
}
