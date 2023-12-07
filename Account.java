import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Account {
    public static void createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le nom du compte: ");
        String accountName = scanner.nextLine();
        System.out.print("Entrez le solde initial: ");
        double initialBalance = Double.parseDouble(scanner.nextLine());

        String transaction = String.format("Date=%s;Type=Création;Compte=%s;Montant=%.2f\n",
                Utils.getCurrentDate(), accountName, initialBalance);

        try {
            FileWriter writer = new FileWriter(Utils.getTransactionFilePath(), true);
            writer.write(transaction);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Compte cree avec succes!");
    }
}
