import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Balance {
    public static void checkBalance() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le nom du compte: ");
        String accountName = scanner.nextLine();

        try {
            String transactions = new String(Files.readAllBytes(Paths.get(Utils.getTransactionFilePath())));
            double balance = Utils.calculateBalance(transactions, accountName);

            if (!Double.isNaN(balance)) {
                System.out.printf("Solde actuel du compte \"%s\": %.2f%n", accountName, balance);
            } else {
                System.out.printf("Le compte \"%s\" n'existe pas.%n", accountName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
