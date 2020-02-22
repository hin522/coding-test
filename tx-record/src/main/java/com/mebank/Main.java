package com.mebank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length == 1) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                TxRecord tr = new TxRecord();
                tr.readTxs(args[0]);

                System.out.print("Enter account ID : ");
                String accountId = br.readLine();
                System.out.print("Enter from date time (dd/MM/yyyy hh:mm:ss) : ");
                LocalDateTime fromDateTime = LocalDateTime.parse(br.readLine(), TxRecord.DATE_TIME_FORMATTER);
                System.out.print("Enter to date time (dd/MM/yyyy hh:mm:ss) : ");
                LocalDateTime toDateTime = LocalDateTime.parse(br.readLine(), TxRecord.DATE_TIME_FORMATTER);
                List<Transaction> txs = tr.getTxs(accountId, fromDateTime, toDateTime);

                System.out.println("Relative balance for the period is : " + tr.getBalance(txs));
                System.out.println("Number of transactions included is : " + txs.size());
            } catch (Throwable e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Error: missing CSV file argument");
            System.out.println("Usage: java -jar build/libs/tx-record-1.0-SNAPSHOT.jar <csv_file>");
        }
    }
}