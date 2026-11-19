package ru.bmstu.lab02;

/**
 * Слово и число его вхождений в тексте.
 *
 * @param word  слово в нижнем регистре
 * @param count число вхождений
 */
public record WordCount(String word, int count) {
}
