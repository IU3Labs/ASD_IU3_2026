package ru.bmstu.lab02.task_3b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Задача 3B. Подсчёт частоты слов.
 * Использует упрощенную встроенную хеш-таблицу (без выноса в отдельный класс).
 */
public class WordCounter {

    //маленькая хеш-таблица прямо тут, чтобы не тащить 2A
    private static class MiniMap {
        private final List<String> keys = new ArrayList<>();
        private final List<Integer> values = new ArrayList<>();

        public void put(String key, int value) {
            int idx = keys.indexOf(key);
            if (idx != -1) {
                values.set(idx, values.get(idx) + 1);
            } else {
                keys.add(key);
                values.add(1);
            }
        }

        public List<String> keys() { return keys; }
        public List<Integer> values() { return values; }
    }

    public static List<String> topFrequentWords(String text, int n) {
        if (text == null || text.isEmpty()) return new ArrayList<>();

        String cleaned = text.toLowerCase().replaceAll("[^a-zа-яё\\s]", " ");
        String[] words = cleaned.split("\\s+");

        MiniMap map = new MiniMap();
        for (String word : words) {
            if (!word.isEmpty()) {
                map.put(word, 1);
            }
        }

        //собираем в список и сортируем
        List<int[]> indices = new ArrayList<>();
        for (int i = 0; i < map.keys().size(); i++) {
            indices.add(new int[]{i});
        }
        //сортируем индексы по убыванию частоты
        indices.sort((a, b) -> map.values().get(b[0]) - map.values().get(a[0]));

        List<String> result = new ArrayList<>();
        for (int i = 0; i < n && i < indices.size(); i++) {
            int idx = indices.get(i)[0];
            result.add(map.keys().get(idx) + ": " + map.values().get(idx));
        }
        return result;
    }

    //ввод-вывод
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append(" ");
        }

        List<String> top10 = topFrequentWords(sb.toString(), 10);
        for (String s : top10) {
            System.out.println(s);
        }
    }
}