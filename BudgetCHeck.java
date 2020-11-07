package com.thekodsquad.finad.sta;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class BudgetCHeck {

    public BudgetCHeck() {
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
    }

}
