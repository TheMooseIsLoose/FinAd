package com.thekodsquad.finad.sta;

import java.math.BigDecimal;
import java.util.Calendar;

public class Transaction {

    private Calendar timestamp;
    private BigDecimal amount;
    private BigDecimal balance;
    private String category;
    private EntryType entryType;

    public enum EntryType {
        other(0), subscription(701), cardPayment(721), loanPayment(760), deposit(710), withdrawal(720);

        public final int id;

        private EntryType(int id) {
            this.id = id;
        }

        public static EntryType valueOfId(int id) {
            for (EntryType entryType : values()) {
                if (entryType.id == id) {
                    return entryType;
                }
            }
            return other;
        }
    }

    Transaction(Calendar timestamp, BigDecimal amount, BigDecimal balance, String category, int id) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.balance = balance;
        this.category = category;
        this.entryType = EntryType.valueOfId(id);
    }

    public Calendar getTimestamp() {
        return timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCategory() {
        return category;
    }

}
