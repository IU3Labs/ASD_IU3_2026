package ru.bmstu.lab2.task_3b;

/**
 * Слово и число его вхождений в текст — элемент результата {@link WordCounter#topWords}.
 *
 * @param word слово в нижнем регистре
 * @param count сколько раз слово встретилось
 */
public record WordCount(String word, int count) {
}
