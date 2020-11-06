package com.thekodsquad.finad.sta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {

    private int accountNumber;
    private List<Transaction> transactions;

    private Map<String, BigDecimal> spendingPerCategory;
    private Map<Transaction.EntryType, BigDecimal> spendingPerEntryType;

    public Account(int accountNumber) {
        this.accountNumber = accountNumber;
        transactions = new ArrayList<>();
        spendingPerCategory = new HashMap<>();
        spendingPerEntryType = new HashMap<>();
    }

    public BigDecimal getBalance() {
        return transactions.get(transactions.size() - 1).getBalance();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getSpendingPerCategory(String category) {
        return (spendingPerCategory.get(category) != null) ? spendingPerCategory.get(category) : new BigDecimal(0);
    }

    public BigDecimal getSpendingPerEntryType(Transaction.EntryType entryType) {
        return (spendingPerEntryType.get(entryType) != null) ? spendingPerEntryType.get(entryType) : new BigDecimal(0);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);

        // Append transaction to category spending
        if (spendingPerCategory.containsKey(transaction.getCategory())) {
            spendingPerCategory.put(transaction.getCategory(), spendingPerCategory.get(transaction.getCategory()).add(transaction.getAmount()));
        } else {
            spendingPerCategory.put(transaction.getCategory(), transaction.getAmount());
        }

        // Append transaction to entry type spending
        if (spendingPerEntryType.containsKey(transaction.getEntryType())) {
            spendingPerEntryType.put(transaction.getEntryType(), spendingPerEntryType.get(transaction.getEntryType()).add(transaction.getAmount()));
        } else {
            spendingPerEntryType.put(transaction.getEntryType(), transaction.getAmount());
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

}
