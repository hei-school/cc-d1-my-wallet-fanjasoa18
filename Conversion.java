import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Conversion {
    public static void convertCurrency() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le montant à convertir: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Entrez la devise actuelle (ar/frc): ");
        String currentCurrency = scanner.nextLine();
        System.out.print("Entrez la devise cible (ar/frc): ");
        String targetCurrency = scanner.nextLine();

        double conversionRate = Utils.conversionRate(currentCurrency, targetCurrency);
        double convertedAmount = amount * conversionRate;

        String conversionTransaction = String.format("Date=%s;Montant=%.2f %s -> %.2f %s\n",
                Utils.getCurrentDate(), amount, currentCurrency, convertedAmount, targetCurrency);

        try {
            FileWriter writer = new FileWriter(Utils.getTransactionFilePath(), true);
            writer.write(conversionTransaction);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.printf("Montant converti: %.2f %s%n", convertedAmount, targetCurrency);
    }
}
