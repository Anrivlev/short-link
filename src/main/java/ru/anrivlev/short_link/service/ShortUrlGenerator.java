package ru.anrivlev.short_link.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShortUrlGenerator {
    private char[] alphabet;

    private Map<Character, Integer> charMap;

    ShortUrlGenerator() {
        this.alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        this.charMap = new HashMap<>();
        for (int i = 0; i < alphabet.length; i++) {
            charMap.put(alphabet[i], i);
        }
    }

    public String getShortUrl(int id) {
        StringBuilder shortUrl = new StringBuilder();

        while (id > 0) {
            shortUrl.append(alphabet[(id % alphabet.length)]);
            id = id / alphabet.length;
        }

        return shortUrl.reverse().toString();
    }

    public int getId(String shortUrl) {
        int id = 0;

        for (int i = 0; i < shortUrl.length(); i++) {
            id = id * alphabet.length + charMap.get(shortUrl.charAt(i));
        }
        return id;
    }
}
