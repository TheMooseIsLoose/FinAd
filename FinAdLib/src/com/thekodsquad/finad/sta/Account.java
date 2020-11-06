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

    public Account(int accountNumber) {
        this.accountNumber = accountNumber;
        transactions = new ArrayList<>();
        spendingPerCategory = new HashMap<>();
    }

    public BigDecimal getBalance() {
        return transactions.get(transactions.size() - 1).getBalance();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public Map<String, BigDecimal> getSpendingPerCategory() {
        return spendingPerCategory;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        if (spendingPerCategory.containsKey(transaction.getCategory())) {
            spendingPerCategory.put(transaction.getCategory(), spendingPerCategory.get(transaction.getCategory()).add(transaction.getAmount()));
        } else {
            spendingPerCategory.put(transaction.getCategory(), transaction.getAmount());
        }
    }

}
