package Utils;

import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
    private static final String CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";

    // Создание рандомного пароля заданной длинны
    public static String randomPasswords(int length) {
        StringBuilder password = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARS.length());
            password.append(CHARS.charAt(index));
        }

        return password.toString();
    }

    // Выбор рандомного элемента в списке элементов
    public static WebElement selectRandomAutomation(List<WebElement> elements)  {
        Random random = new Random();
        WebElement randomElement = elements.get(random.nextInt(elements.size()));
        return randomElement;
    }
}
