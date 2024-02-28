package com.mindhub.homebanking.utilitis;

public class RandomUtil {
    public static String generateAccountNumber(int numDigits) {
        return "VIN-" + String.format("%0" + numDigits + "d", new java.util.Random().nextInt((int) Math.pow(10, numDigits)));
    }

    public static Long generateNumber(int numDigits) {
        return new java.util.Random().nextLong((long) Math.pow(10, numDigits));
    }
}
