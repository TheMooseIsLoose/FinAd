package com.thekodsquad.finad.sta;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Main {

    // 2019-01-04 11:26:21.308411214
    SimpleDateFormat transactionDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

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


        // Get an account
        Account account = accounts.get("1");
        // Budget goal
        BigDecimal budget = new BigDecimal("3000");
        // Current day, or day of month to start at
        Calendar currentDay = Calendar.getInstance();
        List<Transaction> transactionList = account.getTransactionsPerMonth(currentDay);
        transactionList.sort(Comparator.comparing(Transaction::getTimestamp));

        BigDecimal moneySpent = new BigDecimal(0);
        for (Transaction transaction : transactionList) {
            moneySpent = moneySpent.add(transaction.getAmount());
            print(transactionDateFormat.format(transaction.getTimestamp().getTime()));
        }

        BigDecimal days = BigDecimal.valueOf(ChronoUnit.DAYS.between(transactionList.get(0).getTimestamp().toInstant(), transactionList.get(transactionList.size() - 1).getTimestamp().toInstant()));
        BigDecimal moneySpentPerDay = moneySpent.divide(days);
        Calendar lastDay = Calendar.getInstance();
        lastDay.set(currentDay.get(Calendar.YEAR), currentDay.get(Calendar.MONTH), currentDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        BigDecimal daysLeft = BigDecimal.valueOf(ChronoUnit.DAYS.between(currentDay.toInstant(), lastDay.toInstant()));
        BigDecimal estMoneySpent = daysLeft.multiply(moneySpentPerDay);

        print("Money spent so far: " + moneySpent);
        print("Days left: " + daysLeft);
        print("Est money spent from now until eom: " + estMoneySpent);
        print("Budget for month: " + budget);
        print("Est money spend this month: " + moneySpent.add(estMoneySpent));


        /*

        BufferedWriter writer = Files.newBufferedWriter(Paths.get("./test.csv"));
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader());

        // Header
        List<String> headerList = new ArrayList<>();
        headerList.add("accountNumber");

        // Get spending categories
        LinkedHashMap<String, Integer> categoryMap = new LinkedHashMap<>();
        int columnIndex = 1;
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account account = entry.getValue();
            for (String category : account.getSpendingCategories()) {
                if (categoryMap.get(category) == null) {
                    categoryMap.put(category, columnIndex);
                    columnIndex++;
                }
            }
        }
        headerList.addAll(categoryMap.keySet());

        // Balance
        headerList.add("balance");

        printer.printRecord(headerList);

        // Add account records
        List<String> recordList = new ArrayList<>();
        LinkedHashMap<String, BigDecimal> spendingPerCategoryMap = new LinkedHashMap<>();
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            recordList.clear();
            spendingPerCategoryMap.clear();
            Account account = entry.getValue();
            recordList.add(String.valueOf(account.getAccountNumber()));
            for (Transaction transaction : account.getTransactions()) {
                if (spendingPerCategoryMap.get(transaction.getCategory()) == null) {
                    spendingPerCategoryMap.put(transaction.getCategory(), transaction.getAmount());
                } else {
                    spendingPerCategoryMap.put(transaction.getCategory(), spendingPerCategoryMap.get(transaction.getCategory()).add(transaction.getAmount()));
                }
            }

            for (Map.Entry<String, Integer> categoryEntry : categoryMap.entrySet()) {
                if(spendingPerCategoryMap.get(categoryEntry.getKey()) != null) {
                    recordList.add(String.valueOf(spendingPerCategoryMap.get(categoryEntry.getKey())));
                } else {
                    recordList.add("");
                }
            }

            recordList.add(String.valueOf(account.getBalance()));
            printer.printRecord(recordList);
        }


        printer.flush();
        printer.close();


         */

        /*
        Account account = accounts.get("6");
        Calendar firstTransaction = null;
        Calendar lastTransaction = null;
        for (Transaction transaction : account.getTransactions()) {
            if (firstTransaction == null)
                firstTransaction = lastTransaction = transaction.getTimestamp();
            if (transaction.getTimestamp().after(lastTransaction)) {
                lastTransaction = transaction.getTimestamp();
            }
            if (transaction.getTimestamp().before(firstTransaction)) {
                firstTransaction = transaction.getTimestamp();
            }
        }

        print("First transaction: " + transactionDateFormat.format(firstTransaction.getTime()));
        print("Last transaction: " + transactionDateFormat.format(lastTransaction.getTime()));

        for(Map.Entry<String, Account> entry : accounts.entrySet()) {
            entry.getValue().getBalance();
            entry.getValue().getSpendingPerCategory("Tulot");
            entry.getValue().getTransactions();
        }

         */
    }

    private Calendar parseTransactionDate(String timestamp) {
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
            new Main("E:\\Projects\\TKS-OP\\synt_transactions_10M.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
