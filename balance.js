const readlineSync = require('readline-sync');
const fs = require('fs');
const utils = require('./utils');

function checkBalance() {
    const accountName = readlineSync.question('Entrez le nom du compte: ');

    const transactions = fs.readFileSync(utils.getTransactionFilePath(), 'utf8');
    const balance = utils.calculateBalance(transactions, accountName);

    if (balance !== undefined) {
        console.log(`Solde actuel du compte "${accountName}": ${balance}`);
    } else {
        console.log(`Le compte "${accountName}" n'existe pas.`);
    }
}

module.exports = {
    checkBalance
};
