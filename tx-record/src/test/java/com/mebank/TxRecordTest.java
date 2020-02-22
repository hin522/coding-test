package com.mebank;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TxRecordTest {

    private TxRecord tx = new TxRecord();

    @Test
    public void getTxs() throws IOException {
        tx.readTxs("input.csv");
        List<Transaction> txs = tx.getTxs("ACC334455",
                LocalDateTime.of(2018, 10, 20, 12, 0, 0),
                LocalDateTime.of(2018, 10, 20, 19, 0, 0));
        assertEquals(1, txs.size());
        assertEquals("TX10001", txs.get(0).getId());
    }

    @Test
    public void getBalance() {
        List<Transaction> txs = Arrays.asList(
                new Transaction("TX10001", "ACC334455", "ACC778899", LocalDateTime.of(2020, 2, 20, 11, 11, 11), new BigDecimal("25.33"), Transaction.Type.PAYMENT, null),
                new Transaction("TX10002", "ACC334455", "ACC778899", LocalDateTime.of(2020, 2, 21, 11, 11, 11), new BigDecimal("10.44"), Transaction.Type.PAYMENT, null));
        assertEquals(new BigDecimal("35.77"), tx.getBalance(txs));
    }

    @Test
    public void createTx_payment() {
        Transaction t = tx.createTx.apply("TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT");
        assertEquals("TX10001", t.getId());
        assertEquals("ACC334455", t.getFromAccountId());
        assertEquals("ACC778899", t.getToAccountId());
        assertEquals(LocalDateTime.of(2018, 10, 20, 12, 47, 55), t.getCreatedAt());
        assertEquals(new BigDecimal("-25.00"), t.getAmount());
        assertEquals(Transaction.Type.PAYMENT, t.getType());
    }

    @Test
    public void createTx_reversal() {
        Transaction t = tx.createTx.apply("TX10002, ACC334455, ACC778899, 21/10/2018 12:47:55, 25.00, REVERSAL, TX10001");
        assertEquals("TX10002", t.getId());
        assertEquals("ACC334455", t.getFromAccountId());
        assertEquals("ACC778899", t.getToAccountId());
        assertEquals(LocalDateTime.of(2018, 10, 21, 12, 47, 55), t.getCreatedAt());
        assertEquals(new BigDecimal("25.00"), t.getAmount());
        assertEquals(Transaction.Type.REVERSAL, t.getType());
        assertEquals("TX10001", t.getRelatedTransaction());
    }
}