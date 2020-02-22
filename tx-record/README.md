# Tx Record
A simple Java command line application to analyse financial transaction records. It takes a list of transactions from a comma separated CSV file. 

The application allows user to query the relative balance for an account within a given time ranges.

## CSV format
[txId],[accountId],[fromAccountId],[toAccountId],[dateTime: dd/MM/yyyy hh:mm:ss],[amount],[paymentType: PAYMENT | REVERSAL],[relatedTxId]

## How to build
``
./gradlew jar
``

## How to run
``
./java -jar build/libs/tx-record-1.0-SNAPSHOT.jar input.csv
``

## Assumptions
* The transaction inputs are valid

## Design considerations
* Transaction records data are store in memory, no persistence store is used.
* Implementation only handles PAYMENT and REVERSAL types, where negative amount applies to PAYMENT and positive amount applies to REVERSAL.
* All transactions are read into a single list to keep implementation simple. For more efficient query by accountId, transactions could be read into different list based on the accountId.    