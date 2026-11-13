package ru.bmstu.lab2.task_3b;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Генератор русского текста заданного размера для задачи 3B (около 1 МБ).
 *
 * <p>Частоты слов в естественных языках подчиняются закону Ципфа: слово с рангом {@code r}
 * встречается примерно в {@code r} раз реже самого частого. Генератор выбирает слова
 * из встроенного словаря с такими вероятностями, а с вероятностью 12 %
 * вставляет случайное «редкое» слово из слогов — так в тексте появляются тысячи разных слов,
 * как в настоящей книге. Предложения начинаются с заглавной буквы, между словами встречаются
 * запятые, тире, двоеточия, а в конце — точка, вопросительный или восклицательный знак.
 * Генерация детерминирована: одно и то же зерно даёт один и тот же текст.
 */
public final class SampleTextGenerator {

    /** 1 МБ — размер текста из условия задачи. */
    public static final int ONE_MEGABYTE = 1 << 20;

    /** Словарь, отсортированный по убыванию «частоты» (ранг = индекс + 1). */
    static final String[] VOCABULARY = {
        "и", "в", "не", "на", "он", "что", "я", "с", "она", "как", "а", "то", "все", "это", "но",
        "его", "так", "к", "по", "из", "у", "же", "сказал", "было", "за", "был", "бы", "мы", "от", "когда",
        "ее", "только", "они", "еще", "о", "вы", "ты", "для", "до", "уже", "или", "были", "кто-то", "время",
        "человек", "князь", "глаза", "теперь", "здесь", "можно", "вдруг", "сам", "себя", "лицо", "день",
        "рука", "дом", "жизнь", "слово", "дело", "что-то", "из-за", "очень", "всегда", "никогда", "потом",
        "ничего", "между", "голос", "минуту", "дверь", "город", "ночь", "утро", "вечер", "письмо", "дорога",
        "война", "мир", "любовь", "правда", "вопрос", "ответ", "сердце", "мысль", "улыбнулся", "посмотрел",
        "подумал", "сказала", "спросил", "ответил", "пошел", "пришел", "стоял", "сидел", "знал", "хотел",
    };

    /** Доля «редких» слов, составленных из случайных слогов. */
    private static final double RARE_WORD_PROBABILITY = 0.12;

    private static final String[] SYLLABLES = {
        "ба", "ве", "го", "ди", "жу", "за", "ки", "ло", "ма", "ни", "по", "ру", "со", "ти", "фа", "хе",
        "це", "чи", "ша", "ще", "ю", "ям", "ор", "ен", "ст", "пр", "ов", "ка", "ль", "ня",
    };

    private static final int MIN_SYLLABLES = 2;
    private static final int MAX_SYLLABLES = 5;
    private static final int MIN_SENTENCE_WORDS = 4;
    private static final int MAX_SENTENCE_WORDS = 18;
    private static final double INNER_PUNCTUATION_PROBABILITY = 0.1;
    private static final String[] INNER_PUNCTUATION = {",", ",", ",", " —", ":", ";"};
    private static final String[] SENTENCE_ENDINGS = {".", ".", ".", ".", "!", "?", "..."};
    private static final int SENTENCES_PER_PARAGRAPH = 6;

    /** Накопленные вероятности закона Ципфа для словаря. */
    private static final double[] CUMULATIVE = zipfCumulative(VOCABULARY.length);

    private SampleTextGenerator() {
    }

    /**
     * Генерирует текст, размер которого в UTF-8 не меньше {@code targetBytes} байт.
     *
     * @param targetBytes желаемый размер текста в байтах UTF-8
     * @param seed зерно генератора случайных чисел
     * @return текст
     */
    public static String generate(int targetBytes, long seed) {
        return build(seed, targetBytes, Integer.MAX_VALUE);
    }

    /**
     * Генерирует текст, в котором не меньше {@code targetWords} слов (для бенчмарков по числу элементов).
     *
     * @param targetWords желаемое число слов
     * @param seed зерно генератора случайных чисел
     * @return текст
     */
    public static String generateWords(int targetWords, long seed) {
        return build(seed, Integer.MAX_VALUE, targetWords);
    }

    /** Добавляет предложения, пока не будет достигнут хотя бы один из лимитов. */
    private static String build(long seed, int bytesLimit, int wordsLimit) {
        Random random = new Random(seed);
        StringBuilder text = new StringBuilder();
        int bytes = 0;
        int words = 0;
        int sentences = 0;
        while (bytes < bytesLimit && words < wordsLimit) {
            int sentenceStart = text.length();
            words += appendSentence(text, random);
            sentences++;
            text.append(sentences % SENTENCES_PER_PARAGRAPH == 0 ? "\n\n" : " ");
            bytes += text.substring(sentenceStart).getBytes(StandardCharsets.UTF_8).length;
        }
        return text.toString();
    }

    /** Дописывает в {@code text} одно предложение и возвращает число слов в нём. */
    private static int appendSentence(StringBuilder text, Random random) {
        int words = MIN_SENTENCE_WORDS + random.nextInt(MAX_SENTENCE_WORDS - MIN_SENTENCE_WORDS + 1);
        int start = text.length();
        for (int i = 0; i < words; i++) {
            if (i > 0) {
                if (random.nextDouble() < INNER_PUNCTUATION_PROBABILITY) {
                    text.append(pick(INNER_PUNCTUATION, random));
                }
                text.append(' ');
            }
            text.append(word(random));
        }
        text.setCharAt(start, Character.toUpperCase(text.charAt(start)));
        text.append(pick(SENTENCE_ENDINGS, random));
        return words;
    }

    private static String word(Random random) {
        if (random.nextDouble() < RARE_WORD_PROBABILITY) {
            int syllables = MIN_SYLLABLES + random.nextInt(MAX_SYLLABLES - MIN_SYLLABLES + 1);
            StringBuilder word = new StringBuilder();
            for (int i = 0; i < syllables; i++) {
                word.append(pick(SYLLABLES, random));
            }
            return word.toString();
        }
        double point = random.nextDouble();
        int rank = 0;
        while (rank < CUMULATIVE.length - 1 && CUMULATIVE[rank] < point) {
            rank++;
        }
        return VOCABULARY[rank];
    }

    private static String pick(String[] options, Random random) {
        return options[random.nextInt(options.length)];
    }

    private static double[] zipfCumulative(int size) {
        double total = 0;
        for (int rank = 1; rank <= size; rank++) {
            total += 1.0 / rank;
        }
        double[] cumulative = new double[size];
        double sum = 0;
        for (int rank = 1; rank <= size; rank++) {
            sum += 1.0 / rank / total;
            cumulative[rank - 1] = sum;
        }
        return cumulative;
    }
}
