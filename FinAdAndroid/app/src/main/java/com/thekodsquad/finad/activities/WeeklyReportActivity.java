package com.thekodsquad.finad.activities;

import android.os.Bundle;

import com.thekodsquad.finad.R;
import com.thekodsquad.finad.sta.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WeeklyReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeklyreport);
        Account account = new Account(1);
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        account.addTransaction(new Transaction(Calendar.getInstance(), BigDecimal.TEN, BigDecimal.TEN, "Tulot", 0));
        generateReport(account, 2020, 45);
    }

    public void generateReport(Account account, int year, int week) {
        System.out.println(account.getSpendingPerCategory("Tulot", year, week));
    }

}
