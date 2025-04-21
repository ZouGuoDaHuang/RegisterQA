import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import static utils.KeyManager.generateKey;
import static utils.KeyManager.getErrorMessage;

public class Main {
    private static String 彩蛋总数 = "3";
    private static final String REPOSITORY_URL = "https://github.com/XuanLingJingYue/RegisterQA";
    private static final String VERSION = "1.0.3";
    private static final String WELCOME_BANNER =
            "  _____            _     _             ____            \n" +
                    " |  __ \\          (_)   | |           / __ \\     /\\    \n" +
                    " | |__) |___  __ _ _ ___| |_ ___ _ __| |  | |   /  \\   \n" +
                    " |  _  // _ \\/ _` | / __| __/ _ \\ '__| |  | |  / /\\ \\  \n" +
                    " | | \\ \\  __/ (_| | \\__ \\ ||  __/ |  | |__| | / ____ \\ \n" +
                    " |_|  \\_\\___|\\__, |_|___/\\__\\___|_|   \\___\\_\\/_/    \\_\\\n" +
                    "              __/ |                                    \n" +
                    "             |___/                                     ";

    public static void main(String[] args) {
        printWelcomeMessage();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String userInput = promptForUserInput(scanner);
                if (userInput == null || userInput.isEmpty()) {
                    continue;
                }
                processKeyGeneration(userInput);
            }
        }
    }

    private static void printWelcomeMessage() {
        System.out.println(WELCOME_BANNER);
        System.out.println("Repository: " + REPOSITORY_URL);
        System.out.println("Version: " + VERSION);
    }

    private static String promptForUserInput(Scanner scanner) {
        System.out.print("\nEnter message: ");
        return scanner.hasNextLine() ? scanner.nextLine().trim() : null;
    }

    private static void processKeyGeneration(String userInput) {
        String result = generateKey(userInput);

        if(userInput.equalsIgnoreCase("Never Gonna Give You Up")){
            browserUrl("https://www.bilibili.com/video/BV1GJ411x7h7/");
            JOptionPane.showMessageDialog(null, "彩蛋：1-"+彩蛋总数);
            return;
        }
        if(userInput.equalsIgnoreCase("bad apple")){
            browserUrl("https://youtu.be/i41KoE0iMYU");
            JOptionPane.showMessageDialog(null, "彩蛋：2-"+彩蛋总数);
            return;
        }
        if(userInput.equals("what?")){
            System.out.println("whattttttttttttttttttttttttttttttttttttttttttttt?");
            JOptionPane.showMessageDialog(null, "彩蛋：3-"+彩蛋总数);
            return;
        }
        
        if(result.contains("-") && result.length() == 43){
            System.out.println("Generated key: " + result);
        }else{
            System.out.println(getErrorMessage(Integer.parseInt(result)));
        }
    }

    private static void browserUrl(String url){
        if (Desktop.isDesktopSupported()){
            try {
                URI uri = new URI(url);
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(uri);

            } catch (URISyntaxException | IOException ignored) {
            }
        }
    }
}