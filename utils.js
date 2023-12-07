function getCurrentDate() {
    const now = new Date();
    const day = now.getDate();
    const month = now.getMonth() + 1;
    const year = now.getFullYear();

    return `${year}-${month}-${day}`;
}

function calculateBalance(transactions, accountName) {
    const lines = transactions.split('\n');
    let balance = 0;
    let initialBalance = 0;

    for (const line of lines) {
        const transactionDetails = parseTransaction(line);

        if (transactionDetails) {
            if (transactionDetails.Type === 'Création' && transactionDetails.Compte === accountName) {
                // Mettre à jour le solde initial lors de la création du compte
                initialBalance = parseFloat(transactionDetails.Montant);
            } else if (transactionDetails.Compte === accountName) {
                // Appliquer les transactions au solde initial
                if (transactionDetails.Type === 'Dépôt') {
                    initialBalance += parseFloat(transactionDetails.Montant);
                } else if (transactionDetails.Type === 'Retrait') {
                    initialBalance -= parseFloat(transactionDetails.Montant);
                }
            }
        }
    }

    return initialBalance;
}

function parseTransaction(line) {
    const transactionDetails = {};
    const keyValuePairs = line.split(';');

    for (const pair of keyValuePairs) {
        const [key, value] = pair.split('=');
        transactionDetails[key] = value;
    }

    return transactionDetails;
}

function getTransactionFilePath() {
    return 'transactions.txt';
}

function accountExists(transactions, accountName) {
    const lines = transactions.split('\n');

    for (const line of lines) {
        const transactionDetails = parseTransaction(line);

        if (transactionDetails && transactionDetails.Compte === accountName) {
            return true;
        }
    }

    return false;
}

function conversionRate(currentCurrency, targetCurrency) {
    // Taux de conversion par défaut (1:1)
    let tauxDeConversion = 1;

    // Définir les taux de conversion en fonction de la relation donnée
    if (currentCurrency === 'ar' && targetCurrency === 'frc') {
        tauxDeConversion = 5;
    } else if (currentCurrency === 'frc' && targetCurrency === 'ar') {
        tauxDeConversion = 1 / 5;
    }

    return tauxDeConversion;
}

module.exports = {
    getCurrentDate,
    calculateBalance,
    parseTransaction,
    getTransactionFilePath,
    accountExists,
    conversionRate
};