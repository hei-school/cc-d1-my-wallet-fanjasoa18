import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Transaction {
    public static void depositMoney() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le nom du compte: ");
        String accountName = scanner.nextLine();
        System.out.print("Entrez le montant à déposer: ");
        double depositAmount = Double.parseDouble(scanner.nextLine());

        String transaction = String.format("Date=%s;Type=Dépôt;Compte=%s;Montant=%.2f\n",
                Utils.getCurrentDate(), accountName, depositAmount);

        try {
            FileWriter writer = new FileWriter(Utils.getTransactionFilePath(), true);
            writer.write(transaction);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Dépôt effectué avec succès!");
    }

    public static void withdrawMoney() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le nom du compte: ");
        String accountName = scanner.nextLine();

        try {
            String transactions = new String(Files.readAllBytes(Paths.get(Utils.getTransactionFilePath())));
            boolean accountExists = Utils.accountExists(transactions, accountName);

            if (accountExists) {
                System.out.print("Entrez le montant à retirer: ");
                double withdrawalAmount = Double.parseDouble(scanner.nextLine());

                double currentBalance = Utils.calculateBalance(transactions, accountName);

                if (withdrawalAmount > currentBalance) {
                    System.out.println("Solde insuffisant pour effectuer le retrait.");
                } else {
                    String transaction = String.format("Date=%s;Type=Retrait;Compte=%s;Montant=%.2f\n",
                            Utils.getCurrentDate(), accountName, withdrawalAmount);

                    FileWriter writer = new FileWriter(Utils.getTransactionFilePath(), true);
                    writer.write(transaction);
                    writer.close();

                    System.out.println("Retrait effectué avec succès!");
                }
            } else {
                System.out.println("Veuillez créer un compte avant de retirer de l'argent.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showTransactionHistory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez le nom du compte: ");
        String accountName = scanner.nextLine();

        try {
            String transactions = new String(Files.readAllBytes(Paths.get(Utils.getTransactionFilePath())));

            if (Utils.accountExists(transactions, accountName)) {
                String[] allTransactions = transactions.split("\n");
                List<String> accountTransactions = Arrays.stream(allTransactions)
                        .filter(line -> Utils.parseTransaction(line).getCompte().equals(accountName))
                        .collect(Collectors.toList());

                int numberOfTransactionsToShow = Math.min(3, accountTransactions.size());

                if (numberOfTransactionsToShow > 0) {
                    List<String> lastThreeTransactions = accountTransactions.subList(accountTransactions.size() - numberOfTransactionsToShow, accountTransactions.size());

                    System.out.println("Les trois dernières transactions:");
                    for (String transaction : lastThreeTransactions) {
                        System.out.println(transaction);
                    }
                } else {
                    System.out.println("Aucune transaction effectuée jusqu'à présent.");
                }
            } else {
                System.out.printf("Le compte \"%s\" n'existe pas.%n", accountName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
