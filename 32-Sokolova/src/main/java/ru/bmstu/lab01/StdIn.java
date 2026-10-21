package ru.bmstu.lab01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Утилита для быстрого чтения целых чисел и массивов из stdin.
 */
public final class StdIn {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer tokenizer;

    private StdIn() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static String nextToken() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            String line = READER.readLine();
            if (line == null) {
                return null;
            }
            tokenizer = new StringTokenizer(line);
        }
        return tokenizer.nextToken();
    }

    public static int nextInt() throws IOException {
        String token = nextToken();
        return token != null ? Integer.parseInt(token) : 0;
    }

    public static int[] readArray(int size) throws IOException {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = nextInt();
        }
        return arr;
    }
}