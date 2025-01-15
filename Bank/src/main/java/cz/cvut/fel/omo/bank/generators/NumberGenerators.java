package cz.cvut.fel.omo.bank.generators;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NumberGenerators {
    private static final Random RANDOM = new Random();

    // Constants for account number generation
    private static final int ACCOUNT_NUMBER_MIN = (int) Math.pow(10, 9);      // 10^9
    private static final int ACCOUNT_NUMBER_MAX = (int) Math.pow(10, 10) - 1; // 10^10 - 1

    // Constants for card number generation
    private static final long CARD_NUMBER_MIN = (long) Math.pow(10, 15);      // 10^15
    private static final long CARD_NUMBER_MAX = (long) Math.pow(10, 16) - 1;  // 10^16 - 1

    // Constants for CVC generation
    private static final int CVC_MIN = 100;
    private static final int CVC_MAX = 999;

    public static int generateAccountNumber() {
        return RANDOM.nextInt(ACCOUNT_NUMBER_MAX - ACCOUNT_NUMBER_MIN + 1) + ACCOUNT_NUMBER_MIN;
    }

    public static long generateCardNumber() {
        return CARD_NUMBER_MIN + (long) (RANDOM.nextDouble() * (CARD_NUMBER_MAX - CARD_NUMBER_MIN));
    }

    public static int generateCVC() {
        return CVC_MIN + RANDOM.nextInt(CVC_MAX - CVC_MIN + 1);
    }

    public static Date generateExpirationDate() {
        LocalDate now = LocalDate.now();

        // Constants for card validity
        final int MIN_VALIDITY_MONTHS = 1;
        final int MAX_VALIDITY_YEARS = 5;

        LocalDate minDate = now.plusMonths(MIN_VALIDITY_MONTHS);
        LocalDate maxDate = now.plusYears(MAX_VALIDITY_YEARS);

        long minEpochDay = minDate.toEpochDay();
        long maxEpochDay = maxDate.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minEpochDay, maxEpochDay + 1);

        return Date.from(LocalDate.ofEpochDay(randomDay).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}