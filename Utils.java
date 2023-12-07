import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Utils {
    public static String getCurrentDate() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(now);
    }

    public static double calculateBalance(String transactions, String accountName) {
        String[] lines = transactions.split("\n");
        double balance = 0;
        double initialBalance = 0;

        for (String line : lines) {
            TransactionDetails transactionDetails = parseTransaction(line);

            if (transactionDetails != null) {
                if ("Création".equals(transactionDetails.getType()) && accountName.equals(transactionDetails.getCompte())) {
                    // Mettre à jour le solde initial lors de la création du compte
                    initialBalance = Double.parseDouble(transactionDetails.getMontant());
                } else if (accountName.equals(transactionDetails.getCompte())) {
                    // Appliquer les transactions au solde initial
                    if ("Dépôt".equals(transactionDetails.getType())) {
                        initialBalance += Double.parseDouble(transactionDetails.getMontant());
                    } else if ("Retrait".equals(transactionDetails.getType())) {
                        initialBalance -= Double.parseDouble(transactionDetails.getMontant());
                    }
                }
            }
        }

        return initialBalance;
    }

    public static TransactionDetails parseTransaction(String line) {
        String[] keyValuePairs = line.split(";");
        TransactionDetails transactionDetails = new TransactionDetails();

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                if ("Montant".equals(key)) {
                    // Remplacer la virgule par un point dans la valeur du montant
                    value = value.replace(",", ".");
                }
                switch (key) {
                    case "Date":
                        transactionDetails.setDate(value);
                        break;
                    case "Type":
                        transactionDetails.setType(value);
                        break;
                    case "Compte":
                        transactionDetails.setCompte(value);
                        break;
                    case "Montant":
                        transactionDetails.setMontant(value);
                        break;
                    // Ajouter d'autres cas si nécessaire
                }
            }
        }

        return transactionDetails;
    }

    public static String getTransactionFilePath() {
        String filePath = "transactions.txt";
        try {
            // Vérifier si le fichier existe, sinon le créer
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static boolean accountExists(String transactions, String accountName) {
        return Arrays.stream(transactions.split("\n"))
                .map(line -> parseTransaction(line))
                .anyMatch(transactionDetails -> transactionDetails != null && accountName.equals(transactionDetails.getCompte()));
    }

    public static double conversionRate(String currentCurrency, String targetCurrency) {
        // Taux de conversion par défaut (1:1)
        double tauxDeConversion = 1;

        // Définir les taux de conversion en fonction de la relation donnée
        if ("ar".equals(currentCurrency) && "frc".equals(targetCurrency)) {
            tauxDeConversion = 5;
        } else if ("frc".equals(currentCurrency) && "ar".equals(targetCurrency)) {
            tauxDeConversion = 1 / 5.0;
        }

        return tauxDeConversion;
    }

}
