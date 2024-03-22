package com.mindhub.homebanking.utilitis;

import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    public static String generateAccountNumber(int numDigits) {
        return "VIN-" + String.format("%0" + numDigits + "d", random.nextInt((int) Math.pow(10, numDigits)));
    }

    public static long generateNumber(int numDigits) {
        if (numDigits <= 0) {
            throw new IllegalArgumentException("Número de dígitos debe ser mayor que cero");
        }

        long range = (long) Math.pow(10, numDigits);
        return random.nextLong(range) * (long) Math.pow(10, numDigits - 1);
    }
}