package clock.wise.utils;

import java.util.Random;

public class ActivationLinkUtils {
    private static final int HASH_LENGTH = 16;
    private static final char[] HASH_GENERATOR_CHARS = "ABCDEFGHIJKLMNOPRSTQWXYZabcdefghijklmnoprstqwxyz".toCharArray();

    public static String generateLinkHash() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for ( int i = 0; i < HASH_LENGTH; ++i ) {
            char c = HASH_GENERATOR_CHARS[ random.nextInt( HASH_GENERATOR_CHARS.length ) ];
            stringBuilder.append( c );
        }

        return stringBuilder.toString();
    }
}
