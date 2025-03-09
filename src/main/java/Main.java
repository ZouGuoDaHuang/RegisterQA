import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int[] END_BYTES = {39, 86, 26, 72, 13, 91, 23};
    private static final Random RANDOM = new Random();
    private static final String GITHUB_LINK = "https://github.com/Ox7E00/RegisterQA";
    private static final String VERSION = "1.0.2";
    private static final String COMMAND_PREFIX = "/";

    // Command constants
    private static final String HELP_CMD = "/help";
    private static final String QUIT_CMD = "/quit";
    private static final String EXIT_CMD = "/exit";
    private static final String DEV_CMD = "/dev";

    public static boolean devMode = false;

    public static void main(String[] args) {
        printWelcomeMessage();
        Scanner input = new Scanner(System.in);

        while (true) {
            String message = promptForInput(input);
            if (message == null) continue;

            if (message.startsWith(COMMAND_PREFIX)) {
                processCommand(message);
                continue;
            }

            String key = getKey(message);
            printKeyResult(key);
        }
    }

    private static void printWelcomeMessage() {
        System.out.println("""
                  ____            _     _             _   _            \s
                 |  _ \\ ___  __ _(_)___| |_ _ __ __ _| |_(_) ___  _ __ \s
                 | |_) / _ \\/ _` | / __| __| '__/ _` | __| |/ _ \\| '_ \\\s
                 |  _ <  __/ (_| | \\__ \\ |_| | | (_| | |_| | (_) | | | |
                 |_| \\_\\___|\\__, |_|___/\\__|_|  \\__,_|\\__|_|\\___/|_| |_|
                            |___/\
                """);
        System.out.println("Github：" + GITHUB_LINK);
        System.out.println("Version：" + VERSION);
    }

    private static String promptForInput(Scanner input) {
        System.out.print("\nMessage：");
        return input.next();
    }

    private static void processCommand(String command) {
        String normalizedCmd = command.toLowerCase().trim();

        switch (normalizedCmd) {
            case HELP_CMD -> System.out.println("[" + QUIT_CMD + "][" + EXIT_CMD + "]");
            case QUIT_CMD, EXIT_CMD -> System.exit(0);
            case DEV_CMD -> toggleDevMode();
            default -> System.out.println(HELP_CMD + " query");
        }
    }

    private static void toggleDevMode() {
        devMode = !devMode;
        System.out.println("Developer Mode：" + devMode);
    }

    private static void printKeyResult(String key) {
        if (!key.contains("-")) {
            System.out.println(getKeyError(Integer.parseInt(key)));
        } else {
            System.out.println("Your key is：" + key);
        }
    }

    private static String getKeyError(int errorCode) {
        return switch (errorCode) {
            case 1 -> "Authorization error: Developer name not allowed";
            case 2 -> "Input validation failed: Maximum length exceeded (15 chars)";
            case 3 -> "Invalid input: Only alphanumeric characters and underscores allowed";
            default -> "Unknown error code";
        };
    }

    public static String getKey(String message) {
        message = message.toUpperCase();

        if(!devMode && message.contains("0X7E00")){
            return "1";
        }

        if (message.length() >= 15) {
            return "2";
        }

        for (int i = 0; i < message.length(); i++) {
            char character = message.charAt(i);
            if ((character < 'A' || character > 'Z') && character != '_' && (character < '0' || character > '9')) {
                return "3";
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