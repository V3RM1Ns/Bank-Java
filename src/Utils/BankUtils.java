package Utils;

import java.util.Random;
import java.util.UUID;

public class BankUtils {
    private BankUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static String generateId() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder("AZ");
        for (int i = 0; i < 14; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}