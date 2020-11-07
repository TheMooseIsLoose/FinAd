package com.thekodsquad.finad;

import com.thekodsquad.finad.sta.Account;
import com.thekodsquad.finad.sta.Transaction;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FinAdCSV {

    private static final String CSV_FILE_PATH = "E:\\Projects\\TKS-OP\\synt_transactions_10M.csv";

    private Map<String, Account> accounts;

    public Account getAccount(String name) {
        return accounts.get(name);
    }

    public enum Headers {
        category(0), timestamp(1), amount(6), balance(7), accountNumber(5), entryId(8);

        public final int index;

        private Headers(int index) {
            this.index = index;
        }
    }

    /*
        Uses Apache Commons csv library
     */
    public FinAdCSV(InputStream file)  {
        accounts = new HashMap<String, Account>();

        // Iterate csv file, create and add transactions to accounts
        Iterable<CSVRecord> records = null;
        try {
            records = CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader().parse(new InputStreamReader(file));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        for (CSVRecord record : records) {
            String category = record.get(Headers.category);
            String timestamp = record.get(Headers.timestamp.index);
            String amount = record.get(Headers.amount.index);
            String balance = record.get(Headers.balance.index);
            String accountNumber = record.get(Headers.accountNumber.index);
            int entryId = Integer.parseInt(record.get(Headers.entryId));

            Transaction transaction = new Transaction(parseTransactionDate(timestamp), new BigDecimal(amount), new BigDecimal(balance), category, entryId);
            addTransaction(accountNumber, transaction);
        }
    }

    private Calendar parseTransactionDate(String timestamp) {
        // 2019-01-04 11:26:21.308411214
        SimpleDateFormat transactionDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

        Calendar transactionDate = Calendar.getInstance();
        try {
            transactionDate.setTime(transactionDateFormat.parse(timestamp));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return transactionDate;
    }

    private void addTransaction(String accountNumber, Transaction transaction) {
        Account account;
        if (accounts.get(accountNumber) != null) {
            account = (Account) accounts.get(accountNumber);
            account.addTransaction(transaction);
        } else {
            account = new Account(Integer.parseInt(accountNumber));
            account.addTransaction(transaction);
            accounts.put(accountNumber, account);
        }
    }

}