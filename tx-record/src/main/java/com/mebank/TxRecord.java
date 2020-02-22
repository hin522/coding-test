package com.mebank;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TxRecord {

    public static final String DELIMITER = ", ";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private List<Transaction> txs = new ArrayList<>();

    public void readTxs(String fileName) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            txs = lines.map(createTx).collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw e;
        }
    }

    public List<Transaction> getTxs(String fromAccountId, LocalDateTime fromTime, LocalDateTime toTime) {
        Supplier<Stream<Transaction>> txByAccountIdSupplier = () -> txs.stream()
                .filter(t -> t.getFromAccountId().equals(fromAccountId));

        List<String> reversalTxIds = txByAccountIdSupplier.get()
                .filter(t -> Transaction.Type.REVERSAL == t.getType())
                .map(Transaction::getRelatedTransaction)
                .collect(Collectors.toList());

        return txByAccountIdSupplier.get()
                .filter(t -> t.getCreatedAt().isAfter(fromTime) && t.getCreatedAt().isBefore(toTime))
                .filter(t -> !reversalTxIds.contains(t.getId()))
                .collect(Collectors.toList());
    }

    public BigDecimal getBalance(List<Transaction> txs) {
        return txs.stream()
                .map(Transaction::getAmount)
                .reduce(new BigDecimal(0), BigDecimal::add);
    }

    public Function<String, Transaction> createTx = (str) -> {
        String fields[] = str.split(DELIMITER);
        String relatedTx = fields.length == 7 ? fields[6] : null;
        BigDecimal amount = new BigDecimal(fields[4]);
        Transaction.Type type = Transaction.Type.valueOf(fields[5]);
        BigDecimal txAmount = Transaction.Type.PAYMENT == type ? amount.negate() : amount;
        return new Transaction(fields[0], fields[1], fields[2], LocalDateTime.parse(fields[3], DATE_TIME_FORMATTER), txAmount, type, relatedTx);
    };
}
