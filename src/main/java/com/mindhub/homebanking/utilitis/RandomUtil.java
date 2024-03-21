package com.mindhub.homebanking.utilitis;

public class RandomUtil {
    public static String generateAccountNumber(int numDigits) {
        return "VIN-" + String.format("%0" + numDigits + "d", new java.util.Random().nextInt((int) Math.pow(10, numDigits)));
    }

    public static long generateNumber(int numDigits) {
        long min = (long) Math.pow(10, numDigits - 1);
        long max = (long) Math.pow(10, numDigits) - 1;
        return min + new java.util.Random().nextLong(max - min + 1);
    }
}
