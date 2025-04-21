package utils;

import java.util.Random;

public class KeyManager {
    private static final int[] END_BYTES = {39, 86, 26, 72, 13, 91, 23};
    private static final Random RANDOM = new Random();

    private static boolean isDeveloperName(String name){
        return name.equalsIgnoreCase("XuanLingJingYue") || name.equals("玄灵鲸跃") || name.equalsIgnoreCase("ZouGuoDaHuang") || name.equals("走过大荒");
    }

    public static String getErrorMessage(int errorCode) {
        String errorMessage;
        switch (errorCode) {
            case 0:
                errorMessage = ": )";
                break;
            case 1:
                errorMessage = "Invalid input: Maximum length exceeded (15 characters)";
                break;
            case 2:
                errorMessage = "Invalid input: Only alphanumeric characters and underscores are allowed";
                break;
            case 3:
                errorMessage = "Invalid input: Contains space or empty";
                break;
            default:
                errorMessage = "Unknown error occurred";
        }
        return errorMessage;
    }

    public static String generateKey(String message) {
        if(message.contains(" ")){
            return "3";
        }
        if(isDeveloperName(message)){
            return "0";
        }

        message = message.toUpperCase();

        if (message.length() >= 15) {
            return "1";
        }

        for (int i = 0; i < message.length(); i++) {
            char character = message.charAt(i);
            if ((character < 'A' || character > 'Z') && character != '_' && (character < '0' || character > '9')) {
                return "2";
            }
        }

        StringBuilder encodedName = new StringBuilder();
        int writtenBytes = 0;

        for (int i = 0; i < message.length(); i++) {
            encodedName.append(70 - (26 - (message.charAt(i) - 'A')));
            writtenBytes++;
        }

        encodedName.append(END_BYTES[RANDOM.nextInt(END_BYTES.length)]);
        writtenBytes++;

        StringBuilder fullNameStr = new StringBuilder(encodedName);

        while (writtenBytes != 15) {
            fullNameStr.append(10 + RANDOM.nextInt(89));
            writtenBytes++;
        }

        int checksumFullName = 0;
        for (int i = 0; i < fullNameStr.length(); i++) {
            checksumFullName += (fullNameStr.charAt(i) - '0');
        }

        int checksumName = 0;
        for (int i = 0; i < encodedName.length(); i++) {
            checksumName += (encodedName.charAt(i) - '0');
        }
        checksumName %= 100;

        int checkSumPart1 = checksumFullName + RANDOM.nextInt(999 - checksumFullName);
        int checkSumPart2 = checkSumPart1 - checksumFullName;

        StringBuilder retStr = new StringBuilder();
        retStr.append(String.format("%03d", checkSumPart1));
        retStr.reverse();
        retStr.append(fullNameStr);
        retStr.append(String.format("%03d", checkSumPart2));
        retStr.append(String.format("%02d", checksumName));

        retStr.insert(6, "-");
        retStr.insert(15, "-");
        retStr.insert(23, "-");
        retStr.insert(31, "-");
        retStr.insert(36, "-");

        return retStr.toString();
    }
}