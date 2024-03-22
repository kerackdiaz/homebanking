package com.mindhub.homebanking.utilitis;

import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    public static String generateAccountNumber(int numDigits) {
        return "VIN-" + String.format("%0" + numDigits + "d", random.nextInt((int) Math.pow(10, numDigits)));
    }

    public static int generateNumber(int numDigits) {
        if (numDigits <= 0) {
            throw new IllegalArgumentException("Número de dígitos debe ser mayor que cero");
        }

        int min = (int) Math.pow(10, numDigits - 1);
        int max = (int) Math.pow(10, numDigits) - 1;
        return min + random.nextInt(max - min + 1);
    }
}
