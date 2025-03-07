import java.util.Random;
import java.util.Scanner;

public class Main {
    public static boolean DevMode = false;

    private static final int[] END_BYTES = {39, 86, 26, 72, 13, 91, 23};
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        System.out.println("\n  ____            _     _             _   _             \n" + " |  _ \\ ___  __ _(_)___| |_ _ __ __ _| |_(_) ___  _ __  \n" + " | |_) / _ \\/ _` | / __| __| '__/ _` | __| |/ _ \\| '_ \\ \n" + " |  _ <  __/ (_| | \\__ \\ |_| | | (_| | |_| | (_) | | | |\n" + " |_| \\_\\___|\\__, |_|___/\\__|_|  \\__,_|\\__|_|\\___/|_| |_|\n" + "            |___/");
        System.out.println("Github：https://github.com/TheDarknessStar/RegisterQA");
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("\nMessage：");
            String message = input.next();
            if(!DevMode && message.equals("WWSSADADBA")){
                System.out.println("what?");
                DevMode = true;
                continue;
            }
            String key = getKey(message);
            if (key.equals("#")) break;
            System.out.println("Your key is：" + key);
        }
        input.close();
    }

    public static String getKey(String message) {
        message = message.toUpperCase();

        if(!DevMode && message.contains("REGISTERQA")){
            return "null";
        }

        if (message.length() >= 15) {
            return "#";
        }

        for (int i = 0; i < message.length(); i++) {
            char character = message.charAt(i);
            if ((character < 'A' || character > 'Z') && character != '_' && (character < '0' || character > '9')) {
                return "#";
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