package com.thekodsquad.finad.sta;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    public BigDecimal getSpendingPerCategory(String category, int year, int week) {
        return transactions.stream()
                .filter(transaction -> transaction.getCategory().equals(category))
                .filter(transaction -> transaction.getTimestamp().get(Calendar.YEAR) == year
                        && transaction.getTimestamp().get(Calendar.WEEK_OF_YEAR) == week)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Transaction> getTransactionsPerMonth(Calendar lastDate) {
        Calendar firstDate = Calendar.getInstance();
        firstDate.set(lastDate.get(Calendar.YEAR), lastDate.get(Calendar.MONTH), 1);
        return transactions.stream()
                .filter(transaction -> transaction.getTimestamp().before(lastDate))
                .filter(transaction -> transaction.getTimestamp().after(firstDate))
                .collect(Collectors.toList());
    }

    public BigDecimal getSpendingPerEntryType(Transaction.EntryType entryType) {
        return (spendingPerEntryType.get(entryType) != null) ? spendingPerEntryType.get(entryType) : new BigDecimal(0);
    }

    public Set<String> getSpendingCategories() {
        return spendingPerCategory.keySet();
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
