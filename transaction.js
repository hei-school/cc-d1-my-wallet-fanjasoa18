const readlineSync = require('readline-sync');
const fs = require('fs');
const utils = require('./utils');

function depositMoney() {
    const accountName = readlineSync.question('Entrez le nom du compte: ');
    const depositAmount = parseFloat(readlineSync.question('Entrez le montant a deposer: '));

    const transaction = `Date=${utils.getCurrentDate()};Type=Dépôt;Compte=${accountName};Montant=${depositAmount}\n`;
    fs.appendFileSync(utils.getTransactionFilePath(), transaction);

    console.log('Dépôt effectué avec succès!');
}

function withdrawMoney() {
    const accountName = readlineSync.question('Entrez le nom du compte: ');

    // Charger les transactions depuis le fichier
    const transactions = fs.readFileSync(utils.getTransactionFilePath(), 'utf8');

    // Vérifier si le compte existe avant de permettre un retrait
    const accountExists = utils.accountExists(transactions, accountName);

    if (accountExists) {
        const withdrawalAmount = parseFloat(readlineSync.question('Entrez le montant à retirer: '));

        // Vérifier si le solde est suffisant pour le retrait
        const currentBalance = utils.calculateBalance(transactions, accountName);
        if (withdrawalAmount > currentBalance) {
            console.log('Solde insuffisant pour effectuer le retrait.');
        } else {
            const transaction = `Date=${utils.getCurrentDate()};Type=Retrait;Compte=${accountName};Montant=${withdrawalAmount}\n`;
            fs.appendFileSync(utils.getTransactionFilePath(), transaction);
            
            console.log('Retrait effectué avec succès!');
        }
    } else {
        console.log(`Veuillez créer un compte avant de retirer de l'argent.`);
    }
}

function showTransactionHistory() {
    const accountName = readlineSync.question('Entrez le nom du compte: ');

    const transactions = fs.readFileSync(utils.getTransactionFilePath(), 'utf8');

    // Vérifier si le compte existe avant d'afficher l'historique
    if (utils.accountExists(transactions, accountName)) {
        // Les trois derniers transactions
        const lastThreeTransactions = transactions.trim().split('\n').slice(-3).join('\n');

        if (lastThreeTransactions) {
            console.log('Les trois dernières transactions:');
            console.log(lastThreeTransactions);
        } else {
            console.log('Aucune transaction effectuée jusqu\'à présent.');
        }
    } else {
        console.log(`Le compte "${accountName}" n'existe pas.`);
    }
}

module.exports = {
    depositMoney,
    withdrawMoney,
    showTransactionHistory,
};
