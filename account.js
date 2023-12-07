const readlineSync = require('readline-sync');
const fs = require('fs');
const utils = require('./utils');

function createAccount() {
    const accountName = readlineSync.question('Entrez le nom du compte: ');
    const initialBalance = parseFloat(readlineSync.question('Entrez le solde initial: '));

    const transaction = `Date=${utils.getCurrentDate()};Type=Création;Compte=${accountName};Montant=${initialBalance}\n`;
    fs.appendFileSync(utils.getTransactionFilePath(), transaction);

    console.log('Compte créé avec succès!');
}

// Exporter la fonction pour pouvoir l'utiliser dans d'autres fichiers
module.exports = {
    createAccount
};
