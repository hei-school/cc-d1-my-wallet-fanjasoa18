import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String choice;
        do {
            displayMenu();

            System.out.print("Choisissez une option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Account.createAccount();
                    break;
                case "2":
                    Balance.checkBalance();
                    break;
                case "3":
                    Transaction.depositMoney();
                    break;
                case "4":
                    Transaction.withdrawMoney();
                    break;
                case "5":
                    Transaction.showTransactionHistory();
                    break;
                case "6":
                    Conversion.convertCurrency();
                    break;
                case "7":
                    System.out.println("Merci d'avoir utilisé le programme. Au revoir!");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
                    break;
            }
        } while (!choice.equals("7"));
    }

    public static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Création de compte");
        System.out.println("2. Consultation de solde");
        System.out.println("3. Dépôt d'argent");
        System.out.println("4. Retrait d'argent");
        System.out.println("5. Historique de transaction");
        System.out.println("6. Conversion de devise");
        System.out.println("7. Quitter");
    }
}
