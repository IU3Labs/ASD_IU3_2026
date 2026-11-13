package ru.bmstu.lab2.task_3b;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Разбивает текст на слова для задачи 3B за один проход по строке — {@code O(L)}, где {@code L} — длина текста.
 *
 * <p>Правила:
 * <ul>
 *   <li>слово — непрерывная последовательность букв и цифр (любого алфавита, в том числе кириллицы);</li>
 *   <li>регистр не учитывается: все символы приводятся к нижнему регистру;</li>
 *   <li>знаки препинания и пробелы — разделители и в слова не попадают;</li>
 *   <li>дефис и апостроф внутри слова сохраняются ({@code кто-то}, {@code don't}), если с обеих
 *       сторон от них буква или цифра; типографский апостроф {@code ’} заменяется на {@code '}.</li>
 * </ul>
 */
public final class WordTokenizer {

    private static final char HYPHEN = '-';
    private static final char APOSTROPHE = '\'';
    private static final char TYPOGRAPHIC_APOSTROPHE = '’';

    private WordTokenizer() {
    }

    /**
     * Вызывает {@code action} для каждого слова текста в порядке появления.
     *
     * @param text исходный текст
     * @param action действие над очередным словом (слово уже в нижнем регистре)
     * @throws NullPointerException если {@code text} или {@code action} равны {@code null}
     */
    public static void forEachWord(String text, Consumer<String> action) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(action, "action");
        StringBuilder word = new StringBuilder();
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char current = text.charAt(i);
            if (Character.isLetterOrDigit(current)) {
                word.append(Character.toLowerCase(current));
            } else if (isInnerJoiner(text, i, word)) {
                word.append(current == TYPOGRAPHIC_APOSTROPHE ? APOSTROPHE : current);
            } else {
                flush(word, action);
            }
        }
        flush(word, action);
    }

    /** Дефис или апостроф считается частью слова, только если стоит между буквами/цифрами. */
    private static boolean isInnerJoiner(String text, int index, StringBuilder word) {
        char current = text.charAt(index);
        boolean joiner = current == HYPHEN || current == APOSTROPHE || current == TYPOGRAPHIC_APOSTROPHE;
        return joiner
                && word.length() > 0
                && index + 1 < text.length()
                && Character.isLetterOrDigit(text.charAt(index + 1));
    }

    private static void flush(StringBuilder word, Consumer<String> action) {
        if (word.length() > 0) {
            action.accept(word.toString());
            word.setLength(0);
        }
    }
}
