package com.thekodsquad.finad.sta;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public enum Headers {
        category(0), timestamp(1), amount(6), balance(7), accountNumber(5), entryId(8);

        public final int index;

        private Headers(int index) {
            this.index = index;
        }
    }

    public Map<String, Account> accounts;

    public Main(String csvFilePath) throws IOException {
        accounts = new HashMap<String, Account>();

        Reader in = new FileReader(csvFilePath);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader().parse(in);
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

        print("Accounts: " + accounts.size());

        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account account = entry.getValue();
            //print("accountNumber: " + account.getAccountNumber() + "   " + "balance: " + account.getBalance());
            print(account.getSpendingPerCategory().get("Ruoka_paivittaistavarakauppa").toString());
        }
    }

    private Calendar parseTransactionDate(String timestamp) {
        // 2019-01-04 11:26:21.308411214
        SimpleDateFormat transactionDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

        Calendar transactionDate = Calendar.getInstance();
        try {
            transactionDate.setTime(transactionDateFormat.parse(timestamp));
        } catch (ParseException e) {
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

    private void print(String var) {
        System.out.println(var);
    }

    public static void main(String[] args) {
        try {
            new Main("E:\\Projects\\TKS-OP\\test_transactions.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
